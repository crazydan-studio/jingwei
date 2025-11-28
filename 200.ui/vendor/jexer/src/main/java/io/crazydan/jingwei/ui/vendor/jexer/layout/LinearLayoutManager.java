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

package io.crazydan.jingwei.ui.vendor.jexer.layout;

import io.crazydan.duzhou.framework.ui.XuiLayout;
import jexer.TWidget;
import jexer.event.TResizeEvent;
import jexer.layout.LayoutManager;

/**
 * 线性布局器
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-28
 */
public class LinearLayoutManager implements LayoutManager {
    private final XuiLayout layout;

    public LinearLayoutManager(XuiLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onResize(TResizeEvent resize) {

    }

    @Override
    public void add(TWidget child) {

    }

    @Override
    public void remove(TWidget child) {

    }

    @Override
    public void resetSize(TWidget child) {

    }
}
