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

package io.crazydan.jingwei.app.coder;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public interface AppCoderConstants {
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    String XLIB_APP_MODEL_GEN_PATH = "/jingwei/app/coder/xlib/app-model.xlib";
    String XLIB_APP_PAGE_GEN_PATH = "/jingwei/app/coder/xlib/app-page.xlib";

    String XLIB_TAG_ModelDesignToOrmModel = "ModelDesignToOrmModel";
    String XLIB_TAG_UiDesignToUiModel = "UiDesignToUiModel";

    String TAG_ATTR_node = "node";

    String TEMPLATE_APP_MODEL_PATH = "/jingwei/app/templates/app-model";
    String TEMPLATE_APP_PAGE_PATH = "/jingwei/app/templates/app-page";

    String TEMPLATE_DIR_MODEL = "model";
    String TEMPLATE_DIR_ORM = "orm";
    String TEMPLATE_DIR_PAGE = "page";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String SCOPE_VAR_codeGenModel = "codeGenModel";
    String SCOPE_VAR_codeGenConfig = "codeGenConfig";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String BUILD_DIR_HIDDEN_BUILD = ".build";
    String BUILD_DIR_NODE_MODULES = "node_modules";
    String BUILD_DIR_DIST = "dist";
    String BUILD_DIST_FILE_NAME = "index";

    String PACK_DIR_ARTIFACT = "artifact";
}
