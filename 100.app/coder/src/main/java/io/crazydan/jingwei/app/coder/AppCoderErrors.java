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

import io.nop.api.core.exceptions.ErrorCode;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.BUILD_DIR_NODE_MODULES;
import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;
import static io.nop.api.core.exceptions.ErrorCode.define;
import static io.nop.xlang.XLangErrors.ARG_ERROR;
import static io.nop.xlang.XLangErrors.ARG_PATH;

/**
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public interface AppCoderErrors {

    ErrorCode ERR_BUILD_NODE_MODULES_PATH_NOT_SPECIFIED = //
            define("jingwei.err.app.build.node-modules-path-not-specified",
                   "未通过配置项 {"
                   + ARG_CONFIG_VAR
                   + "} 指定应用页面代码构建时的共享 "
                   + BUILD_DIR_NODE_MODULES
                   + " 的路径",
                   ARG_CONFIG_VAR);
    ErrorCode ERR_BUILD_FAILED_TO_CREATE_NODE_MODULES_LINK = //
            define("jingwei.err.app.build.failed-to-create-node-modules-link",
                   "创建指向共享 " + BUILD_DIR_NODE_MODULES + " 目录的软链接失败");

    ErrorCode ERR_BUILD_NPM_PATH_NOT_SPECIFIED = //
            define("jingwei.err.app.build.npm-path-not-specified",
                   "未通过配置项 {" + ARG_CONFIG_VAR + "} 指定应用页面代码构建工具 NPM 的可执行文件路径",
                   ARG_CONFIG_VAR);
    ErrorCode ERR_BUILD_NPM_NOT_USABLE = //
            define("jingwei.err.app.build.npm-not-usable",
                   "NPM 文件 {" + ARG_PATH + "} 不存在或不是可执行的",
                   ARG_PATH);

    ErrorCode ERR_BUILD_RUN_ERROR = //
            define("jingwei.err.app.build.run-error", //
                   "构建出现异常：{" + ARG_ERROR + "}", ARG_ERROR);
}
