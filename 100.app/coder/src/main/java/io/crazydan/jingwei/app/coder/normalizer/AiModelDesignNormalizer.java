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

package io.crazydan.jingwei.app.coder.normalizer;

import java.util.ArrayList;
import java.util.List;

import io.crazydan.jingwei.app.coder.AppCoderConstants;
import io.nop.core.lang.xml.XNode;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.ORM_ONE_TO_ONE;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_PREFIX_ref;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_displayName;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_domain;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_name;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.TAG_ATTR_ref_type;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-13
 */
public class AiModelDesignNormalizer {

    /** 处理 {@link AppCoderConstants#XDSL_SCHEMA_CODER_MODEL_DESIGN} 的根节点 */
    public XNode normalize(XNode node) {
//        node.forEachChild((child) -> {
//            String childTag = child.getTagName();
//
//            if ("entities".equals(childTag)) {
//                child.forEachChild(this::normalizeEntityNode);
//            }
//        });

        return node;
    }

    protected void normalizeEntityNode(XNode node) {
        node.forEachChild((child) -> {
            String childTag = child.getTagName();

            if ("attrs".equals(childTag)) {
                normalizeEntityAttrsNode(child);
            }
        });
    }

    protected void normalizeEntityAttrsNode(XNode node) {
        List<XNode> extraAttrs = new ArrayList<>(node.getChildCount());

        node.forEachChild((child) -> {
            String name = child.attrText(TAG_ATTR_name);
            String displayName = child.attrText(TAG_ATTR_displayName);
            String refType = child.attrText(TAG_ATTR_ref_type);

            if (ORM_ONE_TO_ONE.equals(refType)) {
                name = name + "Id";
                displayName = displayName + " ID";

                XNode attr = child.cloneInstance();
                attr.removeAttrsWithPrefix(TAG_ATTR_PREFIX_ref);
                attr.setAttr(TAG_ATTR_name, name);
                attr.setAttr(TAG_ATTR_displayName, displayName);
                attr.setAttr(TAG_ATTR_domain, "uuid");

                extraAttrs.add(attr);
            }
        });

        node.appendChildren(extraAttrs);
    }
}
