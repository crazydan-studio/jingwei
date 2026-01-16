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

package io.crazydan.jingwei.app.initialize;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.service.AppCoreBizModel;
import io.nop.api.core.config.AppConfig;
import io.nop.api.core.config.IConfigReference;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.ioc.BeanContainer;
import io.nop.commons.lang.impl.Cancellable;
import io.nop.core.initialize.ICoreInitializer;

import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_INSTALL_DIR;
import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_PORTAL_CODE;
import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_STATIC_DIR;
import static io.crazydan.jingwei.app.AppCoreErrors.ERR_CFG_VALUE_NOT_SPECIFIED;
import static io.nop.ai.core.AiCoreErrors.ARG_CONFIG_VAR;

/**
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public class AppCoreInitializer implements ICoreInitializer {
    private final Cancellable cleanup = new Cancellable();

    @Override
    public int order() {
        return NORMAL_PRIORITY + 2000;
    }

    @Override
    public void initialize() {
        checkDirConfig(CFG_APP_STATIC_DIR);
        checkDirConfig(CFG_APP_INSTALL_DIR);

        loadEnabledApps();
    }

    @Override
    public void destroy() {
        this.cleanup.cancel();
    }

    private void checkDirConfig(IConfigReference<String> config) {
        String path = config.get();
        if (StringHelper.isBlank(path)) {
            throw new NopException(ERR_CFG_VALUE_NOT_SPECIFIED).source(config) //
                                                               .param(ARG_CONFIG_VAR, config.getName());
        }

        String pwd = FileHelper.currentDir().getAbsolutePath();
        String dir = StringHelper.absolutePath(pwd, path);
        FileHelper.assureDirExists(dir);

        AppConfig.getConfigProvider().updateConfigValue(config, dir);
    }

    private void loadEnabledApps() {
        if (StringHelper.isBlank(CFG_APP_PORTAL_CODE.get())) {
            throw new NopException(ERR_CFG_VALUE_NOT_SPECIFIED).source(CFG_APP_PORTAL_CODE) //
                                                               .param(ARG_CONFIG_VAR, CFG_APP_PORTAL_CODE.getName());
        }

        AppCoreBizModel biz = BeanContainer.instance().getBeanByType(AppCoreBizModel.class);
        biz.loadEnabledApps();
    }
}
