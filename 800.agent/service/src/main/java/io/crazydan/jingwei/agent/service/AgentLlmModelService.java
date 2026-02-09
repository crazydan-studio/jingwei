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

package io.crazydan.jingwei.agent.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.agent.model.AgentLlmModel;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.api.messages.Prompt;
import io.nop.ai.core.model.LlmModel;
import io.nop.ai.core.service.DefaultAiChatService;
import io.nop.api.core.config.AppConfig;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.ICancelToken;
import io.nop.core.reflect.bean.BeanTool;
import io.nop.http.api.client.HttpRequest;
import io.nop.http.api.client.IHttpClient;
import io.nop.http.api.client.IHttpResponse;
import jakarta.inject.Inject;

import static io.crazydan.jingwei.agent.AgentServiceConfigs.CFG_AGENT_API_TOKEN;
import static io.crazydan.jingwei.agent.AgentServiceConfigs.CFG_AGENT_SERVER_BASE_URL;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_NO_LLM_FOUND;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_NO_LLM_SPECIFIED;
import static io.nop.ai.core.AiCoreConfigs.CFG_AI_SERVICE_LOG_MESSAGE;
import static io.nop.ai.core.AiCoreErrors.ARG_LLM_NAME;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
public class AgentLlmModelService extends DefaultAiChatService implements IAgentLlmModelService {
    private IHttpClient httpClient;

    @Inject
    public void setHttpClient(IHttpClient httpClient) {
        super.setHttpClient(httpClient);
        this.httpClient = httpClient;
    }

    @Override
    public List<AgentLlmModel> getLlmModels() {
        String url = getBaseUrl() + "/models";
        HttpRequest request = HttpRequest.get(url);

        IHttpResponse response = this.httpClient.fetch(request, null);
        if (response.getHttpStatus() != 200) {
            return List.of();
        }

        List<?> results = response.getBodyAsBean(List.class);

        return BeanTool.castListItemToType(results, AgentLlmModel.class);
    }

    @Override
    protected CompletionStage<AiChatExchange> doSendChat(
            String llmName, LlmModel llmModel, Prompt prompt,
            AiChatOptions options, ICancelToken cancelToken
    ) {
        AppConfig.getConfigProvider().updateConfigValue(CFG_AI_SERVICE_LOG_MESSAGE, false);

        return super.doSendChat(llmName, llmModel, prompt, options, cancelToken);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected String getLlmName(AiChatOptions options) {
        String llm = options.getProvider();

        if (StringHelper.isBlank(llm)) {
            throw new NopException(ERR_AGENT_SERVICE_NO_LLM_SPECIFIED);
        }
        return llm;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected LlmModel loadLlmModel(String llmName) {
        return getLlmModels().stream()
                             .filter((m) -> m.getName().equals(llmName))
                             .findFirst()
                             .orElseThrow(() -> new NopException(ERR_AGENT_SERVICE_NO_LLM_FOUND).param(ARG_LLM_NAME,
                                                                                                       llmName));
    }

    @Override
    protected String getBaseUrl(String llmName, LlmModel llmModel, String model) {
        String baseUrl = getBaseUrl();

        return StringHelper.appendPath(baseUrl, llmName);
    }

    @Override
    protected String getApiKey(String llmName) {
        return CFG_AGENT_API_TOKEN.get();
    }

    protected String getBaseUrl() {
        String baseUrl = CFG_AGENT_SERVER_BASE_URL.get();

        return StringHelper.appendPath(baseUrl, "/llm");
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void initBody(
            String llmName, LlmModel llmModel, String model, Map<String, Object> body, Prompt prompt,
            AiChatOptions options
    ) {
        super.initBody(llmName, llmModel, model, body, prompt, options);
    }
}
