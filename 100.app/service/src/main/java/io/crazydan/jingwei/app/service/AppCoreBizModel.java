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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.AppInstallation_ModelResources;
import io.crazydan.jingwei.app.model.AppInstallation_OrmResources;
import io.crazydan.jingwei.app.model.AppInstallation_PageResources;
import io.crazydan.jingwei.app.model.AppLocalStore_Manifest;
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.orm.AppOrmModelProvider;
import io.crazydan.jingwei.app.util.AppHelper;
import io.nop.api.core.annotations.biz.BizAction;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizMutation;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Name;
import io.nop.biz.api.IBizObjectManager;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;
import io.nop.graphql.core.reflection.GraphQLBizModel;
import io.nop.graphql.core.reflection.GraphQLBizModels;
import io.nop.orm.IOrmTemplate;
import jakarta.inject.Inject;

import static io.crazydan.jingwei.app.AppConstants.APP_INSTALLATION_DIR_MODEL;
import static io.crazydan.jingwei.app.AppConstants.APP_INSTALLATION_DIR_ORM;
import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_CLASSPATH_V_PATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_INSTALLATION_V_PATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_STATIC_V_PATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_STORE_V_PATH;
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
    @Inject
    IOrmTemplate ormTemplate;
    @Inject
    AppOrmModelProvider ormModelProvider;

    @Inject
    IBizObjectManager bizObjectManager;

    @Description("加载全部已启用的应用")
    @BizMutation
    public void loadEnabledApps() {
        AppLocalStore_Manifest manifest = loadAppLocalStoreManifest();

        doLoadEnabledApps(manifest);
    }

    @Description("确保应用已安装")
    @BizAction
    public AppInstallation_Manifest assureAppInstalled(@Name("appCode") String appCode) {
        AppInstallation_Manifest manifest = loadAppInstallationManifestFromDir(appCode);

        if (manifest == null) {
            manifest = installAppFromDb(appCode);
        }
        if (manifest == null) {
            manifest = installAppFromClasspath(appCode);
        }

        return manifest != null ? manifest : AppInstallation_Manifest.NONE;
    }

    @Description("从 classpath 安装应用")
    @BizAction
    public AppInstallation_Manifest installAppFromClasspath(@Name("appCode") String appCode) {
        IResource resource = loadAppClasspathResource(appCode, APP_MANIFEST_FILE);
        AppReleasing_Manifest manifest = AppHelper.loadAppReleasingManifest(resource);
        if (manifest == null) {
            return null;
        }

        installAppToDir(manifest);

        return loadAppInstallationManifestFromDir(appCode);
    }

    @Description("从数据库安装应用")
    @BizAction
    public AppInstallation_Manifest installAppFromDb(@Name("appCode") String appCode) {
        AppInstallation_Manifest manifest = null;

        return manifest;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected AppLocalStore_Manifest loadAppLocalStoreManifest() {
        IResource resource = loadVfsResource(TEMPLATE_APP_STORE_V_PATH, "", APP_MANIFEST_FILE);
        AppLocalStore_Manifest manifest = AppHelper.loadAppLocalStoreManifest(resource);

        // 至少需加载门户应用
        if (manifest == null) {
            manifest = AppLocalStore_Manifest.DEFAULT;
        }
        return manifest;
    }

    protected AppInstallation_Manifest loadAppInstallationManifestFromDir(String appCode) {
        IResource resource = loadAppInstallationResource(appCode, APP_MANIFEST_FILE);

        return AppHelper.loadAppInstallationManifest(resource);
    }

    protected IResource loadAppInstallationResource(String appCode, String path) {
        return loadVfsResource(TEMPLATE_APP_INSTALLATION_V_PATH, appCode, path);
    }

    protected IResource loadAppClasspathResource(String appCode, String path) {
        return loadVfsResource(TEMPLATE_APP_CLASSPATH_V_PATH, appCode, path);
    }

    protected IResource loadVfsResource(String template, String appCode, String path) {
        Map<String, Object> params = Map.of(VAR_APP_CODE, appCode, VAR_PATH, path);
        String vPath = StringHelper.renderTemplate(template, params::get);

        return VirtualFileSystem.instance().getResource(vPath);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected synchronized void doLoadEnabledApps(AppLocalStore_Manifest manifest) {
        List<IResource> ormModelResources = new ArrayList<>();
        Map<String, GraphQLBizModel> bizModels = new HashMap<>();

        manifest.getEnabledApps()
                .getChildren()
                .stream()
                .map((app) -> assureAppInstalled(app.getCode()))
                .forEach((appManifest) -> {
                    appManifest.getModelResources().getChildren().forEach((model) -> {
                        IResource resource = loadAppInstallationResource(appManifest.getCode(), model.getPath());

                        GraphQLBizModels.discoverBizModel(bizModels, resource);
                    });

                    appManifest.getOrmResources().getChildren().forEach((orm) -> {
                        if ("orm/app.orm.xml".equals(orm.getPath())) {
                            IResource resource = loadAppInstallationResource(appManifest.getCode(), orm.getPath());
                            ormModelResources.add(resource);
                        }
                    });
                });

        this.bizObjectManager.setDynamicBizModels(GraphQLBizModels.fromBizModels(bizModels));

        this.ormModelProvider.setOrmModelResources(ormModelResources);
        this.ormTemplate.reloadModel();
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected void installAppToDir(AppReleasing_Manifest releasingManifest) {
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

        // Note: 模型资源释放到应用安装目录
        installAppPackageResources(ormResources,
                                   appCode,
                                   APP_INSTALLATION_DIR_ORM,
                                   manifest.getOrmResources()::addChild);
        installAppPackageResources(modelResources,
                                   appCode,
                                   APP_INSTALLATION_DIR_MODEL,
                                   manifest.getModelResources()::addChild);

        // Note: 页面资源需释放到应用静态资源目录
        pageResources.forEach((source) -> {
            AppPackage_Resource pkg = new AppPackage_Resource();

            IResource resource = loadVfsResource(TEMPLATE_APP_STATIC_V_PATH, appCode, source.getName());
            source.getResource().saveToResource(resource);

            pkg.setPath(resource.getStdPath());

            manifest.getPageResources().addChild(pkg);
        });

        IResource manifestResource = loadAppInstallationResource(appCode, APP_MANIFEST_FILE);
        AppHelper.saveAppInstallationManifest(manifest, manifestResource);
    }

    protected void installAppPackageResources(
            List<AppPackage_Resource> sources, String appCode, String subPath, Consumer<AppPackage_Resource> consumer) {
        sources.forEach((source) -> {
            AppPackage_Resource pkg = new AppPackage_Resource();
            pkg.setPath(subPath + '/' + source.getName());

            IResource resource = loadAppInstallationResource(appCode, pkg.getPath());
            source.getResource().saveToResource(resource);

            consumer.accept(pkg);
        });
    }
}
