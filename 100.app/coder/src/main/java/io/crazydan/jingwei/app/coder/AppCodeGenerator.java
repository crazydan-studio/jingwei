/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2026 Crazydan Studio <https://studio.crazydan.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <https://www.gnu.org/licenses/lgpl-3.0.en.html#license-text>.
 */

package io.crazydan.jingwei.app.coder;

import java.io.File;
import java.util.Map;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.PnpmRunner;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.coder.model.AiModelDesign;
import io.crazydan.jingwei.app.coder.model.AiOrmModel;
import io.crazydan.jingwei.app.coder.model.AiUiDesign;
import io.crazydan.jingwei.app.coder.model.AiUiModel;
import io.nop.api.core.util.ISourceLocationGetter;
import io.nop.codegen.XCodeGenerator;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;
import io.nop.xlang.api.XLang;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.BUILD_DIR_HIDDEN_BUILD;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.CODEGEN_TEMPLATE_APP_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.CODEGEN_TEMPLATE_APP_PAGE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenConfig;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenModel;

/**
 * 应用构建器
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public abstract class AppCodeGenerator {

    /** 构建应用模型资源，主要为 {@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz} */
    protected void genModels(IResource modelDesignResource, File targetDir, AppCodeGenConfig genConfig) {
        AiModelDesign modelDesign = new AiModelDesign(modelDesignResource, genConfig);

        Map<String, Object> vars = Map.of();
        AiOrmModel ormModel = modelDesign.genOrmModel(vars);

        String targetDirPath = FileHelper.getAbsolutePath(targetDir);
        FileHelper.assureDirExists(targetDir);

        XCodeGenerator gen = new XCodeGenerator(CODEGEN_TEMPLATE_APP_MODEL, targetDirPath);
        // 保持用户定制的代码不变，仅更新以下划线开头的文件
        gen.forceOverride(false);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(SCOPE_VAR_codeGenConfig, genConfig);
        scope.setLocalValue(SCOPE_VAR_codeGenModel, ormModel);

        gen.execute("", scope);
    }

    /** 构建应用的页面资源 */
    protected void genPages(IResource uiDesignResource, File targetDir, AppCodeGenConfig genConfig) {
        String buildDirPath = preparePageBuildDir(targetDir);
        File buildDir = new File(buildDirPath);

        AiUiDesign uiDesign = new AiUiDesign(uiDesignResource, genConfig);

        Map<String, Object> vars = Map.of();
        AiUiModel uiModel = uiDesign.genUiModel(vars);

        XCodeGenerator gen = new XCodeGenerator(CODEGEN_TEMPLATE_APP_PAGE, buildDirPath);
        // 前端代码不支持用户定制，因此，强制更新所有文件
        gen.forceOverride(true);
        // Note: 禁止格式化，以避免 Vue 等非标准 xml 格式化报错
        gen.autoFormat(false);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(SCOPE_VAR_codeGenConfig, genConfig);
        scope.setLocalValue(SCOPE_VAR_codeGenModel, uiModel);

        gen.execute("", scope);

        PnpmRunner pnpm = new PnpmRunner(buildDir);
        pnpm.runScript("build");
        pnpm.copyDistTo(targetDir, false);

        FileHelper.deleteDir(buildDir);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /** 根据资源位置获取指定资源 */
    protected IResource getResource(ISourceLocationGetter locator, String path) {
        String inDir = StringHelper.removeLastPart(locator.getLocation().getPath(), '/');
        String vPath = StringHelper.appendPath(inDir, path);

        return VirtualFileSystem.instance().getResource(vPath, true);
    }

    /** 复制资源到指定目录。若资源不存在，则不复制 */
    protected void copyResource(ISourceLocationGetter locator, String path, File targetDir) {
        IResource resource = getResource(locator, path);
        if (resource == null) {
            return;
        }

        String name = StringHelper.fileName(path);
        File targetFile = new File(targetDir, name);

        resource.saveToFile(targetFile);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private String preparePageBuildDir(File targetDir) {
        File buildDir = new File(targetDir, BUILD_DIR_HIDDEN_BUILD);
        FileHelper.deleteDir(buildDir);
        FileHelper.assureDirExists(buildDir);

        return FileHelper.getAbsolutePath(buildDir);
    }
}
