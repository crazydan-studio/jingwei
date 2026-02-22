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

import java.io.File;

import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;

import static io.crazydan.jingwei.app.AppConstants.APP_DIR_SOURCE;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_BIZ_REQUIREMENTS;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_LOGO;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_LOGO_REQUIREMENTS;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_MANIFEST;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_MODEL_DESIGN;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_MODEL_REQUIREMENTS;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_UI_DESIGN;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_UI_REQUIREMENTS;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-17
 */
public class AppReleasingBuilder extends AppCodeGenerator {

    public AppReleasing_Manifest build(String manifestVPath, File targetDir) {
        IResource manifestResource = AppModelHelper.getVfsResource(manifestVPath);

        return build(manifestResource, targetDir);
    }

    public AppReleasing_Manifest build(IResource manifestResource, File targetDir) {
        AppReleasing_Manifest manifest = AppModelHelper.loadAppReleasingManifest(manifestResource);

        return build(manifest, targetDir);
    }

    public AppReleasing_Manifest build(AppReleasing_Manifest manifest, File targetDir) {
        // 复制源码
        File sourceDir = new File(targetDir, APP_DIR_SOURCE);
        copySource(manifest, sourceDir);

        // 构造最终的发布包清单
        File targetManifestFile = new File(targetDir, APP_FILE_MANIFEST);
        IResource targetManifestResource = new FileResource('/' + APP_FILE_MANIFEST, targetManifestFile);

        AppModelHelper.saveAppReleasingManifest(manifest, targetManifestResource);

        return AppModelHelper.loadAppReleasingManifest(targetManifestResource);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void copySource(AppReleasing_Manifest manifest, File targetDir) {
        String[] paths = new String[] {
                APP_FILE_BIZ_REQUIREMENTS,
                APP_FILE_LOGO_REQUIREMENTS,
                APP_FILE_LOGO,
                APP_FILE_MODEL_REQUIREMENTS,
                APP_FILE_MODEL_DESIGN,
                APP_FILE_UI_REQUIREMENTS,
                APP_FILE_UI_DESIGN
        };

        for (String path : paths) {
            copyResource(manifest, APP_DIR_SOURCE + '/' + path, targetDir);
        }
    }
}
