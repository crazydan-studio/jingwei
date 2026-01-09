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

import io.crazydan.jingwei.app.model.AppPage;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.ClassPathResource;
import io.nop.dao.api.IDaoProvider;
import jakarta.inject.Inject;

import static io.crazydan.jingwei.AppConfigs.CFG_APP_INSTALL_DIR;
import static io.crazydan.jingwei.AppConstants.APP_PORTAL_DEFAULT_CODE;
import static io.nop.core.resource.ResourceConstants.RESOURCE_NS_CLASSPATH;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public class AppService {
    @Inject
    protected IDaoProvider daoProvider;

    /** 加载指定应用的指定页面：在 Web 服务中将主动加载门户应用的首页 */
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

        if (APP_PORTAL_DEFAULT_CODE.equals(appCode)) {
            loadModuleFromClasspath(appCode);
        } else {
            loadModuleFromDb(appCode);
        }
    }

    protected void loadModuleFromClasspath(String appCode) {
        IResource resource = new ClassPathResource(RESOURCE_NS_CLASSPATH + ":apps/" + appCode);
    }

    protected void loadModuleFromDb(String appCode) {
        // TODO 加载应用已激活版本
    }

    protected boolean checkIfModuleReady(String appCode) {
        File appModuleDir = getModuleDir(appCode);
        if (!appModuleDir.isDirectory()) {
            return false;
        }

        return true;
    }

    protected File getModuleDir(String appCode) {
        return new File(CFG_APP_INSTALL_DIR.get(), appCode);
    }
}
