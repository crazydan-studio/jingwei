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

import io.crazydan.duzhou.framework.commons.DeltaMergerHelper;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.xlang.xdsl.XDslKeys;

import static io.crazydan.jingwei.app.coder.AppCoderConstants.ORM_DEFAULT_DOMAINS_PATH;
import static io.crazydan.jingwei.app.coder.AppCoderConstants.ORM_DEFAULT_ENTITY_PATH;
import static io.nop.orm.model.OrmModelConstants.XDSL_SCHEMA_ORM;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-13
 */
public class AiModelDesignNormalizer {

    public static XNode normalize(XNode node) {
        XDslKeys keys = XDslKeys.of(node);

        patchDomains(node, keys);

        XNode entitiesNode = node.childByTag("entities");
        if (entitiesNode != null) {
            entitiesNode.getChildren().forEach((child) -> patchEntity(child, keys));
        }

        DeltaMergerHelper.cleanNode(node, keys);

        return node;
    }

    protected static void patchDomains(XNode ormNode, XDslKeys keys) {
        XNode deltaNode = XNodeParser.instance().parseFromVirtualPath(ORM_DEFAULT_DOMAINS_PATH);

        DeltaMergerHelper.merge(ormNode, deltaNode, XDSL_SCHEMA_ORM);
    }

    protected static void patchEntity(XNode entityNode, XDslKeys keys) {
        XNode deltaNode = XNodeParser.instance().parseFromVirtualPath(ORM_DEFAULT_ENTITY_PATH);

        DeltaMergerHelper.merge(entityNode, deltaNode, "/nop/schema/orm/entity.xdef");

        entityNode.removeAttr(keys.SCHEMA);
        entityNode.removeAttrsWithPrefix("xmlns");
    }
}
