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

import java.util.Map;

import io.crazydan.jingwei.ai.coder.AiModelDesign;
import io.crazydan.jingwei.ai.coder.AiOrmModel;
import io.nop.codegen.XCodeGenerator;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.xlang.api.XLang;

import static io.crazydan.jingwei.AppConfigs.CFG_APP_INSTALL_DIR;
import static io.crazydan.jingwei.AppConstants.SCOPE_VAR_appGenConfig;
import static io.crazydan.jingwei.AppConstants.SCOPE_VAR_codeGenModel;
import static io.crazydan.jingwei.AppConstants.TEMPLATE_APP_MODULE_PATH;

/**
 * 应用构建器
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-08
 */
public class AppGenerator {

    /**
     * 构建应用模块资源，主要为 {@code app.orm.xml}、{@code *.xmeta}、{@code *.xbiz}
     *
     * @return 应用模块资源所在的目录路径
     */
    public void genModule(IResource modelDesignResource, AppGenConfig genConfig) {
        AiModelDesign modelDesign = new AiModelDesign(modelDesignResource, genConfig);

        Map<String, Object> vars = Map.of();
        AiOrmModel ormModel = modelDesign.genOrmModel(vars);

        String targetDir = CFG_APP_INSTALL_DIR.get();
        XCodeGenerator gen = new XCodeGenerator(TEMPLATE_APP_MODULE_PATH, targetDir);
        // 保持用户定制的代码不变，仅更新以下划线开头的文件
        gen.forceOverride(false);

        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue(SCOPE_VAR_appGenConfig, genConfig);
        scope.setLocalValue(SCOPE_VAR_codeGenModel, ormModel);

        gen.execute("", scope);
    }

    /** 构建应用的 UI 资源 */
    public String genUi(IResource uiDesignResource, AppGenConfig genConfig) {
        return null;
    }
}
