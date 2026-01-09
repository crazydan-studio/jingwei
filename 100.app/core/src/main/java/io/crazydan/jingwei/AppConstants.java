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

package io.crazydan.jingwei;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public interface AppConstants {
    String APP_VFS_NAMESPACE = "xapp";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    String XLIB_AI_APP_GEN_PATH = "/jingwei/ai/xlib/app-module.xlib";

    String XLIB_TAG_ModelDesignToOrmModel = "ModelDesignToOrmModel";

    String XLIB_TAG_ATTR_NODE = "node";

    String TEMPLATE_APP_MODULE_PATH = "/jingwei/templates/app-module";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String SCOPE_VAR_codeGenModel = "codeGenModel";
    String SCOPE_VAR_appGenConfig = "appGenConfig";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /** 门户应用的缺省应用标识 */
    String APP_PORTAL_DEFAULT_CODE = "ae9c6b146f93436abba7761593058f38";

    String APP_AI_MODEL_DESIGN_NAME = "app.ai-model-design.xml";
    String APP_AI_UI_DESIGN_NAME = "app.ai-ui-design.xml";
}
