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

package io.crazydan.jingwei.app.service;

import java.util.List;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.agent.model.AgentLlmModel;
import io.crazydan.jingwei.agent.service.IAgentLlmModelService;
import io.crazydan.jingwei.app.coder.AppCoder;
import io.nop.ai.core.api.messages.AiChatExchange;
import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.annotations.biz.BizQuery;
import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Name;
import io.nop.api.core.util.Guard;
import jakarta.inject.Inject;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-09
 */
@BizModel("AppCoder")
public class AppCoderBizModel {
    @Inject
    IAgentLlmModelService llmModelService;

    @Description("获取可用 LLM 列表")
    @BizQuery
    public List<AgentLlmModel> findLlmModels() {
        return this.llmModelService.getLlmModels();
    }

    @Description("生成模型设计代码")
    @BizQuery
    public AiChatExchange genModelDesign(
            @Name("provider") String provider, @Name("model") String model,
            @Name("requirements") String requirements
    ) {
        Guard.checkState(StringHelper.isNotBlank(requirements), "the parameter 'requirements' is null or blank");

        AppCoder appCoder = AppCoder.create(provider, model);

        return appCoder.genModelDesign(requirements);
    }

    @Description("生成 UI 设计代码")
    @BizQuery
    public AiChatExchange genUiDesign(
            @Name("provider") String provider, @Name("model") String model,
            @Name("requirements") String requirements
    ) {
        Guard.checkState(StringHelper.isNotBlank(requirements), "the parameter 'requirements' is null or blank");

        AppCoder appCoder = AppCoder.create(provider, model);

        return appCoder.genUiDesign(requirements);
    }
}
