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

package io.crazydan.jingwei.apps;

import java.io.File;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.duzhou.framework.junit.NopJunitTestCase;
import io.crazydan.jingwei.app.coder.AppCoder;
import io.nop.commons.util.MavenDirHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-29
 */
@Disabled
public class TestAppCoder extends NopJunitTestCase {

    @Test
    public void test_gen_model_prompt() {
        String name = getClass().getSimpleName();
        File projectDir = getProjectDir();

        AppCoder coder = AppCoder.create(null, null);

        File source = new File(projectDir, "src/main/resources/app/source/biz-requirements.md");
        String bizRequirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);

        source = new File(projectDir, "src/main/resources/app/source/model-requirements.md");
        String modelRequirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);
        String prompt = coder.genModelDesignPrompt(bizRequirements, modelRequirements);

        File target = new File(projectDir, "src/test/resources/cases/" + name + "/prompt-model-design.md");
        IResource resource = new FileResource("/text", target);
        resource.writeText(prompt, StringHelper.ENCODING_UTF8);
    }

    @Test
    public void test_gen_model_code() {
        File projectDir = getProjectDir();

        AppCoder coder = AppCoder.create(null, null);

        String response = attachmentText("response-model-design.md");
        String code = coder.parseModelDesignCodeFromChat(response);

        File target = new File(projectDir, "src/main/resources/app/source/model-design.xml");
        IResource resource = new FileResource("/text", target);
        resource.writeText(code, StringHelper.ENCODING_UTF8);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void test_gen_ui_prompt() {
        String name = getClass().getSimpleName();
        File projectDir = getProjectDir();

        AppCoder coder = AppCoder.create(null, null);

        File source = new File(projectDir, "src/main/resources/app/source/biz-requirements.md");
        String bizRequirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);

        source = new File(projectDir, "src/main/resources/app/source/model-design.xml");
        String bizModelDefs = FileHelper.readText(source, StringHelper.ENCODING_UTF8);

        source = new File(projectDir, "src/main/resources/app/source/ui-requirements.md");
        String uiRequirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);
        String prompt = coder.genUiDesignPrompt(bizRequirements, uiRequirements, bizModelDefs);

        File target = new File(projectDir, "src/test/resources/cases/" + name + "/prompt-ui-design.md");
        IResource resource = new FileResource("/text", target);
        resource.writeText(prompt, StringHelper.ENCODING_UTF8);
    }

    @Test
    public void test_gen_ui_code() {
        File projectDir = getProjectDir();

        AppCoder coder = AppCoder.create(null, null);

        String response = attachmentText("response-ui-design.md");
        String code = coder.parseUiDesignCodeFromChat(response);

        File target = new File(projectDir, "src/main/resources/app/source/ui-design.xml");
        IResource resource = new FileResource("/text", target);
        resource.writeText(code, StringHelper.ENCODING_UTF8);
    }

    protected File getProjectDir() {
        return MavenDirHelper.projectDir(getClass());
    }
}
