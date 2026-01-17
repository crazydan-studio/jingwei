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

package io.crazydan.jingwei.app;

import io.nop.api.core.exceptions.ErrorCode;

import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;
import static io.nop.api.core.exceptions.ErrorCode.define;
import static io.nop.xlang.XLangErrors.ARG_CODE;

/**
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public interface AppCoreErrors {

    ErrorCode ERR_CFG_VALUE_NOT_SPECIFIED = //
            define("jingwei.err.app.cfg.value-not-specified",
                   "配置项 {" + ARG_CONFIG_VAR + "} 未指定有效值",
                   ARG_CONFIG_VAR);

    ErrorCode ERR_BIZ_APP_NOT_EXIST = //
            define("jingwei.err.app.biz.app-not-exist", //
                   "应用 {" + ARG_CODE + "} 不存在", ARG_CODE);
    ErrorCode ERR_BIZ_APP_NO_PAGE = //
            define("jingwei.err.app.biz.app-no-page", //
                   "应用 {" + ARG_CODE + "} 没有 UI 页面", ARG_CODE);
}
