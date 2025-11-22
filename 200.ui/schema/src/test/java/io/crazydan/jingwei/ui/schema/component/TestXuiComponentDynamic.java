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

import java.util.Map;

import io.crazydan.jingwei.ui.XuiJunitTestCase;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeRoot;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XLangCompileTool;
import io.nop.xlang.ast.XLangOutputMode;
import io.nop.xlang.xdsl.DslModelHelper;
import org.junit.jupiter.api.Test;

import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_TEMPLATE;
import static io.crazydan.jingwei.ui.XuiConstants.XDSL_SCHEMA_COMPONENT_TREE;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-22
 */
public class TestXuiComponentDynamic extends XuiJunitTestCase {

    @Test
    public void test_dynamic_tree() {
        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue("$props", Map.of());
    }

    private XuiComponentTreeNodeRoot evalTreeRootNode(XuiComponent component, IEvalScope scope) {
        XNode template = component.getDslNode().childByTag(TAG_NAME_TEMPLATE);

        XLangCompileTool compileTool = XuiComponent.newCompileTool();
        IEvalAction action = compileTool.compileTagBody(template, XLangOutputMode.node);
        XNode node = (XNode) action.invoke(scope);

        return (XuiComponentTreeNodeRoot) DslModelHelper.parseDslNode(XDSL_SCHEMA_COMPONENT_TREE, node);
    }
}
