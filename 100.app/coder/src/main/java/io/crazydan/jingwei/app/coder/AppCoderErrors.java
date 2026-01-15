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

    ErrorCode ERR_BUILD_NPM_PATH_NOT_SPECIFIED = //
            define("jingwei.err.app.build.npm-path-not-specified",
                   "未通过配置项 {" + ARG_CONFIG_VAR + "} 指定应用页面代码构建工具 NPM 的可执行文件路径",
                   ARG_CONFIG_VAR);
}
