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

package io.crazydan.jingwei.app.coder;

import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.crazydan.jingwei.app.coder.normalizer.AiModelDesignNormalizer;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.core.lang.xml.XNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-23
 */
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = true)
public class TestAiModelDesignNormalizer extends NopJunitTestCase {

    @Test
    public void test_normalize() {
        XNode node = attachmentXNode("test-01.ai-model-design.xml");

        AiModelDesignNormalizer normalizer = new AiModelDesignNormalizer();
        normalizer.normalize(node);

        assertEquals(attachmentXmlText("test-01-result.ai-model-design.xml"), node.xml());
    }
}
