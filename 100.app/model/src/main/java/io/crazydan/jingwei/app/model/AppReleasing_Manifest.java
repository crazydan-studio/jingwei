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

import io.crazydan.jingwei.app.model._gen._AppReleasing_Manifest;
import io.nop.api.core.util.INeedInit;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.firstNonNull;

public class AppReleasing_Manifest extends _AppReleasing_Manifest implements INeedInit {

    public AppReleasing_Manifest() {
    }

    @Override
    public void init() {
        // TODO artifactResource 中的 name 必须设置
        // TODO 必须属性校验
        // TODO requirementResource、coderResource 必须
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public AppReleasing_CoderResource getCoderResource() {
        return firstNonNull(super.getCoderResource(), AppReleasing_CoderResource.NONE);
    }

    @Override
    public AppReleasing_RequirementResource getRequirementResource() {
        return firstNonNull(super.getRequirementResource(), AppReleasing_RequirementResource.NONE);
    }

    /** @return 始终不返回 {@code null} */
    @Override
    public AppReleasing_ArtifactResource getArtifactResource() {
        return firstNonNull(super.getArtifactResource(), AppReleasing_ArtifactResource.NONE);
    }
}
