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

package io.crazydan.jingwei.ai.coder;

import java.util.Map;

import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.crazydan.jingwei.app.AppGenConfig;
import io.crazydan.jingwei.app.AppGenerator;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.core.lang.xml.XNode;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.InMemoryTextResource;
import io.nop.orm.model.OrmModelConstants;
import io.nop.xlang.xdsl.DslModelHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = true)
public class TestAiModelDesign extends NopJunitTestCase {

    @Test
    public void test_genOrmModel() {
        String resourcePath = "test-01.ai-model-design.xml";
        AppGenConfig genConfig = createAppGenConfig();
        AiOrmModel ormModel = genOrmModel(resourcePath, genConfig);
        assertNotNull(ormModel);

        XNode node = DslModelHelper.dslModelToXNode(OrmModelConstants.XDSL_SCHEMA_ORM, ormModel);
        assertEquals(attachmentXmlText("test-01-result.ai-model-design.xml"), node.xml());
    }

    @Test
    public void get_genAppModel() {
        String resourcePath = "test-01.ai-model-design.xml";

        AppGenerator gen = new AppGenerator();
        AppGenConfig genConfig = createAppGenConfig();

        gen.genModel(createResource(resourcePath), genConfig);
    }

    protected IResource createResource(String path) {
        return new InMemoryTextResource("/text/" + path, attachmentXmlText(path));
    }

    protected AiOrmModel genOrmModel(String path, AppGenConfig appGenConfig) {
        IResource resource = createResource(path);
        AiModelDesign modelDesign = new AiModelDesign(resource, appGenConfig);

        Map<String, Object> vars = Map.of();

        return modelDesign.genOrmModel(vars);
    }

    protected AppGenConfig createAppGenConfig() {
        AppGenConfig appGenConfig = new AppGenConfig();

        appGenConfig.setCode("16834a2287e74be796b6493008e22ac4");
        appGenConfig.setDomain("app");
        appGenConfig.setDbQuerySpace("user");
        appGenConfig.setDbTablePrefix("tbl_3mk2yf_");

        return appGenConfig;
    }
}
