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

package io.crazydan.jingwei.app.coder.model;

import java.util.Map;

import io.crazydan.jingwei.app.coder.AppCodeGenConfig;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;
import io.nop.xlang.xpl.IXplTag;
import io.nop.xlang.xpl.xlib.XplLibHelper;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenConfig;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_node;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XLIB_APP_MODEL;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XLIB_TAG_ModelDesignToOrmModel;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public class AiModelDesign {
    private final IResource resource;
    private final AppCodeGenConfig genConfig;

    private XNode designNode;

    public AiModelDesign(IResource resource, AppCodeGenConfig genConfig) {
        this.resource = resource;
        this.genConfig = genConfig;
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
        IXplTag tag = XplLibHelper.getTag(XLIB_APP_MODEL, tagName);

        IEvalScope scope = XLang.newEvalScope();
        if (vars != null) {
            scope.setLocalValues(vars);
        }
        scope.setLocalValue(SCOPE_VAR_codeGenConfig, this.genConfig);

        XNode node = getDesignNode();
        Map<String, Object> args = Map.of(TAG_ATTR_node, node);
        return (T) tag.invokeWithNamedArgs(scope, args);
    }
}
