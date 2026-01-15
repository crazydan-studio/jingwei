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

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.api.core.util.Guard;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;

import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.coder.AppCoderConfigs.CFG_APP_BUILD_NODE_MODULES_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConfigs.CFG_APP_BUILD_NPM_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.PACK_DIR_ARTIFACT;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TEMPLATE_DIR_PAGE;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-15
 */
public class AppReleasingPacker {
    private final String npmPath;
    private final String nodeModulesPath;

    public AppReleasingPacker() {
        this(CFG_APP_BUILD_NPM_PATH.get(), CFG_APP_BUILD_NODE_MODULES_PATH.get());
    }

    public AppReleasingPacker(String npmPath, String nodeModulesPath) {
        this.npmPath = StringHelper.normalizePath(npmPath);
        this.nodeModulesPath = StringHelper.normalizePath(nodeModulesPath);
    }

    /** 根据不完整的发布包清单进行资源构建，并将构建产物按发布包结构组织到目标目录中 */
    public AppReleasing_Manifest packTo(IResource manifestResource, File targetDir) {
        File artifactDir = new File(targetDir, PACK_DIR_ARTIFACT);

        AppReleasing_Manifest manifest = build(manifestResource, artifactDir, PACK_DIR_ARTIFACT + '/');

        File targetManifestFile = new File(targetDir, APP_MANIFEST_FILE);
        IResource targetManifestResource = new FileResource('/' + targetManifestFile.getName(), targetManifestFile);
        AppModelHelper.saveAppReleasingManifest(manifest, targetManifestResource);

        return manifest;
    }

    /** 根据不完整的发布包清单打包构建包含构建产物的完整发布包 */
    public AppReleasing_Manifest build(IResource manifestResource, File targetDir, String resourcePrefix) {
        AppReleasing_Manifest manifest = AppModelHelper.loadAppReleasingManifest(manifestResource);
        Guard.notNull(manifest, manifestResource + " doesn't exist");

        return build(manifest, targetDir, resourcePrefix);
    }

    /** 根据不完整的发布包清单打包构建包含构建产物的完整发布包 */
    public AppReleasing_Manifest build(AppReleasing_Manifest manifest, File targetDir, String resourcePrefix) {
        AppReleasing_Manifest newManifest = manifest.cloneInstance();
        newManifest.setArtifactResource(new AppReleasing_ArtifactResource());

        genModels(targetDir, newManifest, resourcePrefix);

        File targetPageDir = new File(targetDir, TEMPLATE_DIR_PAGE);
        genPages(targetPageDir, newManifest, resourcePrefix + TEMPLATE_DIR_PAGE + '/');

        return newManifest;
    }

    /** 根据模型设计生成应用模型定义 {@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz} */
    public void genModels(File targetDir, AppReleasing_Manifest manifest, String resourcePrefix) {
        AppPackage_Resource source = manifest.getCoderResource().getModelDesign();
        if (source == null) {
            return;
        }

        AppCodeGenerator gen = new AppCodeGenerator(this.npmPath, this.nodeModulesPath);
        AppCodeGenConfig genConfig = createGenConfig(manifest);

        IResource resource = source.getResource();
        gen.genModels(targetDir, resource, genConfig);

        AppReleasing_ArtifactResource artifactResource = manifest.getArtifactResource();
        if (artifactResource != AppReleasing_ArtifactResource.NONE) {
            AppPackage_Resource.fromPaths(gen.getOrms(), resourcePrefix, true).forEach(artifactResource::addOrm);
            AppPackage_Resource.fromPaths(gen.getModels(), resourcePrefix, true).forEach(artifactResource::addModel);
        }
    }

    /** 根据 UI 设计生成应用页面资源 */
    public void genPages(File targetDir, AppReleasing_Manifest manifest, String resourcePrefix) {
        AppPackage_Resource source = manifest.getCoderResource().getUiDesign();
        if (source == null) {
            return;
        }

        AppCodeGenerator gen = new AppCodeGenerator(this.npmPath, this.nodeModulesPath);
        AppCodeGenConfig genConfig = createGenConfig(manifest);

        IResource resource = source.getResource();
        gen.genPages(targetDir, resource, genConfig);

        AppReleasing_ArtifactResource artifactResource = manifest.getArtifactResource();
        if (artifactResource != AppReleasing_ArtifactResource.NONE) {
            AppPackage_Resource.fromPaths(gen.getPages(), resourcePrefix, true).forEach(artifactResource::addPage);
        }
    }

    public AppCodeGenConfig createGenConfig(AppReleasing_Manifest manifest) {
        AppCodeGenConfig genConfig = new AppCodeGenConfig();

        genConfig.setCode(manifest.getCode());
        genConfig.setBizDomain(manifest.getBizDomain());

        return genConfig;
    }
}
