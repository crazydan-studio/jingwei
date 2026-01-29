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

package io.crazydan.jingwei.app.coder.prompt;

import java.util.HashMap;
import java.util.Map;

import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.prompt.IPromptTemplate;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.ai.core.xdef.AiXDefHelper;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.xlang.api.XLang;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.PROMPT_MODEL_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.PROMPT_UI_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XDSL_SCHEMA_CODER_MODEL_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XDSL_SCHEMA_CODER_UI_DESIGN;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-29
 */
public class AiPromptGenerator {
    private final IPromptTemplateManager promptTemplateManager;

    public AiPromptGenerator(IPromptTemplateManager promptTemplateManager) {
        this.promptTemplateManager = promptTemplateManager;
    }

    public String genModelDesignPrompt(String requirements) {
        IPromptTemplate promptTemplate = this.promptTemplateManager.loadPromptTemplateFromPath(PROMPT_MODEL_DESIGN);

        Map<String, Object> vars = new HashMap<>();
        vars.put("modelDesignXdef", loadXDefNode(XDSL_SCHEMA_CODER_MODEL_DESIGN).xml());
        vars.put("modelRequirements", requirements);

        IEvalScope scope = promptTemplate.prepareInputs(vars);

        return promptTemplate.generatePrompt(scope);
    }

    public String getModelDesignCodeFromResponse(String response) {
        IPromptTemplate promptTemplate = this.promptTemplateManager.loadPromptTemplateFromPath(PROMPT_MODEL_DESIGN);

        return getCodeFromResponse(promptTemplate, response);
    }

    public String genUiDesignPrompt(String requirements) {
        IPromptTemplate promptTemplate = this.promptTemplateManager.loadPromptTemplateFromPath(PROMPT_UI_DESIGN);

        Map<String, Object> vars = new HashMap<>();
        vars.put("uiDesignXdef", loadXDefNode(XDSL_SCHEMA_CODER_UI_DESIGN).xml());
        vars.put("uiRequirements", requirements);

        IEvalScope scope = promptTemplate.prepareInputs(vars);

        return promptTemplate.generatePrompt(scope);
    }

    public String getUiDesignCodeFromResponse(String response) {
        IPromptTemplate promptTemplate = this.promptTemplateManager.loadPromptTemplateFromPath(PROMPT_UI_DESIGN);

        return getCodeFromResponse(promptTemplate, response);
    }

    protected XNode loadXDefNode(String path) {
        XNode node = AiXDefHelper.loadXDefForAi(path);
        node.clearComment();

        return node;
    }

    protected String getCodeFromResponse(IPromptTemplate promptTemplate, String response) {
        AiChatExchange resp = new AiChatExchange();
        resp.setContent(response);

        IEvalScope scope = XLang.newEvalScope();
        promptTemplate.processChatResponse(resp, scope);

        XNode node = (XNode) resp.getOutput("RESULT");
        return node.xml();
    }
}
