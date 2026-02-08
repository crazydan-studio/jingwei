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

package io.crazydan.jingwei.app;

import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Locale;
import io.nop.api.core.config.IConfigReference;
import io.nop.api.core.util.SourceLocation;

import static io.nop.api.core.config.AppConfig.varRef;
import static io.nop.api.core.config.AppConfig.withPlaceholder;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-07
 */
@Locale("zh-CN")
public interface AppCoreConfigs {
    SourceLocation s_loc = SourceLocation.fromClass(AppCoreConfigs.class);

    @Description("门户（系统入口）应用的应用标识")
    IConfigReference<String> CFG_APP_PORTAL_CODE = //
            varRef(s_loc, "jingwei.app.portal-code", String.class, null);

    @Description("应用的静态资源目录，用于存放应用的 Web 静态资源。"
                 + "应用在其中的文件结构为 /app/{appCode}/index.js, /app/{appCode}/index.css")
    IConfigReference<String> CFG_APP_STATIC_DIR = //
            withPlaceholder(varRef(s_loc, "jingwei.app.static-dir", String.class, null));
    // Note: 按照 Nop 模块的两级目录组织应用
    @Description("应用的安装目录，用于存放应用的模块资源。"
                 + "其文件结构为 /manifest.xml, /app/{appCode}/model, /app/{appCode}/orm")
    IConfigReference<String> CFG_APP_INSTALL_DIR = //
            withPlaceholder(varRef(s_loc, "jingwei.app.install-dir", String.class, null));
}
