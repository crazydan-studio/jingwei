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

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.command.AiCommand;
import io.nop.ai.core.prompt.IPromptTemplate;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.ai.core.xdef.AiXDefHelper;
import io.nop.api.core.annotations.data.DataBean;
import io.nop.api.core.ioc.BeanContainer;
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
 * @date 2026-02-09
 */
public class AppCoder {
    private final String provider;
    private final String model;

    private IPromptTemplateManager promptTemplateManager;

    public static AppCoder create(String provider, String model) {
        return new AppCoder(provider, model);
    }

    private AppCoder(String provider, String model) {
        this.provider = provider;
        this.model = model;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /** 根据模型设计需求生成模型设计提示词 */
    public String genModelDesignPrompt(String requirements) {
        DesignPrompt prompt = createModelDesignPrompt(requirements);

        return prompt.generate();
    }

    /** 根据模型设计需求生成模型设计代码 */
    public DesignCode genModelDesignCode(String requirements) {
        DesignPrompt prompt = createModelDesignPrompt(requirements);

        return genCode(prompt);
    }

    /** 从 AI 聊天内容中解析出模型设计代码 */
    public String parseModelDesignCodeFromChat(String content) {
        IPromptTemplate template = loadPromptTemplate(PROMPT_MODEL_DESIGN);

        return parseCodeFromChat(content, template);
    }

    /** 根据 UI 设计需求生成 UI 设计提示词 */
    public String genUiDesignPrompt(String requirements) {
        DesignPrompt prompt = createUiDesignPrompt(requirements);

        return prompt.generate();
    }

    /** 根据 UI 设计需求生成 UI 设计代码 */
    public DesignCode genUiDesignCode(String requirements) {
        DesignPrompt prompt = createUiDesignPrompt(requirements);

        return genCode(prompt);
    }

    /** 从 AI 聊天内容中解析出 UI 设计代码 */
    public String parseUiDesignCodeFromChat(String content) {
        IPromptTemplate template = loadPromptTemplate(PROMPT_UI_DESIGN);

        return parseCodeFromChat(content, template);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected DesignPrompt createModelDesignPrompt(String requirements) {
        IPromptTemplate template = loadPromptTemplate(PROMPT_MODEL_DESIGN);
        Map<String, Object> vars = Map.of("modelDesignXdef",
                                          loadXDefNode(XDSL_SCHEMA_CODER_MODEL_DESIGN).xml(),
                                          "modelRequirements",
                                          requirements);

        return new DesignPrompt(template, vars);
    }

    protected DesignPrompt createUiDesignPrompt(String requirements) {
        IPromptTemplate template = loadPromptTemplate(PROMPT_UI_DESIGN);
        Map<String, Object> vars = Map.of("uiDesignXdef",
                                          loadXDefNode(XDSL_SCHEMA_CODER_UI_DESIGN).xml(),
                                          "uiRequirements",
                                          requirements);

        return new DesignPrompt(template, vars);
    }

    protected DesignCode genCode(DesignPrompt prompt) {
        AiCommand command = AiCommand.create();
        // Note: 直接抛出异常，由最上层统一处理异常，避免将异常包装为 response
        command.setReturnExceptionAsResponse(false);
        command.setPromptTemplate(prompt.template);

        AiChatOptions options = command.makeChatOptions();
        options.setRequestTimeout(TimeUnit.MINUTES.toMillis(10));
        options.setProvider(this.provider);
        options.setModel(this.model);

        AiChatExchange exchange = command.execute(prompt.vars, null);

        XNode node = (XNode) exchange.getOutput("RESULT");
        return new DesignCode(node.xml());
    }

    protected String parseCodeFromChat(String content, IPromptTemplate template) {
        AiChatExchange exchange = new AiChatExchange();
        exchange.setContent(content);

        IEvalScope scope = XLang.newEvalScope();
        template.processChatResponse(exchange, scope);

        XNode node = (XNode) exchange.getOutput("RESULT");
        return node.xml();
    }

    protected IPromptTemplate loadPromptTemplate(String path) {
        if (this.promptTemplateManager == null) {
            this.promptTemplateManager = BeanContainer.getBeanByType(IPromptTemplateManager.class);
        }
        return this.promptTemplateManager.loadPromptTemplateFromPath(path);
    }

    protected XNode loadXDefNode(String path) {
        XNode node = AiXDefHelper.loadXDefForAi(path);
        node.clearComment();

        return node;
    }

    protected static class DesignPrompt {
        final IPromptTemplate template;
        final Map<String, Object> vars;

        DesignPrompt(IPromptTemplate template, Map<String, Object> vars) {
            this.template = template;
            this.vars = vars;
        }

        public String generate() {
            IEvalScope scope = this.template.prepareInputs(this.vars);

            return this.template.generatePrompt(scope);
        }
    }

    @DataBean
    public static class DesignCode {
        private final String content;

        public DesignCode(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }
    }
}
