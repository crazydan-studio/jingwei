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

package io.crazydan.jingwei.ui.vendor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.crazydan.duzhou.framework.ui.XuiLayout;
import io.crazydan.jingwei.ui.schema.component.XuiComponent;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplate;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNode;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeAny;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeDispatch;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeKeyed;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeLayout;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeNative;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplateNodeText;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.xlang.api.XLang;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-26
 */
public class XuiComponentTreeNode {
    public static final String VAR_NAME_PROPS = "props";
    public static final String VAR_PROP_NAME_CHILDREN = "$children";
    public static final String VAR_PROP_NAME_SLOT = "$slot";
    public static final String VAR_PROP_NAME_INNER_TEXT = "$innerText";

    public final String key;
    public final XuiLayout layout;
    public final List<XuiComponentTreeNode> children;

    public static XuiComponentTreeNode build(XuiComponent component, IEvalScope scope) {
        return buildNode(null, component, scope);
    }

    XuiComponentTreeNode(String key, XuiLayout layout, List<XuiComponentTreeNode> children) {
        this.key = key;
        this.layout = layout;
        this.children = children;
    }

    protected static XuiComponentTreeNode buildNode(String key, XuiComponent component, IEvalScope scope) {
        // Note: 生成的组建模版树中已不再包含 <if/>、<for/> 等控制节点
        XuiComponentTemplate template = component.evalTemplate(scope);

        XuiLayout layout = null;
        List<XuiComponentTreeNode> children = new ArrayList<>();

        for (XuiComponentTemplateNodeKeyed child : template.getChildren()) {
            if (child instanceof XuiComponentTemplateNodeLayout) {
                layout = ((XuiComponentTemplateNodeLayout) child).getType();
            } else if (child instanceof XuiComponentTemplateNodeDispatch) {
                XuiComponentTemplateNodeDispatch dispatch = (XuiComponentTemplateNodeDispatch) child;
            }

            if (child instanceof XuiComponentTemplateNodeText //
                || child instanceof XuiComponentTemplateNodeAny //
            ) {
                children.add(buildChildNode(child, component));
            } //
            else if (child instanceof XuiComponentTemplateNodeNative) {
            }
        }

        return new XuiComponentTreeNode(key, layout, children);
    }

    protected static XuiComponentTreeNode buildChildNode(XuiComponentTemplateNodeKeyed child, XuiComponent component) {
        XuiComponent childComponent = component.loadTagComponent(child);

        String childKey = child.getXuiName();
        Props props = new Props(child);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(VAR_NAME_PROPS, props);

        return buildNode(childKey, childComponent, scope);
    }

    static class Props implements Map<String, Object> {
        private final XuiComponentTemplateNodeKeyed node;

        Props(XuiComponentTemplateNodeKeyed node) {
            this.node = node;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return VAR_PROP_NAME_INNER_TEXT.equals(key) //
                   || VAR_PROP_NAME_CHILDREN.equals(key) //
                   || VAR_PROP_NAME_SLOT.equals(key) //
                   || this.node.prop_has((String) key) //
                    ;
        }

        @Override
        public Object get(Object key) {
            boolean isText = this.node instanceof XuiComponentTemplateNodeText;
            if (VAR_PROP_NAME_INNER_TEXT.equals(key)) {
                if (isText) {
                    return ((XuiComponentTemplateNodeText) this.node).getInnerText();
                }
                return ((XuiComponentTemplateNode) this.node).getInnerText();
            } //
            else if (VAR_PROP_NAME_CHILDREN.equals(key)) {
                if (isText) {
                    return List.of();
                }
                return ((XuiComponentTemplateNode) this.node).getCustomOrTextChildren();
            } //
            else if (VAR_PROP_NAME_SLOT.equals(key)) {
                if (isText) {
                    return Map.of();
                }
                return ((XuiComponentTemplateNode) this.node).getSlottables();
            }

            if (this.node.prop_has((String) key)) {
                return this.node.prop_get((String) key);
            }
            return null;
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsValue(Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object put(String key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(Object key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<String> keySet() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Object> values() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            throw new UnsupportedOperationException();
        }
    }
}
