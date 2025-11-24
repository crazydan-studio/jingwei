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

import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.crazydan.jingwei.ui.schema.component.XuiComponent;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplate;
import io.nop.core.lang.json.JsonTool;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.resource.component.ResourceComponentManager;
import io.nop.xlang.xdsl.DslModelHelper;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-22
 */
public abstract class XuiJunitTestCase extends NopJunitTestCase {

    protected XNode loadNode(String dslPath) {
        return XNodeParser.instance().parseFromVirtualPath(dslPath);
    }

    protected <T> T loadModel(String dslPath) {
        return (T) ResourceComponentManager.instance().loadComponentModel(dslPath);
    }

    protected String toJson(Object obj) {
        return JsonTool.serialize(obj, true);
    }

    protected XNode toXNode(XuiComponent component) {
        return toXNode(XuiConstants.XDSL_SCHEMA_COMPONENT, component);
    }

    protected XNode toXNode(XuiComponentTemplate root) {
        return toXNode(XuiConstants.XDSL_SCHEMA_COMPONENT_TEMPLATE, root);
    }

    protected XNode toXNode(String xdefPath, Object model) {
        return DslModelHelper.dslModelToXNode(xdefPath, model);
    }

    protected String toXml(XNode node) {
        return node.clearComment().clearAttrs().xml();
    }

    protected String cleanXml(String xml) {
        return xml.replaceAll("\n\\s*", "");
    }
}
