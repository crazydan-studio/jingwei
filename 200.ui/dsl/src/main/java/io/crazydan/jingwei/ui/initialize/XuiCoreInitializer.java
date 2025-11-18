/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2025 Crazydan Studio <https://studio.crazydan.org>
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

package io.crazydan.jingwei.ui.initialize;

import java.util.List;

import io.crazydan.jingwei.ui.domain.GenericStdDomainHandlers;
import io.nop.commons.lang.impl.Cancellable;
import io.nop.core.CoreConstants;
import io.nop.core.initialize.ICoreInitializer;
import io.nop.xlang.xdef.IStdDomainHandler;
import io.nop.xlang.xdef.domain.StdDomainRegistry;

/**
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-17
 */
public class XuiCoreInitializer implements ICoreInitializer {
    private final Cancellable cancellable = new Cancellable();

    @Override
    public int order() {
        return CoreConstants.INITIALIZER_PRIORITY_REGISTER_XLANG;
    }

    @Override
    public void initialize() {
        List<IStdDomainHandler> handlers = //
                List.of( //
                         new GenericStdDomainHandlers.ComponentNameType());

        StdDomainRegistry registry = StdDomainRegistry.instance();
        handlers.forEach(registry::registerStdDomainHandler);

        this.cancellable.appendOnCancelTask(() -> {
            handlers.forEach(registry::unregisterStdDomainHandler);
        });
    }

    @Override
    public void destroy() {
        this.cancellable.cancel();
    }
}
