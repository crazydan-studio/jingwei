/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2025 Crazydan Studio <https://studio.crazydan.org>
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

package io.crazydan.jingwei.ui;

import io.nop.api.core.exceptions.ErrorCode;

import static io.nop.api.core.exceptions.ErrorCode.define;
import static io.nop.xlang.XLangErrors.ARG_NAME;
import static io.nop.xlang.XLangErrors.ARG_TAG_NAME;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-17
 */
public interface XuiErrors {

    ErrorCode ERR_COMPONENT_INVALID_TAG_NAME = //
            define("jingwei.err.ui.component.invalid-tag-name",
                   "组件标签名 [{tagName}] 不符合规范。"
                   + "其需为字母、数字、下划线组成的驼峰形式，且首字母必须大写，"
                   + "如 Button、Button_Ext",
                   ARG_TAG_NAME);
    ErrorCode ERR_COMPONENT_MULTIPLE_LAYOUTS_NOT_ALLOWED = //
            define("jingwei.err.ui.component.multiple-layouts-not-allowed",
                   "不允许在 <{tagName}/> 标签中定义多个 <layout/>",
                   ARG_TAG_NAME);
    ErrorCode ERR_COMPONENT_MULTIPLE_DISPATCHES_NOT_ALLOWED = //
            define("jingwei.err.ui.component.multiple-dispatches-not-allowed",
                   "不允许在 <{tagName}/> 标签中定义消息名（{name}）重复的 <dispatch/>",
                   ARG_TAG_NAME,
                   ARG_NAME);
    ErrorCode ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED = //
            define("jingwei.err.ui.component.slot-in-depth-not-allowed", //
                   "不允许在 <slot/> 标签内嵌套使用 slot");
    ErrorCode ERR_COMPONENT_DSL_NODE_NOT_BOUND = //
            define("jingwei.err.ui.component.dsl-node-not-bound", //
                   "组件未与其 XNode 节点绑定，建议在 xdef 元模型中的 <xdef:post-parse/> 脚本中做全局自动绑定，如：_dsl_model.setDslNode(_dsl_root)");
}
