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

import java.util.List;
import java.util.Map;

import io.crazydan.jingwei.ui.XuiJunitTestCase;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplate;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XLangCompileTool;
import io.nop.xlang.ast.XLangOutputMode;
import io.nop.xlang.xdsl.DslModelHelper;
import org.junit.jupiter.api.Test;

import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_TEMPLATE;
import static io.crazydan.jingwei.ui.XuiConstants.XDSL_SCHEMA_COMPONENT_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-22
 */
public class TestXuiComponentDynamic extends XuiJunitTestCase {

    @Test
    public void test_vars() {
        XuiComponent component = loadModel("/jingwei/ui/test-dynamic-component-tree-vars.xui");

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue("props", Map.of("padding", "1u", "msg", "Welcome!"));

        XuiComponentTemplate template = evalTemplate(component, scope);
        String json = toJson(template);
        assertEquals(attachmentJsonText("vars.json"), json);

        XNode node = toXNode(template);
        String xml = cleanXml(toXml(node));
        assertEquals(cleanXml(attachmentXmlText("vars.xml")), xml);
    }

    @Test
    public void test_statement_if() {
        XuiComponent component = loadModel("/jingwei/ui/test-dynamic-component-tree-statement-if.xui");

        Map<?, ?>[] samples = new Map[] {
                Map.of("var", 1, "msg1", "Hello IF#1"), //
                Map.of("var", 2, "msg2", "Hello IF#2"), //
        };

        for (int i = 0; i < samples.length; i++) {
            Map<?, ?> props = samples[i];

            IEvalScope scope = XLang.newEvalScope();
            scope.setLocalValue("props", props);

            XuiComponentTemplate template = evalTemplate(component, scope);
            String json = toJson(template);
            assertEquals(attachmentJsonText("statement-if-" + i + ".json"), json);

            XNode node = toXNode(template);
            String xml = cleanXml(toXml(node));
            assertEquals(cleanXml(attachmentXmlText("statement-if-" + i + ".xml")), xml);
        }
    }

    @Test
    public void test_statement_choose() {
        XuiComponent component = loadModel("/jingwei/ui/test-dynamic-component-tree-statement-choose.xui");

        Map<?, ?>[] samples = new Map[] {
                Map.of("var", 1, "msg1", "Hello WHEN#1"), //
                Map.of("var", 2, "msg2", "Hello WHEN#2"), //
                Map.of("var", 3, "msg3", "Hello WHEN#3"), //
                Map.of("var", 4, "msg", "Hello OTHERWISE"), //
        };

        for (int i = 0; i < samples.length; i++) {
            Map<?, ?> props = samples[i];

            IEvalScope scope = XLang.newEvalScope();
            scope.setLocalValue("props", props);

            XuiComponentTemplate template = evalTemplate(component, scope);
            String json = toJson(template);
            assertEquals(attachmentJsonText("statement-choose-" + i + ".json"), json);

            XNode node = toXNode(template);
            String xml = cleanXml(toXml(node));
            assertEquals(cleanXml(attachmentXmlText("statement-choose-" + i + ".xml")), xml);
        }
    }

    @Test
    public void test_statement_for() {
        XuiComponent component = loadModel("/jingwei/ui/test-dynamic-component-tree-statement-for.xui");

        Map<?, ?>[] samples = new Map[] {
                Map.of("var", 1, "items", List.of("a", "b", "c")), //
                Map.of("var", 2, "items", List.of(12, 15, 21, 34)), //
        };

        for (int i = 0; i < samples.length; i++) {
            Map<?, ?> props = samples[i];

            IEvalScope scope = XLang.newEvalScope();
            scope.setLocalValue("props", props);

            XuiComponentTemplate template = evalTemplate(component, scope);
            String json = toJson(template);
            assertEquals(attachmentJsonText("statement-for-" + i + ".json"), json);

            XNode node = toXNode(template);
            String xml = cleanXml(toXml(node));
            assertEquals(cleanXml(attachmentXmlText("statement-for-" + i + ".xml")), xml);
        }
    }

    private XuiComponentTemplate evalTemplate(XuiComponent component, IEvalScope scope) {
        XNode dummy = new XNode("_");
        component.getDslNode().childByTag(TAG_NAME_TEMPLATE).cloneInstance().insertParent(dummy);

        XLangCompileTool compileTool = XuiComponent.newCompileTool();
        IEvalAction action = compileTool.compileTagBody(dummy, XLangOutputMode.node);
        XNode node = (XNode) action.invoke(scope);

        XuiComponentTemplate template = //
                (XuiComponentTemplate) DslModelHelper.parseDslNode(XDSL_SCHEMA_COMPONENT_TEMPLATE, node);
        template.init();

        return template;
    }
}
