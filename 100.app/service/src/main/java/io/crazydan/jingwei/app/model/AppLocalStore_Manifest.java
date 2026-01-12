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

package io.crazydan.jingwei.app.model;

import io.crazydan.jingwei.app.model._gen._AppLocalStore_Manifest;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.firstNonNull;
import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_PORTAL_CODE;

public class AppLocalStore_Manifest extends _AppLocalStore_Manifest {
    /** 仅包含门户应用的本地仓库清单 */
    public static final AppLocalStore_Manifest DEFAULT = new AppLocalStore_Manifest() {{
        AppLocalStore_App app = createPortalApp();

        AppLocalStore_EnabledApps enabledApps = new AppLocalStore_EnabledApps();
        enabledApps.addChild(app);

        setEnabledApps(enabledApps);

        freeze(true);
    }};

    public AppLocalStore_Manifest() {
    }

    @Override
    public AppLocalStore_EnabledApps getEnabledApps() {
        AppLocalStore_EnabledApps apps = firstNonNull(super.getEnabledApps(), AppLocalStore_EnabledApps.NONE);

        // 确保始终包含门户应用
        AppLocalStore_App portalApp = apps.getChild(CFG_APP_PORTAL_CODE.get());
        if (portalApp == null) {
            apps = apps.cloneInstance();
            apps.getChildren().add(0, createPortalApp());
        }
        return apps;
    }

    private static AppLocalStore_App createPortalApp() {
        AppLocalStore_App app = new AppLocalStore_App();
        app.setCode(CFG_APP_PORTAL_CODE.get());

        return app;
    }
}
