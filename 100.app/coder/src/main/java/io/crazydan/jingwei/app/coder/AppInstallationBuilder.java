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
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.AppInstallation_ModelResources;
import io.crazydan.jingwei.app.model.AppInstallation_OrmResources;
import io.crazydan.jingwei.app.model.AppInstallation_PageResources;
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;

import static io.crazydan.jingwei.app.AppConstants.APP_DIR_SOURCE;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_LOGO;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_MANIFEST;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_MODEL_DESIGN;
import static io.crazydan.jingwei.app.AppConstants.APP_FILE_UI_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_ORM;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_SRC;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-17
 */
public class AppInstallationBuilder extends AppCodeGenerator {

    public AppInstallation_Manifest install(AppReleasing_Manifest releasingManifest, File appModelDir) {
        installCoders(releasingManifest, appModelDir);

        // Note: 页面延迟到首次访问应用时构建
        buildModels(releasingManifest, appModelDir);

        //
        AppInstallation_Manifest manifest = AppInstallation_Manifest.from(releasingManifest);
        recordModels(manifest, appModelDir);

        //
        File manifestFile = new File(appModelDir, APP_FILE_MANIFEST);
        IResource manifestResource = new FileResource('/' + APP_FILE_MANIFEST, manifestFile);

        AppModelHelper.saveAppInstallationManifest(manifest, manifestResource);

        return AppModelHelper.loadAppInstallationManifest(manifestResource);
    }

    /** 根据页面设计构建应用页面 */
    public AppInstallation_Manifest installPages(AppInstallation_Manifest manifest, File targetDir) {
        IResource resource = getResource(manifest, APP_DIR_SRC + '/' + APP_FILE_UI_DESIGN);
        if (resource == null) {
            return manifest;
        }

        AppCodeGenConfig genConfig = AppCodeGenConfig.from(manifest);

        genPages(resource, targetDir, genConfig);
        recordPages(manifest, targetDir);

        IResource manifestResource = AppModelHelper.getVfsResource(manifest.resourcePath());
        AppModelHelper.saveAppInstallationManifest(manifest, manifestResource);

        return AppModelHelper.loadAppInstallationManifest(manifestResource);
    }

    /** 根据模型设计构建模型资源（{@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz}） */
    protected void buildModels(AppReleasing_Manifest manifest, File targetDir) {
        IResource resource = getResource(manifest, APP_DIR_SOURCE + '/' + APP_FILE_MODEL_DESIGN);
        if (resource == null) {
            return;
        }

        AppCodeGenConfig genConfig = AppCodeGenConfig.from(manifest);

        genModels(resource, targetDir, genConfig);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected void installCoders(AppReleasing_Manifest manifest, File targetDir) {
        String[] paths = new String[] {
                APP_FILE_LOGO, APP_FILE_MODEL_DESIGN, APP_FILE_UI_DESIGN
        };

        for (String path : paths) {
            File target = new File(targetDir, APP_DIR_SRC);
            copyResource(manifest, APP_DIR_SOURCE + '/' + path, target);
        }
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected void recordModels(AppInstallation_Manifest manifest, File targetDir) {
        List<String> paths;
        List<AppPackage_Resource> resources;
        Function<String, AppPackage_Resource> pathMapper = AppPackage_Resource::fromPath;

        manifest.setOrmResources(new AppInstallation_OrmResources());
        paths = FileHelper.findFilePaths(targetDir, APP_DIR_ORM + "/**/*", true, true);
        resources = paths.stream().map(pathMapper).collect(Collectors.toList());
        manifest.getOrmResources().setChildren(resources);

        manifest.setModelResources(new AppInstallation_ModelResources());
        paths = FileHelper.findFilePaths(targetDir, APP_DIR_MODEL + "/**/*", true, true);
        resources = paths.stream().map(pathMapper).collect(Collectors.toList());
        manifest.getModelResources().setChildren(resources);
    }

    protected void recordPages(AppInstallation_Manifest manifest, File targetDir) {
        manifest.setPageResources(new AppInstallation_PageResources());

        List<String> paths = FileHelper.findFilePaths(targetDir, "**/*", true, true);
        paths.forEach((path) -> {
            String name = path;
            if (path.endsWith(".br") || path.endsWith(".gz") || path.endsWith(".zst")) {
                name = StringHelper.removeLastPart(path, '.');
            }

            AppPackage_Resource pkg = AppPackage_Resource.fromPath(path);
            pkg.setName(name);

            manifest.getPageResources().addChild(pkg);
        });
    }
}
