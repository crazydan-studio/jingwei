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

import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.ClassPathResource;

import static io.nop.core.resource.ResourceConstants.RESOURCE_NS_CLASSPATH;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public class AppLoader {

    public void loadModule(String appCode) {
    }

    protected void loadModuleFromClasspath(String appCode) {
        IResource resource = new ClassPathResource(RESOURCE_NS_CLASSPATH + ":apps/" + appCode);
    }

    protected void loadModuleFromDb(String appCode) {
        //
    }
}
