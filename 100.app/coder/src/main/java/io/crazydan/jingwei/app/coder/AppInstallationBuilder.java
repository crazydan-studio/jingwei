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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppInstallation_CoderResource;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.AppInstallation_ModelResources;
import io.crazydan.jingwei.app.model.AppInstallation_OrmResources;
import io.crazydan.jingwei.app.model.AppInstallation_PageResources;
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource;
import io.crazydan.jingwei.app.model.AppReleasing_CoderResource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;

import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_ORM;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_SRC;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_FILE_MODEL_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_FILE_UI_DESIGN;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-17
 */
public class AppInstallationBuilder extends AppCodeGenerator {

    public AppInstallation_Manifest install(
            AppReleasing_Manifest releasingManifest, File appModelDir, File appPageDir) {

        installModels(releasingManifest, appModelDir);
        installPages(releasingManifest, appPageDir);

        installCoders(releasingManifest, appModelDir);

        AppInstallation_Manifest manifest = AppInstallation_Manifest.from(releasingManifest);
        recordModels(manifest, appModelDir);
        recordPages(manifest, appPageDir);

        File manifestFile = new File(appModelDir, APP_MANIFEST_FILE);
        IResource manifestResource = new FileResource('/' + APP_MANIFEST_FILE, manifestFile);

        AppModelHelper.saveAppInstallationManifest(manifest, manifestResource);

        return AppModelHelper.loadAppInstallationManifest(manifestResource);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected void installModels(AppReleasing_Manifest manifest, File targetDir) {
        AppReleasing_ArtifactResource artifactResource = manifest.getArtifactResource();

        List<AppPackage_Resource> orms = artifactResource.getOrms();
        List<AppPackage_Resource> models = artifactResource.getModels();

        // 从设计资源构建
        if (orms.isEmpty() || models.isEmpty()) {
            buildModels(manifest, targetDir);
        }
        // 直接释放已构建资源
        else {
            Map<String, List<AppPackage_Resource>> resources = //
                    Map.of(APP_DIR_ORM, orms, APP_DIR_MODEL, models);

            resources.forEach((dirName, list) -> {
                list.forEach((source) -> {
                    String targetFilePath = dirName + '/' + source.getName();
                    File targetFile = new File(targetDir, targetFilePath);

                    source.getResource().saveToFile(targetFile);
                });
            });
        }
    }

    protected void installPages(AppReleasing_Manifest manifest, File targetDir) {
        AppReleasing_ArtifactResource artifactResource = manifest.getArtifactResource();

        List<AppPackage_Resource> pages = artifactResource.getPages();

        // Note: 若存在已构建的页面资源，则将其释放到应用静态资源目录，否则，延迟到首次访问时再主动构建
        pages.forEach((source) -> {
            String targetFilePath = source.getName();
            File targetFile = new File(targetDir, targetFilePath);

            source.getResource().saveToFile(targetFile);
        });
    }

    protected void installCoders(AppReleasing_Manifest manifest, File targetDir) {
        AppReleasing_CoderResource coder = manifest.getCoderResource();

        Map<String, AppPackage_Resource> resources = //
                Map.of(APP_FILE_MODEL_DESIGN, coder.getModelDesign(), APP_FILE_UI_DESIGN, coder.getUiDesign());

        resources.forEach((targetFilePath, source) -> {
            if (source != null) {
                File targetFile = new File(targetDir, APP_DIR_SRC + '/' + targetFilePath);
                source.getResource().saveToFile(targetFile);
            }
        });
    }

    /** 根据模型设计构建模型资源（{@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz}） */
    public void buildModels(AppReleasing_Manifest manifest, File targetDir) {
        AppPackage_Resource source = manifest.getCoderResource().getModelDesign();
        if (source == null) {
            return;
        }

        IResource resource = source.getResource();
        AppCodeGenConfig genConfig = AppCodeGenConfig.from(manifest);

        genModels(resource, targetDir, genConfig);
    }

    /** 根据页面设计构建应用页面 */
    public void buildPages(AppInstallation_Manifest manifest, File targetDir) {
        AppPackage_Resource source = manifest.getCoderResource().getUiDesign();
        if (source == null) {
            return;
        }

        IResource resource = source.getResource();
        AppCodeGenConfig genConfig = AppCodeGenConfig.from(manifest);

        genPages(resource, targetDir, genConfig);

        recordPages(manifest, targetDir);
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

        manifest.setCoderResource(new AppInstallation_CoderResource());
        paths = FileHelper.findFilePaths(targetDir, APP_DIR_SRC + "/**/*", true, true);
        paths.forEach((path) -> {
            AppPackage_Resource pkg = AppPackage_Resource.fromPath(path);

            if (path.endsWith('/' + APP_FILE_MODEL_DESIGN)) {
                manifest.getCoderResource().setModelDesign(pkg);
            } else if (path.endsWith('/' + APP_FILE_UI_DESIGN)) {
                manifest.getCoderResource().setUiDesign(pkg);
            }
        });
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
