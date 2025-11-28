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

package io.crazydan.jingwei.ui.vender.jexer;

import java.util.Map;

import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.crazydan.jingwei.ui.vendor.jexer.JexerApp;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-25
 */
public class TestJexerApp extends NopJunitTestCase {

    @Test
    public void test_render_page() throws Exception {
        JexerApp app = new JexerApp();
        app.addToolMenu();

        Object data = Map.of("me", Map.of("notExist", true));
        app.render("/jingwei/ui/page/main.page.xui", data);

        //(new Thread(app)).start();
        app.run();
    }
}
