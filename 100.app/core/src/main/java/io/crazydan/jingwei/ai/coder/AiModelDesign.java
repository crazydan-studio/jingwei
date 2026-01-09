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

package io.crazydan.jingwei.ai.coder;

import java.util.Map;

import io.crazydan.jingwei.app.AppGenConfig;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;
import io.nop.xlang.xpl.IXplTag;
import io.nop.xlang.xpl.xlib.XplLibHelper;

import static io.crazydan.jingwei.AppConstants.SCOPE_VAR_appGenConfig;
import static io.crazydan.jingwei.AppConstants.XLIB_AI_APP_GEN_PATH;
import static io.crazydan.jingwei.AppConstants.XLIB_TAG_ATTR_NODE;
import static io.crazydan.jingwei.AppConstants.XLIB_TAG_ModelDesignToOrmModel;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public class AiModelDesign {
    private final IResource resource;
    private final AppGenConfig appGenConfig;

    private XNode designNode;

    public AiModelDesign(IResource resource, AppGenConfig appGenConfig) {
        this.resource = resource;
        this.appGenConfig = appGenConfig;
    }

    public AiOrmModel genOrmModel(Map<String, Object> vars) {
        AiOrmModel ormModel = callXlibTag(XLIB_TAG_ModelDesignToOrmModel, vars);
        ormModel.init();

        return ormModel;
    }

    public XNode getDesignNode() {
        if (this.designNode == null) {
            this.designNode = XNodeParser.instance().parseFromResource(this.resource);
        }
        return this.designNode;
    }

    protected <T> T callXlibTag(String tagName, Map<String, Object> vars) {
        IEvalScope scope = XLang.newEvalScope();
        if (vars != null) {
            scope.setLocalValues(vars);
            scope.setLocalValue(SCOPE_VAR_appGenConfig, this.appGenConfig);
        }

        Map<String, Object> args = Map.of(XLIB_TAG_ATTR_NODE, getDesignNode());

        IXplTag tag = XplLibHelper.getTag(XLIB_AI_APP_GEN_PATH, tagName);
        return (T) tag.invokeWithNamedArgs(scope, args);
    }
}
