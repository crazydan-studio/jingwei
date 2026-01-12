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
import io.crazydan.jingwei.app.model._gen._AppPackage_Resource;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;

public class AppPackage_Resource extends _AppPackage_Resource {

    public AppPackage_Resource() {
    }

    /** 根据当前对象的资源位置加载资源 */
    public IResource getResource() {
        String inDir = StringHelper.removeLastPart(getLocation().getPath(), '/');
        String path = StringHelper.normalizePath(getPath());
        String vPath = StringHelper.appendPath(inDir, path);

        return VirtualFileSystem.instance().getResource(vPath, true);
    }
}
