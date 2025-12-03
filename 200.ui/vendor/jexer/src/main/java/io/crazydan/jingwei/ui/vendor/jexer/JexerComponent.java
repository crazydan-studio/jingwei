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

package io.crazydan.jingwei.ui.vendor.jexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import io.crazydan.duzhou.framework.ui.layout.XuiLayoutNode;
import io.crazydan.duzhou.framework.ui.layout.XuiLayoutProps;
import io.crazydan.duzhou.framework.ui.layout.XuiLayoutSize;
import io.crazydan.jingwei.ui.vendor.XuiComponentTreeNode;
import io.crazydan.jingwei.ui.vendor.jexer.component.JexerBox;
import io.nop.api.core.exceptions.NopException;
import jexer.TWidget;

import static io.crazydan.jingwei.ui.vendor.XuiVendorErrors.ERR_COMPONENT_NATIVE_NOT_REGISTERED;
import static io.nop.xlang.XLangErrors.ARG_NAME;

/**
 * 连接 {@link XuiComponentTreeNode} 与 {@link TWidget} 的中间模型
 * <p/>
 * Note:<ul>
 * <li>组件树 {@link XuiComponentTreeNode} 中的节点均为确定的可显示组件，
 * 其与布局节点 {@link XuiLayoutNode} 不是按层级对应的；
 * </li>
 * <li>在布局树 {@link XuiComponentTreeNode#layout}
 * 中的布局节点（{@link XuiLayoutNode#getType() 类型}不是
 * {@link XuiLayoutNode.Type#item} 的节点）对应容器组件 {@link JexerBox} 以控制内部组件的位置和尺寸；
 * </li>
 * </ul>
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-28
 */
public abstract class JexerComponent {
    public interface Creator {
        JexerComponent create(JexerComponent parent, XuiComponentTreeNode node);
    }

    private static final XuiLayoutNode LAYOUT_ANY_ITEM = XuiLayoutNode.item(null, ".+");

    public final TWidget widget;

    public final XuiLayoutNode layout;
    public final List<JexerComponent> children;

    protected JexerComponent(JexerComponent parent, XuiComponentTreeNode node) {
        this(parent.widget, node);
    }

    protected JexerComponent(JexerComponent parent, XuiComponentTreeNode node, XuiLayoutNode layout) {
        this(parent.widget, node, layout);
    }

    protected JexerComponent(TWidget parent, XuiComponentTreeNode node) {
        this( //
              parent, node, //
              node != null && node.layout != null //
              ? node.layout.getRoot() //
              : XuiLayoutNode.column(null, List.of(LAYOUT_ANY_ITEM)) //
        );
    }

    protected JexerComponent(TWidget parent, XuiComponentTreeNode node, XuiLayoutNode layout) {
        this.widget = createWidget(parent, node);

        this.layout = layout;
        this.children = layoutChildren(node, layout);

        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        onResize(parentWidth, parentHeight);
    }

    /** 响应容器尺寸变化，重新布局子节点的位置和尺寸 */
    public void onResize(int parentWidth, int parentHeight) {
        int xGap = 0;
        int yGap = 0;
        int offsetX = 0;
        int offsetY = 0;
        int contentWidth = 0;
        int contentHeight = 0;

        // 调整子组件的相对位置
        for (JexerComponent componentChild : this.children) {
            int w = componentChild.widget.getWidth();
            int h = componentChild.widget.getHeight();
            componentChild.widget.setX(offsetX);
            componentChild.widget.setY(offsetY);

            switch (this.layout.getType()) {
                case row: {
                    offsetX += w + xGap;
                    contentWidth = offsetX - xGap;
                    contentHeight = Math.max(contentHeight, h);
                    break;
                }
                case column: {
                    offsetY += h + yGap;
                    contentWidth = Math.max(contentWidth, w);
                    contentHeight = offsetY - yGap;
                    break;
                }
            }
        }

        XuiLayoutProps props = this.layout.getProps();
        // 更新当前组件的尺寸
        setLayoutSize(props.getWidth(), contentWidth, parentWidth, this.widget::setWidth);
        setLayoutSize(props.getHeight(), contentHeight, parentHeight, this.widget::setHeight);
    }

    protected List<JexerComponent> layoutChildren(XuiComponentTreeNode node, XuiLayoutNode layout) {
        List<JexerComponent> children = new ArrayList<>();

        // Note: 布局子节点仅包含多层嵌套的布局节点，以及当前组件树的直接子节点
        for (XuiLayoutNode layoutChild : layout.getChildren()) {
            List<JexerComponent> list = layoutChild(this, node, layoutChild);
            children.addAll(list);
        }

        return Collections.unmodifiableList(children);
    }

    protected List<JexerComponent> layoutChild(JexerComponent parent, XuiComponentTreeNode node, XuiLayoutNode layout) {
        switch (layout.getType()) {
            case item: {
                // 匹配组件树直接子节点
                return node.children.stream()
                                    .filter((n) -> layout.matched(n.key))
                                    .map((n) -> createComponent(parent, n))
                                    .collect(Collectors.toList());
            }
            default: {
                JexerBox box = new JexerBox(parent, node, layout);
                return List.of(box);
            }
        }
    }

    protected abstract TWidget createWidget(TWidget parent, XuiComponentTreeNode node);

    protected static JexerComponent createComponent(JexerComponent parent, XuiComponentTreeNode node) {
        if (node.nativeName == null) {
            return new JexerBox(parent, node);
        } //
        else {
            Creator creator = JexerComponentRegistry.get(node.nativeName);
            if (creator != null) {
                return creator.create(parent, node);
            } else {
                throw new NopException(ERR_COMPONENT_NATIVE_NOT_REGISTERED).param(ARG_NAME, node.nativeName);
            }
        }
    }

    protected static void setLayoutSize(XuiLayoutSize size, int contentSize, int parentSize, Consumer<Integer> setter) {
        switch (size.type) {
            case wrap_content: {
                setter.accept(contentSize);
                break;
            }
            case match_parent: {
                setter.accept(parentSize);
                break;
            }
            case fill_remains: {
                break;
            }
            case with_specified: {
                break;
            }
        }
    }
}
