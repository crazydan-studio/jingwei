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
import io.crazydan.jingwei.app.coder.prompt.AiPromptGenerator;
import io.nop.ai.core.prompt.IPromptTemplateManager;
import io.nop.commons.util.MavenDirHelper;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.FileResource;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-29
 */
public class TestAiCoder extends NopJunitTestCase {
    @Inject
    IPromptTemplateManager promptTemplateManager;

    @Test
    public void test_gen_prompt() {
        File projectDir = getProjectDir();

        AiPromptGenerator promptGenerator = new AiPromptGenerator(this.promptTemplateManager);

        File source = new File(projectDir, "src/main/resources/app/source/model-requirements.md");
        String requirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);
        String prompt = promptGenerator.genModelDesignPrompt(requirements);

        File target = new File(projectDir, "src/test/resources/cases/TestAiCoder/prompt-model-design.md");
        IResource resource = new FileResource("/text", target);
        resource.writeText(prompt, StringHelper.ENCODING_UTF8);

        //
        source = new File(projectDir, "src/main/resources/app/source/ui-requirements.md");
        requirements = FileHelper.readText(source, StringHelper.ENCODING_UTF8);
        prompt = promptGenerator.genUiDesignPrompt(requirements);

        target = new File(projectDir, "src/test/resources/cases/TestAiCoder/prompt-ui-design.md");
        resource = new FileResource("/text", target);
        resource.writeText(prompt, StringHelper.ENCODING_UTF8);
    }

    @Test
    public void test_gen_code() {
        File projectDir = getProjectDir();

        AiPromptGenerator promptGenerator = new AiPromptGenerator(this.promptTemplateManager);

        String response = attachmentText("response-model-design.md");
        String code = promptGenerator.getModelDesignCodeFromResponse(response);

        File target = new File(projectDir, "src/main/resources/app/source/model-design.xml");
        IResource resource = new FileResource("/text", target);
        resource.writeText(code, StringHelper.ENCODING_UTF8);

        //
        response = attachmentText("response-ui-design.md");
        code = promptGenerator.getUiDesignCodeFromResponse(response);

        target = new File(projectDir, "src/main/resources/app/source/ui-design.xml");
        resource = new FileResource("/text", target);
        resource.writeText(code, StringHelper.ENCODING_UTF8);
    }

    protected File getProjectDir() {
        return MavenDirHelper.projectDir(getClass());
    }
}
