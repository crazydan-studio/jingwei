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
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.api.core.beans.graphql.GraphQLRequestBean;
import io.nop.api.core.beans.graphql.GraphQLResponseBean;
import io.nop.graphql.core.IGraphQLExecutionContext;
import io.nop.graphql.core.engine.IGraphQLEngine;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-12
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", initDatabaseSchema = true)
public abstract class TestBizModel extends NopJunitAutoTestCase {
    @Inject
    IGraphQLEngine graphQLEngine;

    protected <T> T graphql(GraphQLRequestBean request) {
        IGraphQLExecutionContext ctx = graphQLEngine.newGraphQLContext(request);

        GraphQLResponseBean response = graphQLEngine.executeGraphQL(ctx);
        assertFalse(response.hasError());
        assertInstanceOf(Map.class, response.getData());

        return (T) ((Map<?, ?>) response.getData()).values().iterator().next();
    }
}
