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

package io.crazydan.jingwei.ai.prompt;

import java.util.HashMap;
import java.util.Map;

import io.crazydan.duzhou.framework.junit.NopJunitAutoTestCase;
import io.nop.ai.coder.orm.AiOrmModel;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.command.AiCommand;
import io.nop.ai.core.prompt.IPromptTemplate;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-01
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = true)
public class TestAiPrompt extends NopJunitAutoTestCase {
    @Inject
    IPromptTemplateManager promptTemplateManager;

    @EnableSnapshot
    @Test
    public void test_app_main_design() {
        IPromptTemplate promptModel = loadPrompt("/jingwei/ai/prompts/coder/erd-design.prompt.yaml");
        Map<String, Object> vars = new HashMap<>();
        vars.put("appRequirements", inputText("app-requirements.md"));
        vars.put("modelRequirements", inputText("model-requirements.md"));

        IEvalScope scope = promptModel.prepareInputs(vars);
        String prompt = promptModel.generatePrompt(scope);
        outputText("prompt-erd-design.md", prompt);

        AiChatExchange response = new AiChatExchange();
        String content = inputText("response-erd-design.md");
        response.setContent(content);
        promptModel.processChatResponse(response, scope);

        XNode node = (XNode) response.getOutput("RESULT");
        node.dump();

        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        promptModel = loadPrompt("/jingwei/ai/prompts/coder/ui-design.prompt.yaml");
        vars = new HashMap<>();
        vars.put("modelDefinitions", inputText("response-erd-design.md"));
        vars.put("appRequirements", inputText("app-requirements.md"));
        vars.put("uiRequirements", inputText("ui-requirements.md"));

        scope = promptModel.prepareInputs(vars);
        prompt = promptModel.generatePrompt(scope);
        outputText("prompt-ui-design.md", prompt);

        response = new AiChatExchange();
        content = inputText("response-ui-design.md");
        response.setContent(content);
        promptModel.processChatResponse(response, scope);

        node = (XNode) response.getOutput("RESULT");
        node.dump();
    }

    //@EnableSnapshot
    //@Test
    public void test_erd_design() {
        IPromptTemplate promptTemplate = loadPrompt("/jingwei/ai/prompts/coder/erd-design.prompt.yaml");

        AiCommand command = AiCommand.create();
        AiChatOptions options = command.makeChatOptions();
        options.setProvider("bailian");
        options.setModel("qwen-coder-plus");

//        // 对应系统提示词 /nop/ai/prompts/system/programming
//        options.setWorkMode("programming");
//        options.setEnableCognitivePrompt(true);
//        options.setEnableMetaPrompt(true);
//        options.setEnableSystemPrompt(true);

        Map<String, Object> vars = new HashMap<>();
        vars.put("requirements", inputText("input-requirements.md"));

        command.promptTemplate(promptTemplate);

        XNode node = ((AiOrmModel) command.execute(vars, null).getOutput("RESULT")).getOrmNodeForAi();
        node.dump();
    }

    protected IPromptTemplate loadPrompt(String promptPath) {
        return this.promptTemplateManager.loadPromptTemplateFromPath(promptPath);
    }
}
