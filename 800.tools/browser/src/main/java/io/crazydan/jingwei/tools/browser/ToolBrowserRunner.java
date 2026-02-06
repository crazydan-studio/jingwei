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

package io.crazydan.jingwei.tools.browser;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.crazydan.duzhou.framework.commons.FileHelper;
import io.crazydan.duzhou.framework.commons.PnpmRunner;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.nop.api.core.exceptions.NopException;
import io.nop.core.lang.json.JsonTool;
import io.nop.core.resource.IResource;
import io.nop.core.resource.impl.ClassPathResource;

import static io.crazydan.jingwei.tools.browser.ToolBrowserConfigs.CFG_BROWSER_API_TOKEN;
import static io.crazydan.jingwei.tools.browser.ToolBrowserConfigs.CFG_BROWSER_DATA_DIR;
import static io.crazydan.jingwei.tools.browser.ToolBrowserConfigs.CFG_BROWSER_SERVER_HOST;
import static io.crazydan.jingwei.tools.browser.ToolBrowserConfigs.CFG_BROWSER_SERVER_PORT;
import static io.crazydan.jingwei.tools.browser.ToolBrowserErrors.ERR_CFG_VALUE_NOT_SPECIFIED;
import static io.nop.xlang.XLangErrors.ARG_NAME;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-06
 */
public class ToolBrowserRunner {
    public static final String CLASSPATH_EXEC = "classpath:exec/";

    public static final String FILE_MANIFEST = "manifest.json";
    public static final String FILE_CONFIG = "config.json";
    public static final String DIR_EXEC = ".exec";
    public static final String DIR_AUTH = "auth";

    public static final String KEY_HOST = "host";
    public static final String KEY_PORT = "port";
    public static final String KEY_TOKEN = "token";

    public void run() {
        checkConfigs();

        File dataDir = FileHelper.getAbsoluteFile(new File(CFG_BROWSER_DATA_DIR.get()));
        prepareConfig(dataDir);

        File execDir = new File(dataDir, DIR_EXEC);
        prepareExec(execDir);

        PnpmRunner pnpm = new PnpmRunner(execDir);
        pnpm.runScript("start", new String[] { "--config=../" + FILE_CONFIG, "--auth-dir=../" + DIR_AUTH });
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void checkConfigs() {
        if (CFG_BROWSER_SERVER_PORT.get() == null) {
            throw new NopException(ERR_CFG_VALUE_NOT_SPECIFIED).param(ARG_NAME, CFG_BROWSER_SERVER_PORT.getName());
        }
        if (StringHelper.isBlank(CFG_BROWSER_DATA_DIR.get())) {
            throw new NopException(ERR_CFG_VALUE_NOT_SPECIFIED).param(ARG_NAME, CFG_BROWSER_DATA_DIR.getName());
        }
    }

    private void prepareExec(File execDir) {
        FileHelper.deleteDir(execDir);

        IResource manifest = new ClassPathResource(CLASSPATH_EXEC + FILE_MANIFEST);
        Collection<String> filePaths = (Collection<String>) JsonTool.parseBeanFromResource(manifest);
        for (String filePath : filePaths) {
            File target = new File(execDir, filePath);

            IResource resource = new ClassPathResource(CLASSPATH_EXEC + filePath);
            resource.saveToFile(target);
        }
    }

    private void prepareConfig(File dataDir) {
        Map<String, Object> config = new HashMap<>();
        config.put(KEY_HOST, CFG_BROWSER_SERVER_HOST.get());
        config.put(KEY_PORT, CFG_BROWSER_SERVER_PORT.get());
        config.put(KEY_TOKEN, CFG_BROWSER_API_TOKEN.get());

        File configFile = new File(dataDir, FILE_CONFIG);
        FileHelper.writeJson(configFile, config, StringHelper.ENCODING_UTF8);
    }
}
