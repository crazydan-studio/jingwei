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

package io.crazydan.jingwei.app;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import io.crazydan.duzhou.framework.junit.NopJunitAutoTestCase;
import io.crazydan.jingwei.agent.model.AgentLlmModel;
import io.crazydan.jingwei.agent.service.AgentLlmModelService;
import io.nop.ai.core.api.chat.AiChatOptions;
import io.nop.ai.core.api.chat.IAiChatService;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.ai.core.api.messages.Prompt;
import io.nop.ai.core.service.DefaultAiChatService;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.api.core.util.FutureHelper;
import io.nop.api.core.util.ICancelToken;
import io.nop.core.lang.json.JsonTool;
import io.nop.http.api.client.DownloadOptions;
import io.nop.http.api.client.HttpRequest;
import io.nop.http.api.client.IHttpClient;
import io.nop.http.api.client.IHttpInputFile;
import io.nop.http.api.client.IHttpOutputFile;
import io.nop.http.api.client.IHttpResponse;
import io.nop.http.api.client.UploadOptions;
import io.nop.http.api.support.DefaultHttpResponse;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = false)
public class TestAgentLlmModelService extends NopJunitAutoTestCase {
    @Inject
    IAiChatService chatService;
    @Inject
    AgentLlmModelService llmModelService;

    @EnableSnapshot
    @Test
    public void test_getLlmModels() {
        MockHttpClient httpClient = new MockHttpClient(null, "response.json");
        this.llmModelService.setHttpClient(httpClient);

        List<AgentLlmModel> models = this.llmModelService.getLlmModels();
        String json = JsonTool.stringify(models);
        assertEquals(inputText("response.json").trim(), json);
    }

    @EnableSnapshot
    @Test
    public void test_sendChat() {
        AiChatOptions options = new AiChatOptions();
        options.setRequestTimeout(TimeUnit.MINUTES.toMillis(10));
        options.setProvider("deepseek-web");
        options.setModel("deepseek-chat");

        options.setMaxTokens(10000);
        options.setEnableThinking(true);
        options.setSessionId("f1f121ba63654218aa34bf91a5c02302");
        options.setStream(true);
        options.setEnableSystemPrompt(true);
        options.setEnableMetaPrompt(true);
        options.setEnableCognitivePrompt(true);
        options.setWorkMode("abc");
        options.setResponseFormat("markdown");
        options.setContextLength(10000);
        options.setSeed("abc");
        options.setStop(List.of("a", "b"));
        options.setTemperature(0.4f);
        options.setTopK(10);
        options.setTopP(12f);
        options.setUserId("uuuu");

        Prompt prompt = new Prompt();
        prompt.setName("test-prompt");
        prompt.addSystemMessage("System Prompt");
        prompt.addUserMessage("User Prompt");

        MockHttpClient httpClient = new MockHttpClient("request.json", "response.json");
        ((DefaultAiChatService) this.chatService).setHttpClient(httpClient);

        //
        AiChatExchange exchange = this.chatService.sendChat(prompt, options, null);
        exchange.setBeginTime(0);
        exchange.setUsedTime(0);
        exchange.setExchangeId("");

        String json = JsonTool.stringify(exchange);
        assertEquals(inputText("chat-exchange.json").trim(), json);
    }

    class MockHttpClient implements IHttpClient {
        final String requestInput;
        final String responseInput;

        MockHttpClient(String requestInput, String responseInput) {
            this.requestInput = requestInput;
            this.responseInput = responseInput;
        }

        @Override
        public CompletionStage<IHttpResponse> fetchAsync(HttpRequest request, ICancelToken cancelTokens) {
            if (this.requestInput != null) {
                String json = JsonTool.stringify(request.getBody());
                assertEquals(inputText(this.requestInput).trim(), json);
            }

            DefaultHttpResponse response = new DefaultHttpResponse();
            response.setHttpStatus(200);
            response.setBodyAsText(inputText(this.responseInput));

            return FutureHelper.success(response);
        }

        @Override
        public CompletionStage<IHttpResponse> downloadAsync(
                HttpRequest request, IHttpOutputFile targetFile,
                DownloadOptions options, ICancelToken cancelToken
        ) {
            return null;
        }

        @Override
        public CompletionStage<IHttpResponse> uploadAsync(
                HttpRequest request, IHttpInputFile inputFile,
                UploadOptions options, ICancelToken cancelToken
        ) {
            return null;
        }
    }
}
