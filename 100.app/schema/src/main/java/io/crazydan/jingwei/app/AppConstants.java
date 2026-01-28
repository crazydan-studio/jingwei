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

package io.crazydan.jingwei.app;

import static io.nop.core.resource.ResourceConstants.RESOURCE_NS_CLASSPATH;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public interface AppConstants {
    String VAR_APP_CODE = "appCode";
    String VAR_PATH = "path";

    String VFS_NS_INSTALLATION_APP = "app-install-dir";
    String VFS_NS_STATIC_APP = "app-static-dir";

    String XDSL_SCHEMA_APP_PACKAGE_RESOURCE = "/jingwei/app/schema/manifest/resource.xdef";
    String XDSL_SCHEMA_APP_RELEASING_MANIFEST = "/jingwei/app/schema/manifest/releasing.xdef";
    String XDSL_SCHEMA_APP_LOCAL_STORE_MANIFEST = "/jingwei/app/schema/manifest/local-store.xdef";
    String XDSL_SCHEMA_APP_INSTALLATION_MANIFEST = "/jingwei/app/schema/manifest/installation.xdef";

    String APP_MANIFEST_FILE = "manifest.xml";
    String TEMPLATE_APP_STORE_VPATH = //
            VFS_NS_INSTALLATION_APP + ":/{" + VAR_PATH + "}";

    String TEMPLATE_APP_INSTALLATION_ROOT_VPATH = //
            VFS_NS_INSTALLATION_APP + ":/app/{" + VAR_APP_CODE + "}";
    String TEMPLATE_APP_INSTALLATION_VPATH = //
            TEMPLATE_APP_INSTALLATION_ROOT_VPATH + "/{" + VAR_PATH + "}";

    String TEMPLATE_APP_STATIC_ROOT_VPATH = //
            VFS_NS_STATIC_APP + ":/app/{" + VAR_APP_CODE + "}";
    String TEMPLATE_APP_STATIC_VPATH = //
            TEMPLATE_APP_STATIC_ROOT_VPATH + "/{" + VAR_PATH + "}";

    String TEMPLATE_APP_CLASSPATH_VPATH = //
            RESOURCE_NS_CLASSPATH + ":app/{" + VAR_APP_CODE + "}/{" + VAR_PATH + "}";
}
