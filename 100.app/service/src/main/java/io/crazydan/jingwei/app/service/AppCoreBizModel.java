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

package io.crazydan.jingwei.app.service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppPage;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_ModelResources;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_OrmResources;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_PageResources;
import io.crazydan.jingwei.app.model.manifest.AppPackage_Resource;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_ArtifactResource;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_Manifest;
import io.crazydan.jingwei.app.util.AppHelper;
import io.nop.api.core.annotations.biz.BizAction;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizQuery;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Name;
import io.nop.commons.cache.ICache;
import io.nop.commons.cache.MapCache;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;

import static io.crazydan.jingwei.app.AppConstants.APP_INSTALLATION_DIR_MODEL;
import static io.crazydan.jingwei.app.AppConstants.APP_INSTALLATION_DIR_ORM;
import static io.crazydan.jingwei.app.AppConstants.APP_INSTALLATION_DIR_PAGE;
import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_CLASSPATH_V_PATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_INSTALLATION_V_PATH;
import static io.crazydan.jingwei.app.AppConstants.VAR_APP_CODE;
import static io.crazydan.jingwei.app.AppConstants.VAR_PATH;

/**
 * 提供与应用相关的核心基础服务接口
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
@BizModel("App")
public class AppCoreBizModel {
    private final ICache<String, AppInstallation_Manifest> cache = MapCache.create("app-model-cache", true);

    /**
     * 加载指定应用
     * <p/>
     * 仅在首次访问应用时才加载其模型资源
     */
    @BizQuery
    public AppPage loadApp(@Name("appCode") String appCode) {
        this.cache.computeIfAbsentAsync(appCode, this::assureAppLoaded);

        AppPage page = new AppPage();

        return page;
    }

    @Description("确保应用已加载")
    @BizAction
    public AppInstallation_Manifest assureAppLoaded(@Name("appCode") String appCode) {
        AppInstallation_Manifest manifest = loadInstallationManifestFromDir(appCode);

        if (manifest == null) {
            manifest = installFromDb(appCode);
        }
        if (manifest == null) {
            manifest = installFromClasspath(appCode);
        }

        if (manifest != null) {
            loadAppModel(manifest);
        }

        return manifest != null ? manifest : AppInstallation_Manifest.NONE;
    }

    @Description("从 classpath 安装应用")
    @BizAction
    public AppInstallation_Manifest installFromClasspath(@Name("appCode") String appCode) {
        IResource resource = loadClasspathResource(appCode, APP_MANIFEST_FILE);
        AppReleasing_Manifest manifest = AppHelper.loadAppReleasingManifest(resource);
        if (manifest == null) {
            return null;
        }

        installToDir(manifest);

        return loadInstallationManifestFromDir(appCode);
    }

    @Description("从数据库安装应用")
    @BizAction
    public AppInstallation_Manifest installFromDb(@Name("appCode") String appCode) {
        AppInstallation_Manifest manifest = null;

        return manifest;
    }

    protected AppInstallation_Manifest loadInstallationManifestFromDir(String appCode) {
        IResource resource = loadInstallationResource(appCode, APP_MANIFEST_FILE);

        return AppHelper.loadAppInstallationManifest(resource);
    }

    protected void loadAppModel(AppInstallation_Manifest manifest) {
        manifest.getOrmResources().getBody().forEach((orm) -> {
            if ((APP_INSTALLATION_DIR_ORM + "/app.orm.xml").equals(orm.getPath())) {
                // TODO Load OrmModel
            }
        });

        manifest.getModelResources().getBody().forEach((model) -> {
            // TODO build GraphQLBizModel
        });
    }

    protected IResource loadInstallationResource(String appCode, String path) {
        return loadVfsResource(TEMPLATE_APP_INSTALLATION_V_PATH, appCode, path);
    }

    protected IResource loadClasspathResource(String appCode, String path) {
        return loadVfsResource(TEMPLATE_APP_CLASSPATH_V_PATH, appCode, path);
    }

    protected IResource loadVfsResource(String template, String appCode, String path) {
        Map<String, Object> params = Map.of(VAR_APP_CODE, appCode, VAR_PATH, path);
        String vPath = StringHelper.renderTemplate(template, params::get);

        return VirtualFileSystem.instance().getResource(vPath);
    }

    protected void installToDir(AppReleasing_Manifest releasingManifest) {
        String appCode = releasingManifest.getCode();
        AppReleasing_ArtifactResource artifactResource = releasingManifest.getArtifactResource();

        List<AppPackage_Resource> ormResources = artifactResource.getOrms();
        List<AppPackage_Resource> modelResources = artifactResource.getModels();
        List<AppPackage_Resource> pageResources = artifactResource.getPages();

        AppInstallation_Manifest manifest = new AppInstallation_Manifest();
        manifest.setCode(appCode);
        manifest.setVersion(releasingManifest.getVersion());
        manifest.setOrmResources(new AppInstallation_OrmResources());
        manifest.setModelResources(new AppInstallation_ModelResources());
        manifest.setPageResources(new AppInstallation_PageResources());

        installPackageResources(ormResources,
                                appCode,
                                APP_INSTALLATION_DIR_ORM,
                                manifest.getOrmResources()::addResource);
        installPackageResources(modelResources,
                                appCode,
                                APP_INSTALLATION_DIR_MODEL,
                                manifest.getModelResources()::addResource);
        // TODO 释放页面资源到 CFG_APP_STATIC_DIR
        installPackageResources(pageResources,
                                appCode,
                                APP_INSTALLATION_DIR_PAGE,
                                manifest.getPageResources()::addResource);

        IResource manifestResource = loadInstallationResource(appCode, APP_MANIFEST_FILE);
        AppHelper.saveAppInstallationManifest(manifest, manifestResource);
    }

    protected void installPackageResources(
            List<AppPackage_Resource> sources, String appCode, String subPath, Consumer<AppPackage_Resource> consumer) {
        sources.forEach((source) -> {
            AppPackage_Resource pkg = new AppPackage_Resource();
            pkg.setPath(subPath + '/' + source.getName());

            IResource resource = loadInstallationResource(appCode, pkg.getPath());
            source.getResource().saveToResource(resource);

            consumer.accept(pkg);
        });
    }
}
