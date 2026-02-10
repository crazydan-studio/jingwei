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

package io.crazydan.jingwei.agent;

import io.nop.api.core.exceptions.ErrorCode;

import static io.nop.ai.core.AiCoreErrors.ARG_LLM_NAME;
import static io.nop.api.core.exceptions.ErrorCode.define;
import static io.nop.xlang.XLangErrors.ARG_ERROR;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
public interface AgentServiceErrors {

    ErrorCode ERR_AGENT_SERVICE_NO_LLM_FOUND = //
            define("jingwei.err.agent.service.no-llm-found",
                   "指定的大语言模型 '{" + ARG_LLM_NAME + "}' 不存在",
                   ARG_LLM_NAME);
    ErrorCode ERR_AGENT_SERVICE_NO_LLM_SPECIFIED = //
            define("jingwei.err.agent.service.no-llm-specified",
                   "未指定大语言模型，请通过 AiChatOptions#setProvider 设置所要使用的大语言模型名");

    ErrorCode ERR_AGENT_SERVICE_CALLING_FAILED = //
            define("jingwei.err.agent.service.calling-failed", //
                   "代理服务调用发生错误：{" + ARG_ERROR + "}", ARG_ERROR);
    ErrorCode ERR_AGENT_SERVICE_LLM_CHAT_FAILED = //
            define("jingwei.err.agent.service.llm-chat-failed",
                   "大语言模型 '{" + ARG_LLM_NAME + "}' 调用异常：{" + ARG_ERROR + "}",
                   ARG_LLM_NAME,
                   ARG_ERROR);
}
