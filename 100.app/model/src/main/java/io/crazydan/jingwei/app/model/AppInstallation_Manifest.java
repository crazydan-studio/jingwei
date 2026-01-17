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

import io.crazydan.jingwei.app.model._gen._AppInstallation_Manifest;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.firstNonNull;

public class AppInstallation_Manifest extends _AppInstallation_Manifest {

    public AppInstallation_Manifest() {
    }

    @Override
    public AppInstallation_ModelResources getModelResources() {
        return firstNonNull(super.getModelResources(), AppInstallation_ModelResources.NONE);
    }

    @Override
    public AppInstallation_OrmResources getOrmResources() {
        return firstNonNull(super.getOrmResources(), AppInstallation_OrmResources.NONE);
    }

    @Override
    public AppInstallation_PageResources getPageResources() {
        return firstNonNull(super.getPageResources(), AppInstallation_PageResources.NONE);
    }

    @Override
    public AppInstallation_CoderResource getCoderResource() {
        return firstNonNull(super.getCoderResource(), AppInstallation_CoderResource.NONE);
    }
}
