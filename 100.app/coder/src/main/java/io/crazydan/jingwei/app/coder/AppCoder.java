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

import io.crazydan.jingwei.app.coder.prompt.AiPromptGenerator;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.command.AiCommand;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.api.core.ioc.BeanContainer;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
public class AppCoder {
    private final AiCommand command;

    public static AppCoder create(String provider, String model) {
        AiCommand command = AiCommand.create();
        // Note: 直接抛出异常，由最上层统一处理异常，避免将异常包装为 response
        command.setReturnExceptionAsResponse(false);

        AiChatOptions options = command.makeChatOptions();
        options.setRequestTimeout(TimeUnit.MINUTES.toMillis(10));
        options.setProvider(provider);
        options.setModel(model);

        return new AppCoder(command);
    }

    AppCoder(AiCommand command) {
        this.command = command;
    }

    /** 根据模型设计需求生成模型设计代码 */
    public String genModelDesign(String requirements) {
        AiPromptGenerator promptGenerator = createPromptGenerator();

        String prompt = promptGenerator.genModelDesignPrompt(requirements);
        this.command.prompt(prompt);

        AiChatExchange exchange = this.command.execute(Map.of(), null);

        return promptGenerator.getModelDesignCodeFromResponse(exchange.getContent());
    }

    /** 根据 UI 设计需求生成 UI 设计代码 */
    public String genUiDesign(String requirements) {
        AiPromptGenerator promptGenerator = createPromptGenerator();

        String prompt = promptGenerator.genUiDesignPrompt(requirements);
        this.command.prompt(prompt);

        AiChatExchange exchange = this.command.execute(Map.of(), null);

        return promptGenerator.getUiDesignCodeFromResponse(exchange.getContent());
    }

    private AiPromptGenerator createPromptGenerator() {
        IPromptTemplateManager promptTemplateManager = BeanContainer.getBeanByType(IPromptTemplateManager.class);

        return new AiPromptGenerator(promptTemplateManager);
    }
}
