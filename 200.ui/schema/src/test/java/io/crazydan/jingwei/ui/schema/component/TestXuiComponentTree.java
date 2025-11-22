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

package io.crazydan.jingwei.ui.schema.component;

import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.nop.api.core.exceptions.NopException;
import io.nop.core.lang.json.JsonTool;
import io.nop.core.lang.xml.XNode;
import io.nop.core.resource.component.ResourceComponentManager;
import io.nop.xlang.xdsl.DslModelHelper;
import org.junit.jupiter.api.Test;

import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_INVALID_TAG_NAME;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-20
 */
public class TestXuiComponentTree extends NopJunitTestCase {

    @Test
    public void test_valid_tree() {
        XuiComponent component = loadModel("/jingwei/ui/test-valid-component-tree.xui");

        String json = JsonTool.serialize(component, true);
        assertEquals(attachmentJsonText("valid-component-tree.json"), json);

        XNode node = toXNode(component);
        String xml = node.xml();
        assertEquals(attachmentXmlText("valid-component-tree.xml"), xml);
    }

    @Test
    public void test_invalid_tree() {
        try {
            loadModel("/jingwei/ui/test-invalid-component-tag-name.xui");
            fail("invalid-tag-name");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_INVALID_TAG_NAME.getErrorCode(), e.getErrorCode());
        }
        try {
            loadModel("/jingwei/ui/test-invalid-component-tag-name-in-depth.xui");
            fail("invalid-tag-name");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_INVALID_TAG_NAME.getErrorCode(), e.getErrorCode());
        }

        try {
            loadModel("/jingwei/ui/test-invalid-component-multi-layout.xui");
            fail("multiple-layout");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED.getErrorCode(), e.getErrorCode());
        }
        try {
            loadModel("/jingwei/ui/test-invalid-component-multi-layout-depth.xui");
            fail("multiple-layout");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED.getErrorCode(), e.getErrorCode());
        }

        try {
            loadModel("/jingwei/ui/test-invalid-component-slot-in-slot.xui");
            fail("slot-in-depth");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED.getErrorCode(), e.getErrorCode());
        }
        try {
            loadModel("/jingwei/ui/test-invalid-component-slot-in-slot-depth.xui");
            fail("slot-in-depth");
        } catch (NopException e) {
            assertEquals(ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED.getErrorCode(), e.getErrorCode());
        }
    }

    protected <T> T loadModel(String path) {
        return (T) ResourceComponentManager.instance().loadComponentModel(path);
    }

    protected XNode toXNode(XuiComponent component) {
        XNode node = DslModelHelper.dslModelToXNode("/jingwei/ui/schema/component.xdef", component);
        node.clearComment();
        node.removeAttrsWithPrefix("xmlns:");

        return node;
    }
}
