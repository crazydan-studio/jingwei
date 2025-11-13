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

import java.util.List;
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
 * @date 2025-11-13
 */
public class TestPersonRelationshipBizModel extends TestBizModel {

    @EnableSnapshot(localDb = false)
    @Test
    public void test_save_person_relationship() {
        Map<String, Object> data = prepareData();
        assertNotNull(data);

        String id = data.remove("oid").toString();
        String sid = data.remove("sid").toString();
        String tid = data.remove("tid").toString();
        output("relationship.json5", data);

        String type = data.get("type").toString();

        GraphQLRequestBean request = new GraphQLRequestBean();
        request.setQuery(
                "query($id:String){ PersonRelationship__get(id:$id){ oid,type,sid:sourceId,tid:targetId,source{relationships{oid,tid:targetId}},target{inverseRelationships{oid,sid:sourceId}} } }");
        request.setVariables(Map.of("id", id));

        data = graphql(request);
        assertNotNull(data);
        assertEquals(id, data.get("oid"));
        assertEquals(sid, data.get("sid"));
        assertEquals(tid, data.get("tid"));
        assertEquals(type, data.get("type"));

        Map<String, Object> source = (Map<String, Object>) data.get("source");
        Map<String, Object> relateWith = (Map<String, Object>) ((List<?>) source.get("relationships")).get(0);
        assertEquals(id, relateWith.get("oid"));
        assertEquals(tid, relateWith.get("tid"));

        Map<String, Object> target = (Map<String, Object>) data.get("target");
        Map<String, Object> relatedBy = (Map<String, Object>) ((List<?>) target.get("inverseRelationships")).get(0);
        assertEquals(id, relatedBy.get("oid"));
        assertEquals(sid, relatedBy.get("sid"));
    }

    @EnableSnapshot(localDb = false)
    @Test
    public void test_delete_person_relationship() {
        Map<String, Object> data = prepareData();
        String id = data.remove("oid").toString();
        String sid = data.remove("sid").toString();
        String tid = data.remove("tid").toString();

        // 删除 target 时，级联删除关系
        GraphQLRequestBean request = new GraphQLRequestBean();
        request.setQuery("mutation($id:String){ Person__delete(id:$id) }");
        request.setVariables(Map.of("id", tid));
        assertTrue((Boolean) graphql(request));

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ PersonRelationship__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", id));
        data = graphql(request);
        assertNull(data);

        // - source 保留
        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", sid));
        data = graphql(request);
        assertEquals(sid, data.get("oid"));

        // 删除 source 时，级联删除关系
        data = prepareData();
        id = data.remove("oid").toString();
        sid = data.remove("sid").toString();
        tid = data.remove("tid").toString();

        request = new GraphQLRequestBean();
        request.setQuery("mutation($id:String){ Person__delete(id:$id) }");
        request.setVariables(Map.of("id", sid));
        assertTrue((Boolean) graphql(request));

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ PersonRelationship__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", id));
        data = graphql(request);
        assertNull(data);

        // - target 保留
        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", tid));
        data = graphql(request);
        assertEquals(tid, data.get("oid"));

        // 删除关系不影响 source 和 target
        data = prepareData();
        id = data.remove("oid").toString();
        sid = data.remove("sid").toString();
        tid = data.remove("tid").toString();

        request = new GraphQLRequestBean();
        request.setQuery("mutation($id:String){ PersonRelationship__delete(id:$id) }");
        request.setVariables(Map.of("id", id));
        assertTrue((Boolean) graphql(request));

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", sid));
        data = graphql(request);
        assertEquals(sid, data.get("oid"));

        request = new GraphQLRequestBean();
        request.setQuery("query($id:String){ Person__get(id:$id,ignoreUnknown:true){ oid } }");
        request.setVariables(Map.of("id", tid));
        data = graphql(request);
        assertEquals(tid, data.get("oid"));
    }

    private Map<String, Object> prepareData() {
        GraphQLRequestBean request = input("../../data/person-1.json5", GraphQLRequestBean.class);
        Map<String, Object> source = graphql(request);
        assertNotNull(source);

        request = input("../../data/person-2.json5", GraphQLRequestBean.class);
        Map<String, Object> target = graphql(request);
        assertNotNull(target);

        String sid = source.get("oid").toString();
        String tid = target.get("oid").toString();
        String type = "sibling";
        request = input("../../data/relationship.json5", GraphQLRequestBean.class);
        request.getVariables().putAll(Map.of("sid", sid, //
                                             "tid", tid, //
                                             "type", type));
        Map<String, Object> data = graphql(request);
        assertNotNull(data);

        return data;
    }
}
