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

package io.crazydan.jingwei.store.service;

import java.util.Map;

import io.crazydan.jingwei.store.TestBizModel;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.beans.graphql.GraphQLRequestBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-11-12
 */
public class TestPersonBizModel extends TestBizModel {

    @EnableSnapshot(localDb = false)
    @Test
    public void test_save_person() {
        Map<String, Object> data = prepareData();

        String id = data.remove("oid").toString();
        output("response.json5", data);

        GraphQLRequestBean request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id){ ...F_defaults } }");
        request.setVariables(Map.of("id", id));

        data = graphql(request);
        assertNotNull(data);
        assertEquals(id, data.get("oid"));
    }

    @EnableSnapshot(localDb = false)
    @Test
    public void test_delete_person() {
        Map<String, Object> data = prepareData();
        String id = data.remove("oid").toString();

        GraphQLRequestBean request = new GraphQLRequestBean();
        request.setQuery("mutation($id:String){ Person__delete(id:$id) }");
        request.setVariables(Map.of("id", id));
        assertTrue((Boolean) graphql(request));

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", id));
        data = graphql(request);
        assertNull(data);

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__deleted_get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", id));
        data = graphql(request);
        assertEquals(id, data.get("oid"));
    }

    private Map<String, Object> prepareData() {
        GraphQLRequestBean request = input("../../data/person-1.json5", GraphQLRequestBean.class);
        Map<String, Object> data = graphql(request);
        assertNotNull(data);

        return data;
    }
}
