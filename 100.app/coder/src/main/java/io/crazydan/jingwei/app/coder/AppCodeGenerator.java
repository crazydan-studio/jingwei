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
import io.crazydan.duzhou.framework.commons.ShellHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.coder.model.AiModelDesign;
import io.crazydan.jingwei.app.coder.model.AiOrmModel;
import io.crazydan.jingwei.app.coder.model.AiUiDesign;
import io.crazydan.jingwei.app.coder.model.AiUiModel;
import io.nop.api.core.exceptions.NopException;
import io.nop.codegen.XCodeGenerator;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;

import static io.crazydan.jingwei.app.coder.AppCoderConfigs.CFG_APP_BUILD_NODE_MODULES_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConfigs.CFG_APP_BUILD_NPM_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.BUILD_DIR_DIST;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.BUILD_DIR_HIDDEN_BUILD;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.BUILD_DIR_NODE_MODULES;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenConfig;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenModel;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_APP_MODEL_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_APP_PAGE_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_NODE_MODULES_PATH_NOT_SPECIFIED;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_NPM_PATH_NOT_SPECIFIED;
import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;

/**
 * 应用构建器
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public abstract class AppCodeGenerator {
    private final String npmPath;
    private final String nodeModulesPath;

    public AppCodeGenerator() {
        this(CFG_APP_BUILD_NPM_PATH.get(), CFG_APP_BUILD_NODE_MODULES_PATH.get());
    }

    public AppCodeGenerator(String npmPath, String nodeModulesPath) {
        this.npmPath = StringHelper.normalizePath(npmPath);
        this.nodeModulesPath = StringHelper.normalizePath(nodeModulesPath);
    }

    /** 构建应用模型资源，主要为 {@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz} */
    protected void genModels(IResource modelDesignResource, File targetDir, AppCodeGenConfig genConfig) {
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
    }

    /** 构建应用的页面资源 */
    protected void genPages(IResource uiDesignResource, File targetDir, AppCodeGenConfig genConfig) {
        String buildDirPath = preparePageBuildDir(targetDir);
        File buildDir = new File(buildDirPath);

        AiUiDesign uiDesign = new AiUiDesign(uiDesignResource, genConfig);

        Map<String, Object> vars = Map.of();
        AiUiModel uiModel = uiDesign.genUiModel(vars);

        XCodeGenerator gen = new XCodeGenerator(TEMPLATE_APP_PAGE_PATH, buildDirPath);
        // 前端代码不支持用户定制，因此，强制更新所有文件
        gen.forceOverride(true);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(SCOPE_VAR_codeGenConfig, genConfig);
        scope.setLocalValue(SCOPE_VAR_codeGenModel, uiModel);

        gen.execute("", scope);

        buildPageSourceCode(buildDir);

        File buildDistDir = new File(buildDir, BUILD_DIR_DIST);
        FileHelper.copyWithFilter(buildDistDir, targetDir, null);
        FileHelper.deleteDir(buildDir);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private String preparePageBuildDir(File targetDir) {
        if (StringHelper.isBlank(this.npmPath)) {
            throw new NopException(ERR_BUILD_NPM_PATH_NOT_SPECIFIED).param(ARG_CONFIG_VAR,
                                                                           CFG_APP_BUILD_NPM_PATH.getName());
        }

        if (StringHelper.isBlank(this.nodeModulesPath)) {
            throw new NopException(ERR_BUILD_NODE_MODULES_PATH_NOT_SPECIFIED).param(ARG_CONFIG_VAR,
                                                                                    CFG_APP_BUILD_NODE_MODULES_PATH.getName());
        }

        File nodeModulesDir = new File(this.nodeModulesPath);
        FileHelper.assureDirExists(nodeModulesDir);

        File buildDir = new File(targetDir, BUILD_DIR_HIDDEN_BUILD);
        FileHelper.deleteDir(buildDir);
        FileHelper.assureDirExists(buildDir);

        File nodeModulesInBuildDir = new File(buildDir, BUILD_DIR_NODE_MODULES);
        FileHelper.createSymbolLink(nodeModulesInBuildDir, nodeModulesDir);

        return FileHelper.getAbsolutePath(buildDir);
    }

    private void buildPageSourceCode(File buildDir) {
        // 不做依赖安装，其会删除软链接，造成 node_modules 无法被共享。
        // 该过程也涉及外网访问，可能需要代理加速
        //ShellHelper.runExecutable(npmPath, new String[] { "install" }, buildDir, true);

        ShellHelper.runExecutable(this.npmPath, new String[] { "run", "build" }, buildDir, true);
    }
}
