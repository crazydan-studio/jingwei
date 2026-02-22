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

    String XDSL_ORM_DEFAULT_DOMAINS = "/jingwei/app/coder/default/orm-domains.xml";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String XDSL_SCHEMA_CODER_MODEL_DESIGN = "/jingwei/app/coder/schema/model-design.xdef";
    String XDSL_SCHEMA_CODER_UI_DESIGN = "/jingwei/app/coder/schema/ui-design.xdef";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String TAG_ATTR_node = "node";
    String TAG_ATTR_name = "name";
    String TAG_ATTR_displayName = "displayName";
    String TAG_ATTR_domain = "domain";
    String TAG_ATTR_ref_type = "ref:type";
    String TAG_ATTR_PREFIX_ref = "ref";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String XLIB_APP_MODEL = "/jingwei/app/coder/xlib/app-model.xlib";
    String XLIB_APP_PAGE = "/jingwei/app/coder/xlib/app-page.xlib";

    String XLIB_TAG_ModelDesignToOrmModel = "ModelDesignToOrmModel";
    String XLIB_TAG_UiDesignToUiModel = "UiDesignToUiModel";

    String CODEGEN_TEMPLATE_APP_MODEL = "/jingwei/app/templates/app-model";
    String CODEGEN_TEMPLATE_APP_PAGE = "/jingwei/app/templates/app-page";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String SCOPE_VAR_codeGenModel = "codeGenModel";
    String SCOPE_VAR_codeGenConfig = "codeGenConfig";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String BUILD_DIR_HIDDEN_BUILD = ".build";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String APP_DIR_MODEL = "model";
    String APP_DIR_ORM = "orm";
    String APP_DIR_SRC = "src";
    String APP_FILE_ORM_DSL = "orm/app.orm.xml";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String ORM_ONE_TO_ONE = "one-to-one";
    String ORM_ONE_TO_MANY = "one-to-many";

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    String PROMPT_MODEL_DESIGN = "/jingwei/app/coder/ai-prompts/model-design.prompt.yaml";
    String PROMPT_UI_DESIGN = "/jingwei/app/coder/ai-prompts/ui-design.prompt.yaml";
    String PROMPT_LOGO_DESIGN = "/jingwei/app/coder/ai-prompts/logo-design.prompt.yaml";
}
