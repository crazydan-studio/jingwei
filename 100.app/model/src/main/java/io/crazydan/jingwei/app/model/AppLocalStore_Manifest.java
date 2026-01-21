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

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model._gen._AppLocalStore_Manifest;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.firstNonNull;

public class AppLocalStore_Manifest extends _AppLocalStore_Manifest {

    public AppLocalStore_Manifest() {
    }

    @Override
    public AppLocalStore_EnabledApps getEnabledApps() {
        return firstNonNull(super.getEnabledApps(), AppLocalStore_EnabledApps.NONE);
    }

    public String getPortalAppCode() {
        AppLocalStore_App app = getPortalApp();
        return app != null && StringHelper.isNotBlank(app.getCode()) ? app.getCode() : null;
    }
}
