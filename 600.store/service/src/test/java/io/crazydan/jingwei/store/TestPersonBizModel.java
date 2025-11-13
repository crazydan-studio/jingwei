/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2025 Crazydan Studio <https://studio.crazydan.org>
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

package io.crazydan.jingwei.store;

import java.util.Map;

import io.crazydan.duzhou.framework.junit.NopJunitAutoTestCase;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.api.core.beans.ApiRequest;
import io.nop.api.core.beans.ApiResponse;
import io.nop.graphql.core.IGraphQLExecutionContext;
import io.nop.graphql.core.engine.IGraphQLEngine;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-12
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", initDatabaseSchema = true)
public class TestPersonBizModel extends NopJunitAutoTestCase {
    @Inject
    IGraphQLEngine graphQLEngine;

    @EnableSnapshot(localDb = false)
    @Test
    public void test_save_person() {
        ApiRequest<?> request = request("request.json5", Map.class);

        IGraphQLExecutionContext ctx = graphQLEngine.newRpcContext(null, "Person__save", request);

        ApiResponse<?> response = graphQLEngine.executeRpc(ctx);
        assertTrue(response.isOk());
        assertInstanceOf(Map.class, response.getData());
        assertNotNull(((Map<?, ?>) response.getData()).remove("oid"));

        output("response.json5", response);
    }
}
