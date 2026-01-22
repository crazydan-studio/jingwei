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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.coder.AppInstallationBuilder;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.AppLocalStore_App;
import io.crazydan.jingwei.app.model.AppLocalStore_Manifest;
import io.crazydan.jingwei.app.model.AppPackage_Resource;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.crazydan.jingwei.app.orm.AppOrmModelProvider;
import io.crazydan.jingwei.app.util.AppModelHelper;
import io.nop.api.core.annotations.biz.BizAction;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizMutation;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Name;
import io.nop.api.core.annotations.core.Optional;
import io.nop.api.core.exceptions.NopException;
import io.nop.biz.api.IBizObjectManager;
import io.nop.core.resource.IResource;
import io.nop.graphql.core.reflection.GraphQLBizModel;
import io.nop.graphql.core.reflection.GraphQLBizModels;
import io.nop.orm.IOrmTemplate;
import jakarta.inject.Inject;

import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_CLASSPATH_VPATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_INSTALLATION_ROOT_VPATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_INSTALLATION_VPATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_STATIC_ROOT_VPATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_STATIC_VPATH;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_STORE_VPATH;
import static io.crazydan.jingwei.app.AppConstants.VAR_APP_CODE;
import static io.crazydan.jingwei.app.AppConstants.VAR_PATH;
import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_PORTAL_CODE;
import static io.crazydan.jingwei.app.AppCoreErrors.ERR_BIZ_APP_NOT_EXIST;
import static io.crazydan.jingwei.app.AppCoreErrors.ERR_BIZ_APP_NO_PAGE;
import static io.crazydan.jingwei.app.AppCoreErrors.ERR_CFG_VALUE_NOT_SPECIFIED;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.APP_FILE_ORM_DSL;
import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;
import static io.nop.xlang.XLangErrors.ARG_CODE;

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

    @Description("加载指定应用的页面")
    @BizMutation
    public Map<String, Object> loadAppPage(
            @Optional @Name("app") String appCode,
            @Optional @Name("preview") Boolean forPreview
    ) {
        // TODO 加载应用预览页面 /preview/{appCode}/{version}/index.js

        if (StringHelper.isBlank(appCode)) {
            appCode = getPortalAppCode();
        }

        AppInstallation_Manifest manifest = assureAppInstalled(appCode);
        if (manifest == null) {
            throw new NopException(ERR_BIZ_APP_NOT_EXIST).param(ARG_CODE, appCode);
        }

        manifest = prepareAppPages(manifest);
        if (!manifest.getPageResources().hasChildren()) {
            throw new NopException(ERR_BIZ_APP_NO_PAGE).param(ARG_CODE, appCode);
        }

        return getAppPageStaticPath(manifest);
    }

    @Description("加载全部已启用的应用")
    @BizAction
    public synchronized void loadEnabledApps() {
        AppLocalStore_Manifest manifest = loadAppLocalStoreManifest();

        loadEnabledApps(manifest);
    }

    @Description("确保应用已安装")
    @BizAction
    public AppInstallation_Manifest assureAppInstalled(@Name("appCode") String appCode) {
        IResource resource = loadAppInstallationResource(appCode, APP_MANIFEST_FILE);
        AppInstallation_Manifest manifest = AppModelHelper.loadAppInstallationManifest(resource);

        if (manifest == null) {
            manifest = installAppFromClasspath(appCode);
        }
        return manifest;
    }

    @Description("从 classpath 安装应用")
    @BizAction
    public AppInstallation_Manifest installAppFromClasspath(@Name("appCode") String appCode) {
        IResource resource = loadVfsResource(TEMPLATE_APP_CLASSPATH_VPATH, appCode, APP_MANIFEST_FILE);
        AppReleasing_Manifest manifest = AppModelHelper.loadAppReleasingManifest(resource);
        if (manifest == null) {
            return null;
        }

        File targetDir = loadVfsResource(TEMPLATE_APP_INSTALLATION_ROOT_VPATH, appCode, "").toFile();
        File pageTargetDir = loadVfsResource(TEMPLATE_APP_STATIC_ROOT_VPATH, appCode, "").toFile();

        AppInstallationBuilder builder = new AppInstallationBuilder();

        return builder.install(manifest, targetDir, pageTargetDir);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected void loadEnabledApps(AppLocalStore_Manifest manifest) {
        List<IResource> ormModelResources = new ArrayList<>();
        Map<String, GraphQLBizModel> bizModels = new HashMap<>();

        List<AppLocalStore_App> apps = new ArrayList<>();
        apps.add(manifest.getPortalApp());
        apps.addAll(manifest.getEnabledApps().getChildren());

        apps.stream()
            .map((app) -> assureAppInstalled(app.getCode()))
            .filter(Objects::nonNull)
            .forEach((appManifest) -> {
                appManifest.getModelResources().getChildren().forEach((model) -> {
                    IResource resource = loadAppInstallationResource(appManifest.getCode(), model.getPath());

                    GraphQLBizModels.discoverBizModel(bizModels, resource);
                });

                appManifest.getOrmResources().getChildren().forEach((orm) -> {
                    if (APP_FILE_ORM_DSL.equals(orm.getPath())) {
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

    protected AppInstallation_Manifest prepareAppPages(AppInstallation_Manifest manifest) {
        String appCode = manifest.getCode();

        boolean pageGenerated = manifest.getPageResources().hasChildren();
        for (AppPackage_Resource pkg : manifest.getPageResources().getChildren()) {
            IResource resource = loadVfsResource(TEMPLATE_APP_STATIC_VPATH, appCode, pkg.getPath());
            if (!resource.exists()) {
                pageGenerated = false;
                break;
            }
        }

        if (pageGenerated) {
            return manifest;
        }

        File targetDir = loadVfsResource(TEMPLATE_APP_STATIC_ROOT_VPATH, appCode, "").toFile();
        AppInstallation_Manifest cloned = manifest.cloneInstance();

        AppInstallationBuilder builder = new AppInstallationBuilder();
        builder.buildPages(cloned, targetDir);

        IResource manifestResource = AppModelHelper.getVfsResource(manifest.resourcePath());
        AppModelHelper.saveAppInstallationManifest(cloned, manifestResource);

        return AppModelHelper.loadAppInstallationManifest(manifestResource);
    }

    protected Map<String, Object> getAppPageStaticPath(AppInstallation_Manifest manifest) {
        Map<String, Object> result = new HashMap<>();

        String appCode = manifest.getCode();
        manifest.getPageResources().getChildren().forEach((page) -> {
            String vPath = getVPath(TEMPLATE_APP_STATIC_VPATH, appCode, page.getName());
            String staticPath = StringHelper.nextPart(vPath, ':');

            if (staticPath.endsWith(".js")) {
                result.put("js", staticPath);
            } else if (staticPath.endsWith(".css")) {
                List<String> list = (List<String>) result.computeIfAbsent("css", (k) -> new ArrayList<>());
                list.add(staticPath);
            }
        });

        return result;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected String getPortalAppCode() {
        AppLocalStore_Manifest manifest = loadAppLocalStoreManifest();

        return manifest.getPortalAppCode();
    }

    protected AppLocalStore_Manifest loadAppLocalStoreManifest() {
        IResource resource = loadVfsResource(TEMPLATE_APP_STORE_VPATH, "", APP_MANIFEST_FILE);
        AppLocalStore_Manifest manifest = AppModelHelper.loadAppLocalStoreManifest(resource);

        // TODO 完善门户页面配置检查和异常信息
        if (manifest == null) {
            AppLocalStore_App app = new AppLocalStore_App();
            app.setCode(CFG_APP_PORTAL_CODE.get());

            if (StringHelper.isBlank(app.getCode())) {
                throw new NopException(ERR_CFG_VALUE_NOT_SPECIFIED).source(CFG_APP_PORTAL_CODE) //
                                                                   .param(ARG_CONFIG_VAR,
                                                                          CFG_APP_PORTAL_CODE.getName());
            }

            manifest = new AppLocalStore_Manifest();
            manifest.setPortalApp(app);
        }

        return manifest;
    }

    protected IResource loadAppInstallationResource(String appCode, String path) {
        return loadVfsResource(TEMPLATE_APP_INSTALLATION_VPATH, appCode, path);
    }

    protected IResource loadVfsResource(String template, String appCode, String path) {
        String vPath = getVPath(template, appCode, path);

        return AppModelHelper.getVfsResource(vPath);
    }

    protected String getVPath(String template, String appCode, String path) {
        Map<String, Object> params = Map.of(VAR_APP_CODE, appCode, VAR_PATH, path);

        return StringHelper.renderTemplate(template, params::get);
    }
}
