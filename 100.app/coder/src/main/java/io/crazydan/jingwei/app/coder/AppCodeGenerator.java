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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.coder.model.AiModelDesign;
import io.crazydan.jingwei.app.coder.model.AiOrmModel;
import io.crazydan.jingwei.app.coder.model.AiUiDesign;
import io.crazydan.jingwei.app.coder.model.AiUiModel;
import io.nop.api.core.exceptions.NopException;
import io.nop.codegen.XCodeGenerator;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.shell.DefaultShellOutputCollector;
import io.nop.shell.ShellCommand;
import io.nop.shell.ShellRunner;
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
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_DIR_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_DIR_ORM;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_FAILED_TO_CREATE_NODE_MODULES_LINK;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_NODE_MODULES_PATH_NOT_SPECIFIED;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_NPM_NOT_USABLE;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_NPM_PATH_NOT_SPECIFIED;
import static io.crazydan.jingwei.app.coder.AppCoderErrors.ERR_BUILD_RUN_ERROR;
import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;
import static io.nop.xlang.XLangErrors.ARG_ERROR;
import static io.nop.xlang.XLangErrors.ARG_PATH;

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
        FileHelper.removeDir(buildDir);

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

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private String preparePageBuildDir(File targetDir) {
        String npmPath = CFG_APP_BUILD_NPM_PATH.get();
        if (StringHelper.isBlank(npmPath)) {
            throw new NopException(ERR_BUILD_NPM_PATH_NOT_SPECIFIED).param(ARG_CONFIG_VAR,
                                                                           CFG_APP_BUILD_NPM_PATH.getName());
        }
        File npmFile = new File(npmPath);
        if (!npmFile.exists() || !npmFile.isFile()) {
            throw new NopException(ERR_BUILD_NPM_NOT_USABLE).param(ARG_PATH, npmPath);
        }

        String nodeModulesPath = CFG_APP_BUILD_NODE_MODULES_PATH.get();
        if (StringHelper.isBlank(nodeModulesPath)) {
            throw new NopException(ERR_BUILD_NODE_MODULES_PATH_NOT_SPECIFIED).param(ARG_CONFIG_VAR,
                                                                                    CFG_APP_BUILD_NODE_MODULES_PATH.getName());
        }
        nodeModulesPath = StringHelper.normalizePath(nodeModulesPath);

        File nodeModulesDir = new File(nodeModulesPath);
        FileHelper.assureDirExists(nodeModulesDir);

        File buildDir = new File(targetDir, BUILD_DIR_HIDDEN_BUILD);
        FileHelper.removeDir(buildDir);
        FileHelper.assureDirExists(buildDir);

        File nodeModulesInBuildDir = new File(buildDir, BUILD_DIR_NODE_MODULES);
        try {
            Files.createSymbolicLink(nodeModulesInBuildDir.toPath(), nodeModulesDir.getAbsoluteFile().toPath());
        } catch (Exception e) {
            throw new NopException(ERR_BUILD_FAILED_TO_CREATE_NODE_MODULES_LINK, e);
        }

        return FileHelper.getAbsolutePath(buildDir);
    }

    private void buildPageSourceCode(File buildDir) {
        String npmPath = CFG_APP_BUILD_NPM_PATH.get();

        // 不做依赖安装，其会删除软链接，造成 node_modules 无法被共享。
        // 该过程也涉及外网访问，可能需要代理加速
        //runCommand(npmPath + " install", buildDir);

        runCommand(npmPath + " run build", buildDir);
    }

    private void runCommand(String command, File workDir) {
        ShellCommand cmd = new ShellCommand();
        // Note: npm 的执行器为 node，不能使用 sh/bash 运行 npm
        String[] args = ShellCommand.splitCommandLine(command);
        for (String arg : args) {
            cmd.addCmd(arg);
        }

        cmd.redirectErrorStream(true);
        cmd.workDir(workDir.getAbsolutePath());

        ShellRunner runner = new ShellRunner();
        DefaultShellOutputCollector collector = new DefaultShellOutputCollector();

        int exitCode = runner.run(cmd, collector);
        if (exitCode != 0) {
            String msg = collector.getOutput() + '\n' + collector.getError();
            throw new NopException(ERR_BUILD_RUN_ERROR).param(ARG_ERROR, msg);
        }
    }
}
