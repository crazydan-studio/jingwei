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
import io.crazydan.jingwei.app.coder.normalizer.AiUiDesignNormalizer;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;
import io.nop.xlang.xpl.IXplTag;
import io.nop.xlang.xpl.xlib.XplLibHelper;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.SCOPE_VAR_codeGenConfig;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_node;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XLIB_APP_PAGE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XLIB_TAG_UiDesignToUiModel;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-07
 */
public class AiUiDesign {
    private final IResource resource;
    private final AppCodeGenConfig genConfig;

    private XNode designNode;

    public AiUiDesign(IResource resource, AppCodeGenConfig genConfig) {
        this.resource = resource;
        this.genConfig = genConfig;
    }

    public AiUiModel genUiModel(Map<String, Object> vars) {
        AiUiModel uiModel = callXlibTag(XLIB_TAG_UiDesignToUiModel, vars);
        uiModel.init();

        return uiModel;
    }

    public XNode getDesignNode() {
        if (this.designNode == null) {
            this.designNode = XNodeParser.instance().parseFromResource(this.resource);
            AiUiDesignNormalizer.normalize(this.designNode);
        }
        return this.designNode;
    }

    protected <T> T callXlibTag(String tagName, Map<String, Object> vars) {
        IXplTag tag = XplLibHelper.getTag(XLIB_APP_PAGE, tagName);

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
