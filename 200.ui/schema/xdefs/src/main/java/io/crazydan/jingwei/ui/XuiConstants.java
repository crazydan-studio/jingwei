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

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-17
 */
public interface XuiConstants {

    String XDSL_SCHEMA_APP = "/jingwei/ui/schema/app.xdef";
    String XDSL_SCHEMA_PAGE = "/jingwei/ui/schema/page.xdef";
    String XDSL_SCHEMA_COMPONENT = "/jingwei/ui/schema/component.xdef";
    String XDSL_SCHEMA_COMPONENT_TEMPLATE = "/jingwei/ui/schema/component/template.xdef";
    String XDSL_SCHEMA_COMPONENT_IMPORT = "/jingwei/ui/schema/component/import.xdef";
    String XDSL_SCHEMA_COMPONENT_MESSAGE = "/jingwei/ui/schema/component/message.xdef";

    String TAG_NAME_TEMPLATE = "template";
    String TAG_NAME_IF = "if";
    String TAG_NAME_FOR = "for";
    String TAG_NAME_CHOOSE = "choose";
    String TAG_NAME_WHEN = "when";
    String TAG_NAME_OTHERWISE = "otherwise";

    String ATTR_NAME_XUI_ID = "xui-id";
    /** 用于记录在 Xpl &lt;for/> 标签中组件的原始 {@link #ATTR_NAME_XUI_ID 唯一标识} */
    String ATTR_NAME_XUI_ID_RAW = "raw:" + ATTR_NAME_XUI_ID;
}
