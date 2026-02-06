/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2026 Crazydan Studio <https://studio.crazydan.org>
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

package io.crazydan.jingwei.tools.browser;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Locale;
import io.nop.api.core.config.IConfigReference;
import io.nop.api.core.util.SourceLocation;

import static io.nop.api.core.config.AppConfig.varRef;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-06
 */
@Locale("zh-CN")
public interface ToolBrowserConfigs {
    SourceLocation s_loc = SourceLocation.fromClass(ToolBrowserConfigs.class);

    @Description("浏览器控制服务绑定的地址")
    IConfigReference<String> CFG_BROWSER_SERVER_HOST = varRef(s_loc,
                                                              "jingwei.tools.browser.server-host",
                                                              String.class,
                                                              "localhost");
    @Description("浏览器控制服务监听的端口号")
    IConfigReference<Integer> CFG_BROWSER_SERVER_PORT = varRef(s_loc,
                                                               "jingwei.tools.browser.server-port",
                                                               Integer.class,
                                                               null);

    @Description("浏览器控制服务的数据目录，用于存放配置、执行本体等数据")
    IConfigReference<String> CFG_BROWSER_DATA_DIR = varRef(s_loc, "jingwei.tools.browser.data-dir", String.class, null);
    @Description("浏览器控制服务的 API Token")
    IConfigReference<String> CFG_BROWSER_API_TOKEN = varRef(s_loc,
                                                            "jingwei.tools.browser.api-token",
                                                            String.class,
                                                            StringHelper.generateUUID());
}
