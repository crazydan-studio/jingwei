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

package io.crazydan.jingwei.app.coder;

import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Locale;
import io.nop.api.core.config.IConfigReference;
import io.nop.api.core.util.SourceLocation;

import static io.nop.api.core.config.AppConfig.varRef;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-07
 */
@Locale("zh-CN")
public interface AppCoderConfigs {
    SourceLocation s_loc = SourceLocation.fromClass(AppCoderConfigs.class);

    @Description("npm 可执行文件路径")
    IConfigReference<String> CFG_APP_BUILD_NPM_PATH = //
            varRef(s_loc, "jingwei.app.build.npm.path", String.class, null);
    @Description("共享的 node_modules 目录路径。必须提前在该目录中安装构建所需的依赖包，构建过程中不会尝试更新")
    IConfigReference<String> CFG_APP_BUILD_NODE_MODULES_PATH = //
            varRef(s_loc, "jingwei.app.build.node_modules.path", String.class, null);
}
