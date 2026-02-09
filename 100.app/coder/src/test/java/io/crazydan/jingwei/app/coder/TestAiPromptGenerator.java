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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.crazydan.duzhou.framework.junit.NopJunitAutoTestCase;
import io.crazydan.jingwei.app.coder.prompt.AiPromptGenerator;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.command.AiCommand;
import io.nop.ai.core.prompt.IPromptTemplate;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.ai.core.xdef.AiXDefHelper;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.core.lang.xml.XNode;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.XDSL_SCHEMA_CODER_MODEL_DESIGN;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.XDSL_SCHEMA_CODER_UI_DESIGN;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-01
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = true)
public class TestAiPromptGenerator extends NopJunitAutoTestCase {
    @Inject
    IPromptTemplateManager promptTemplateManager;

    @EnableSnapshot
    @Test
    public void test_app_design() {
        AiPromptGenerator promptGenerator = new AiPromptGenerator(this.promptTemplateManager);

        String requirements = inputText("model-requirements.md");
        String prompt = promptGenerator.genModelDesignPrompt(requirements);
        outputText("prompt-model-design.md", prompt);

        //
        requirements = inputText("ui-requirements.md");
        prompt = promptGenerator.genUiDesignPrompt(requirements);
        outputText("prompt-ui-design.md", prompt);
    }

    @EnableSnapshot
    @Test
    public void test_ai_coder() {
        AiCommand command = AiCommand.create();

        AiChatOptions options = command.makeChatOptions();
        options.setMaxTokens(8192);
        options.setRequestTimeout(TimeUnit.MINUTES.toMillis(10));
//        options.setProvider("deepseek");
//        options.setModel("deepseek-chat");
        options.setProvider("bailian");
        options.setModel("qwen-coder-plus");

//        // 对应系统提示词 /nop/ai/prompts/system/programming
//        options.setWorkMode("programming");
//        options.setEnableCognitivePrompt(true);
//        options.setEnableMetaPrompt(true);
//        options.setEnableSystemPrompt(true);

        Map<String, Object> vars = new HashMap<>();
        vars.put("modelDesignXdef", loadXNode(XDSL_SCHEMA_CODER_MODEL_DESIGN).xml());
        vars.put("modelRequirements", inputText("model-requirements.md"));

        IPromptTemplate promptTemplate = loadPrompt("/jingwei/app/coder/ai-prompts/model-design.prompt.yaml");
        command.promptTemplate(promptTemplate);

        AiChatExchange exchange = command.execute(vars, null);
        XNode node = (XNode) exchange.getOutput("RESULT");
        node.dump();

        //
        command.setPrevMessages(exchange.getAllMessages(true));

        promptTemplate = loadPrompt("/jingwei/app/coder/ai-prompts/ui-design.prompt.yaml");
        command.promptTemplate(promptTemplate);

        vars = new HashMap<>();
        vars.put("uiDesignXdef", loadXNode(XDSL_SCHEMA_CODER_UI_DESIGN).xml());
        vars.put("uiRequirements", inputText("ui-requirements.md"));

        node = (XNode) command.execute(vars, null).getOutput("RESULT");
        node.dump();
    }

    protected IPromptTemplate loadPrompt(String promptPath) {
        return this.promptTemplateManager.loadPromptTemplateFromPath(promptPath);
    }

    protected XNode loadXNode(String path) {
        XNode node = AiXDefHelper.loadXDefForAi(path);
        node.clearComment();

        return node;
    }
}
