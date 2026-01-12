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

package io.crazydan.jingwei.app.orm;

import java.util.List;

import io.nop.api.core.context.ContextProvider;
import io.nop.commons.cache.GlobalCacheRegistry;
import io.nop.core.resource.IResource;
import io.nop.core.resource.cache.CacheEntryManagement;
import io.nop.core.resource.tenant.ResourceTenantManager;
import io.nop.orm.ILoadedOrmModel;
import io.nop.orm.IOrmInterceptor;
import io.nop.orm.factory.IOrmModelProvider;
import io.nop.orm.factory.LoadedOrmModel;
import io.nop.orm.factory.XplOrmInterceptorLoader;
import io.nop.orm.model.OrmModel;
import io.nop.orm.model.loader.OrmModelLoader;
import io.nop.orm.persister.IPersistEnv;

import static io.nop.orm.OrmConfigs.CFG_ORM_MODEL_CACHE_CHECK_CHANGE;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-12
 */
public class AppOrmModelProvider implements IOrmModelProvider {
    private final CacheEntryManagement<ILoadedOrmModel> cache;

    private List<IResource> ormModelResources;

    public AppOrmModelProvider() {
        this.cache = ResourceTenantManager.instance().makeCacheEntry("dyn-loaded-orm-model-cache", false, null);
        GlobalCacheRegistry.instance().register(this.cache);
    }

    public void setOrmModelResources(List<IResource> ormModelResources) {
        this.ormModelResources = ormModelResources;
    }

    @Override
    public ILoadedOrmModel getOrmModel(IPersistEnv env) {
        return ContextProvider.runWithoutTenantId(() -> doGetSharedOrmModel(env));
    }

    @Override
    public void clearCache() {
        this.cache.clear();
    }

    @Override
    public void clearCacheForTenant(String tenantId) {
        this.cache.clearForTenant(tenantId);
    }

    @Override
    public void close() {
        this.cache.clear();
        GlobalCacheRegistry.instance().unregister(this.cache);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ILoadedOrmModel doGetSharedOrmModel(IPersistEnv env) {
        return this.cache.getObject(CFG_ORM_MODEL_CACHE_CHECK_CHANGE.get(), k -> loadSharedOrmModel(env));
    }

    private LoadedOrmModel loadSharedOrmModel(IPersistEnv env) {
        OrmModel ormModel = new OrmModelLoader().loadOrmModel(this.ormModelResources);
        LoadedOrmModel ret = new LoadedOrmModel(env, ormModel);

        IOrmInterceptor interceptor = new XplOrmInterceptorLoader().loadInterceptor(List.of());
        ret.setOrmInterceptor(interceptor);

        return ret;
    }
}
