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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.jingwei.app.coder.model.AiModelDesign;
import io.crazydan.jingwei.app.coder.model.AiOrmModel;
import io.nop.codegen.XCodeGenerator;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenConfig;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenModel;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_APP_MODEL_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_DIR_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_DIR_ORM;

/**
 * 应用构建器
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public class AppCodeGenerator {
    private final List<String> models = new ArrayList<>();
    private final List<String> orms = new ArrayList<>();
    private final List<String> pages = new ArrayList<>();

    /** 构建应用模型资源，主要为 {@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz} */
    public void genModels(File targetDir, IResource modelDesignResource, AppCodeGenConfig genConfig) {
        AiModelDesign modelDesign = new AiModelDesign(modelDesignResource, genConfig);

        Map<String, Object> vars = Map.of();
        AiOrmModel ormModel = modelDesign.genOrmModel(vars);

        String targetDirPath = FileHelper.getAbsolutePath(targetDir);
        FileHelper.assureDirExists(targetDir);

        XCodeGenerator gen = new XCodeGenerator(TEMPLATE_APP_MODEL_PATH, targetDirPath);
        // 保持用户定制的代码不变，仅更新以下划线开头的文件
        gen.forceOverride(false);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(SCOPE_VAR_codeGenConfig, genConfig);
        scope.setLocalValue(SCOPE_VAR_codeGenModel, ormModel);

        gen.execute("", scope);

        List<String> paths = FileHelper.findFilePaths(targetDir, TEMPLATE_DIR_ORM + "/**/*", true, true);
        this.orms.addAll(paths);

        paths = FileHelper.findFilePaths(targetDir, TEMPLATE_DIR_MODEL + "/**/*", true, true);
        this.models.addAll(paths);
    }

    /** 构建应用的页面资源 */
    public void genPages(File targetDir, IResource uiDesignResource, AppCodeGenConfig genConfig) {
        String targetDirPath = FileHelper.getAbsolutePath(targetDir);
        FileHelper.assureDirExists(targetDir);

        List<String> paths = FileHelper.findFilePaths(targetDir, "**/*", true, true);
        this.pages.addAll(paths);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public List<String> getModels() {
        return this.models;
    }

    public List<String> getOrms() {
        return this.orms;
    }

    public List<String> getPages() {
        return this.pages;
    }
}
