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
import java.util.Map;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppPage;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizQuery;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;
import io.nop.core.resource.impl.ClassPathResource;

import static io.crazydan.jingwei.AppCoreConfigs.CFG_APP_INSTALL_DIR;
import static io.crazydan.jingwei.app.AppConstants.APP_MANIFEST_FILE;
import static io.crazydan.jingwei.app.AppConstants.TEMPLATE_APP_VPATH;
import static io.crazydan.jingwei.app.AppConstants.VAR_APP_CODE;
import static io.crazydan.jingwei.app.AppConstants.VAR_PATH;
import static io.nop.core.resource.ResourceConstants.RESOURCE_NS_CLASSPATH;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
@BizModel("App")
public class AppCoreBizModel {

    /** 加载指定应用的指定页面：在 Web 服务中将主动加载门户应用的首页 */
    @BizQuery
    public AppPage loadPage(String appCode, String pageName) {
        assureModuleLoaded(appCode);

        AppPage page = new AppPage();

        return page;
    }

    /** 确保指定应用的模块已加载 */
    public void assureModuleLoaded(String appCode) {
        if (checkIfModuleReady(appCode)) {
            return;
        }
    }

    protected void loadModuleFromClasspath(String appCode) {
        IResource resource = new ClassPathResource(RESOURCE_NS_CLASSPATH + ":apps/" + appCode);
    }

    protected void loadModuleFromDb(String appCode) {
        // TODO 加载应用已激活版本
    }

    protected boolean checkIfModuleReady(String appCode) {
        IResource manifestResource = loadModuleResource(appCode, APP_MANIFEST_FILE);

        return true;
    }

    protected IResource loadModuleResource(String appCode, String path) {
        Map<String, Object> params = Map.of(VAR_APP_CODE, appCode, VAR_PATH, path);
        String vpath = StringHelper.renderTemplate(TEMPLATE_APP_VPATH, params::get);

        return VirtualFileSystem.instance().getResource(vpath);
    }

    protected File getModuleDir(String appCode) {
        return new File(CFG_APP_INSTALL_DIR.get(), appCode);
    }
}
