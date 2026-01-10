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

package io.crazydan.jingwei.app.util;

import io.crazydan.jingwei.app.model.manifest.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_Manifest;
import io.nop.core.resource.IResource;
import io.nop.xlang.xdsl.DslModelParser;

import static io.crazydan.jingwei.app.AppConstants.XDSL_SCHEMA_APP_INSTALLATION_MANIFEST;
import static io.crazydan.jingwei.app.AppConstants.XDSL_SCHEMA_APP_RELEASING_MANIFEST;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-09
 */
public class AppHelper {

    /** 加载应用安装包清单，若资源不存在，则返回 {@code null} */
    public static AppInstallation_Manifest loadAppInstallationManifest(IResource resource) {
        return loadDslModel(resource, XDSL_SCHEMA_APP_INSTALLATION_MANIFEST);
    }

    /** 加载应用发布包清单，若资源不存在，则返回 {@code null} */
    public static AppReleasing_Manifest loadAppReleasingManifest(IResource resource) {
        return loadDslModel(resource, XDSL_SCHEMA_APP_RELEASING_MANIFEST);
    }

    /** 加载 DSL 模型，若资源不存在，则返回 {@code null} */
    public static <T> T loadDslModel(IResource resource, String xdefPath) {
        DslModelParser parser = new DslModelParser(xdefPath);

        return (T) parser.parseFromResource(resource, true);
    }
}
