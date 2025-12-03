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

import io.crazydan.jingwei.ui.vendor.XuiComponentTreeNode;
import io.crazydan.jingwei.ui.vendor.jexer.component.JexerBox;
import jexer.TDesktop;
import jexer.event.TResizeEvent;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-25
 */
public class JexerPage extends TDesktop {
    private final JexerBox root;

    JexerPage(JexerApp app, XuiComponentTreeNode node) {
        super(app);
        setActive(true);

        this.root = new JexerBox(this, node);
    }

    @Override
    public void onResize(TResizeEvent resize) {
        this.root.onResize(resize.getWidth(), resize.getHeight());
    }
}
