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

import io.crazydan.duzhou.framework.junit.NopJunitAutoTestCase;
import io.nop.api.core.annotations.autotest.EnableSnapshot;
import io.nop.api.core.annotations.autotest.NopTestConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-01
 */
@Disabled
@NopTestConfig(testConfigFile = "classpath:/application.yaml", localDb = true)
public class TestAppCoder extends NopJunitAutoTestCase {

    @EnableSnapshot
    @Test
    public void test_app_design() {
        AppCoder coder = AppCoder.create(null, null);

        String requirements = inputText("model-requirements.md");
        String prompt = coder.genModelDesignPrompt(requirements);
        outputText("prompt-model-design.md", prompt);

        //
        requirements = inputText("ui-requirements.md");
        prompt = coder.genUiDesignPrompt(requirements);
        outputText("prompt-ui-design.md", prompt);
    }

    @EnableSnapshot
    @Test
    public void test_ai_coder() {
        AppCoder coder = AppCoder.create("bailian", "qwen-coder-plus");

        String requirements = inputText("model-requirements.md");
        AppCoderCode code = coder.genModelDesignCode(requirements);
        System.out.println(code.getContent());

        requirements = inputText("ui-requirements.md");
        code = coder.genUiDesignCode(requirements);
        System.out.println(code.getContent());
    }
}
