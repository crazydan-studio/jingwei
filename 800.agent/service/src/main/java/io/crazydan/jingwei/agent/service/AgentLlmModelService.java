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
import java.util.concurrent.TimeUnit;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.duzhou.framework.exception.NopNeedMoreActionException;
import io.crazydan.jingwei.agent.model.AgentLlmModel;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.messages.AiAssistantMessage;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.api.messages.Prompt;
import io.nop.ai.core.model.LlmModel;
import io.nop.ai.core.service.DefaultAiChatService;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizMutation;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Name;
import io.nop.api.core.annotations.core.Optional;
import io.nop.api.core.config.AppConfig;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.Guard;
import io.nop.api.core.util.ICancelToken;
import io.nop.commons.cache.LocalCache;
import io.nop.core.reflect.bean.BeanTool;
import io.nop.http.api.client.HttpRequest;
import io.nop.http.api.client.IHttpClient;
import io.nop.http.api.client.IHttpResponse;
import jakarta.inject.Inject;

import static io.crazydan.jingwei.agent.AgentServiceConfigs.CFG_AGENT_API_TOKEN;
import static io.crazydan.jingwei.agent.AgentServiceConfigs.CFG_AGENT_SERVER_BASE_URL;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_CALLING_FAILED;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_LLM_CHAT_FAILED;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_NO_LLM_FOUND;
import static io.crazydan.jingwei.agent.AgentServiceErrors.ERR_AGENT_SERVICE_NO_LLM_SPECIFIED;
import static io.nop.ai.core.AiCoreConfigs.CFG_AI_SERVICE_LOG_MESSAGE;
import static io.nop.ai.core.AiCoreErrors.ARG_LLM_NAME;
import static io.nop.commons.cache.CacheConfig.newConfig;
import static io.nop.xlang.XLangErrors.ARG_ERROR;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
@BizModel(AgentLlmModelService.BIZ_NAME)
public class AgentLlmModelService extends DefaultAiChatService implements IAgentLlmModelService {
    public static final String BIZ_NAME = "LlmAgent";

    public static final String URL_LLM = "/llm";
    public static final String URL_MODELS = "/models";
    public static final String URL_ACTION = "/action";

    public static final String KEY_KEEP_SESSION = "keep_session";
    public static final String KEY_SESSION_ID = "session_id";

    private IHttpClient httpClient;
    private LocalCache<String, List<AgentLlmModel>> modelsCache = //
            LocalCache.newCache("llm-models-cache", newConfig(1, 5 * 60 * 1000), (key) -> doGetLlmModels());

    @Inject
    public void setHttpClient(IHttpClient httpClient) {
        super.setHttpClient(httpClient);
        this.httpClient = httpClient;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Description("提交「需更多处理」的数据")
    @BizMutation
    public void needMoreAction(
            @Name("action") String action, @Name("provider") String provider,
            @Optional @Name("data") String data
    ) {
        String url = getBaseUrl(provider, null, null) + URL_ACTION;
        HttpRequest request = HttpRequest.post(url);
        request.param("name", action);
        // Note: 缺省设置的 header 为 HttpApiConstants#CONTENT_TYPE_JSON
        request.setBody(StringHelper.isNotBlank(data) ? data : "{}");

        doSendRequest(request, provider);
    }

    @Description("与大模型对话")
    @BizMutation
    public CompletionStage<AiAssistantMessage> chat(
            @Description("大模型的提供商") @Name("provider") String provider,
            @Description("大模型的模型名") @Name("model") String model,
            @Description("会话标识") @Optional @Name("sessionId") String sessionId,
            @Description("对话内容") @Name("content") String content
    ) {
        Guard.checkState(StringHelper.isNotBlank(content), "the parameter 'content' is null or blank");

        AiChatOptions options = new AiChatOptions();
        options.setRequestTimeout(TimeUnit.MINUTES.toMillis(10));
        options.setProvider(provider);
        options.setModel(model);
        // Note: 在 Agent 侧根据 session id 记录历史对话
        options.setSessionId(sessionId);

        Prompt prompt = new Prompt();
        prompt.addMetadata(KEY_KEEP_SESSION, true);
        prompt.addUserMessage(content);

        return sendChatAsync(prompt, options, null).thenApply((exchange) -> {
            String sid = (String) exchange.getResponse().getMetadata(KEY_SESSION_ID);

            AiAssistantMessage msg = new AiAssistantMessage();
            msg.addMetadata(KEY_SESSION_ID, sid);
            msg.setContent(exchange.getContent());

            return msg;
        });
    }

    // TODO 补充根据 session id 获取历史对话的接口

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public List<AgentLlmModel> getLlmModels() {
        return this.modelsCache.get("llm-models");
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

        return StringHelper.appendPath(baseUrl, URL_LLM);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void initBody(
            String llmName, LlmModel llmModel, String model, Map<String, Object> body, Prompt prompt,
            AiChatOptions options
    ) {
        setIfNotNull(body, KEY_KEEP_SESSION, prompt.getMetadata(KEY_KEEP_SESSION));
        setIfNotNull(body, KEY_SESSION_ID, options.getSessionId());

        super.initBody(llmName, llmModel, model, body, prompt, options);
    }

    @Override
    protected void parseHttpResponse(
            String llmName, LlmModel llmModel, Map<String, Object> response,
            AiChatExchange chatResponse
    ) {
        processResponse(response, llmName);

        super.parseHttpResponse(llmName, llmModel, response, chatResponse);

        String sessionId = (String) response.get(KEY_SESSION_ID);
        chatResponse.getResponse().addMetadata(KEY_SESSION_ID, sessionId);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected List<AgentLlmModel> doGetLlmModels() {
        String url = getBaseUrl() + URL_MODELS;
        HttpRequest request = HttpRequest.get(url);

        Map<String, Object> response = doSendRequest(request, null);

        return BeanTool.castListItemToType((List<?>) response.get("data"), AgentLlmModel.class);
    }

    protected Map<String, Object> doSendRequest(HttpRequest request, String llmName) {
        request.bearerToken(getApiKey(null));

        IHttpResponse resp = this.httpClient.fetch(request, null);
        if (resp.getHttpStatus() != 200) {
            throw new NopException(ERR_AGENT_SERVICE_CALLING_FAILED).param(ARG_ERROR, resp.getHttpStatus());
        }

        Map<String, Object> response = resp.getBodyAsBean(Map.class);
        processResponse(response, llmName);

        return response;
    }

    protected void processResponse(Map<String, Object> response, String llmName) {
        Object form = response.get("form");
        String error = (String) response.get("error");
        boolean needMoreAction = Boolean.TRUE.equals(response.get("need_more_action"));

        if (needMoreAction && form instanceof Map) {
            Map<String, Object> map = (Map) form;
            map.put("graphql", //
                    "mutation($action:String,$data:String){" //
                    + BIZ_NAME + "__needMoreAction(" //
                    + "action:$action" //
                    + ",provider:\"" + llmName + '"' //
                    + ",data:$data)" //
                    + "}");

            throw new NopNeedMoreActionException(map);
        } //
        else if (StringHelper.isNotBlank(error)) {
            if (llmName != null) {
                throw new NopException(ERR_AGENT_SERVICE_LLM_CHAT_FAILED).param(ARG_LLM_NAME, llmName)
                                                                         .param(ARG_ERROR, error);
            } else {
                throw new NopException(ERR_AGENT_SERVICE_CALLING_FAILED).param(ARG_ERROR, error);
            }
        }
    }
}
