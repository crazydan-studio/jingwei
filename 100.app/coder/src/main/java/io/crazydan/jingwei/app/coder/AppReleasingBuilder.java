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
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource;
import io.crazydan.jingwei.app.model.AppReleasing_CoderResource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.model.AppReleasing_RequirementResource;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.ifNotNullThenGet;
import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_ARTIFACT;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_ORM;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_PAGE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_DIR_SOURCE;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-17
 */
public class AppReleasingBuilder extends AppCodeGenerator {

    public AppReleasingBuilder() {
        super();
    }

    public AppReleasingBuilder(String pnpmPath) {
        super(pnpmPath);
    }

    public AppReleasing_Manifest build(String manifestVPath, File targetDir) {
        IResource manifestResource = AppModelHelper.getVfsResource(manifestVPath);

        return build(manifestResource, targetDir);
    }

    public AppReleasing_Manifest build(IResource manifestResource, File targetDir) {
        AppReleasing_Manifest manifest = AppModelHelper.loadAppReleasingManifest(manifestResource);

        return build(manifest, targetDir);
    }

    public AppReleasing_Manifest build(AppReleasing_Manifest manifest, File targetDir) {
        AppReleasing_CoderResource coderResource = manifest.getCoderResource();
        AppPackage_Resource modelDesign = coderResource.getModelDesign();
        AppPackage_Resource uiDesign = coderResource.getUiDesign();

        AppCodeGenConfig genConfig = AppCodeGenConfig.from(manifest);

        // 构建产物
        File artifactDir = new File(targetDir, APP_DIR_ARTIFACT);
        if (modelDesign != null) {
            IResource modelDesignResource = modelDesign.getResource();
            File modelTargetDir = artifactDir;

            genModels(modelDesignResource, modelTargetDir, genConfig);
        }
        if (uiDesign != null) {
            IResource uiDesignResource = uiDesign.getResource();
            File pageTargetDir = new File(artifactDir, APP_DIR_PAGE);

            genPages(uiDesignResource, pageTargetDir, genConfig);
        }

        // 复制源码
        File sourceDir = new File(targetDir, APP_DIR_SOURCE);
        copySource(manifest, sourceDir);

        // 构造最终的发布包清单
        File targetManifestFile = new File(targetDir, APP_MANIFEST_FILE);
        IResource targetManifestResource = new FileResource('/' + APP_MANIFEST_FILE, targetManifestFile);
        AppReleasing_Manifest completed = completeManifest(manifest, artifactDir);

        AppModelHelper.saveAppReleasingManifest(completed, targetManifestResource);

        return AppModelHelper.loadAppReleasingManifest(targetManifestResource);
    }

    private void copySource(AppReleasing_Manifest manifest, File targetDir) {
        AppPackage_Resource[] pkgResources = new AppPackage_Resource[] {
                manifest.getRequirementResource().getModelDesign(), manifest.getRequirementResource().getUiDesign(),
                //
                manifest.getCoderResource().getModelDesign(), manifest.getCoderResource().getUiDesign(),
                };

        for (AppPackage_Resource pkgResource : pkgResources) {
            if (pkgResource != null) {
                String name = StringHelper.fileName(pkgResource.getPath());

                IResource resource = pkgResource.getResource();
                File targetFile = new File(targetDir, name);

                resource.saveToFile(targetFile);
            }
        }
    }

    private AppReleasing_Manifest completeManifest(AppReleasing_Manifest manifest, File artifactDir) {
        AppReleasing_Manifest cloned = manifest.cloneInstance();

        cloned.setRequirementResource(new AppReleasing_RequirementResource());
        cloned.setCoderResource(new AppReleasing_CoderResource());
        cloned.setArtifactResource(new AppReleasing_ArtifactResource());

        Function<AppPackage_Resource, AppPackage_Resource> cloneResource = (source) -> {
            String name = StringHelper.fileName(source.getPath());

            AppPackage_Resource target = source.cloneInstance();
            target.setPath(APP_DIR_SOURCE + '/' + name);

            return target;
        };

        AppPackage_Resource resource;
        // ====================================================
        AppReleasing_RequirementResource requirement = manifest.getRequirementResource();

        resource = requirement.getModelDesign();
        resource = ifNotNullThenGet(resource, cloneResource);
        cloned.getRequirementResource().setModelDesign(resource);

        resource = requirement.getUiDesign();
        resource = ifNotNullThenGet(resource, cloneResource);
        cloned.getRequirementResource().setUiDesign(resource);

        // ====================================================
        AppReleasing_CoderResource coder = manifest.getCoderResource();

        resource = coder.getModelDesign();
        resource = ifNotNullThenGet(resource, cloneResource);
        cloned.getCoderResource().setModelDesign(resource);

        resource = coder.getUiDesign();
        resource = ifNotNullThenGet(resource, cloneResource);
        cloned.getCoderResource().setUiDesign(resource);

        // ====================================================
        List<String> paths;
        List<AppPackage_Resource> resources;
        Function<String, AppPackage_Resource> pathMapper = (path) -> {
            String name = StringHelper.nextPart(path, '/');

            AppPackage_Resource r = new AppPackage_Resource();
            r.setName(name);
            r.setPath(APP_DIR_ARTIFACT + '/' + path);

            return r;
        };

        paths = FileHelper.findFilePaths(artifactDir, APP_DIR_ORM + "/**/*", true, true);
        resources = paths.stream().map(pathMapper).collect(Collectors.toList());
        cloned.getArtifactResource().setOrms(resources);

        paths = FileHelper.findFilePaths(artifactDir, APP_DIR_MODEL + "/**/*", true, true);
        resources = paths.stream().map(pathMapper).collect(Collectors.toList());
        cloned.getArtifactResource().setModels(resources);

        paths = FileHelper.findFilePaths(artifactDir, APP_DIR_PAGE + "/**/*", true, true);
        resources = paths.stream().map(pathMapper).collect(Collectors.toList());
        cloned.getArtifactResource().setPages(resources);

        return cloned;
    }
}
