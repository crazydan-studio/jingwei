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

import java.util.Locale;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;

/**
 * 应用构建配置
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public class AppCodeGenConfig {
    /** 应用唯一标识，不能为 {@code null} 或空白 */
    private String code;
    /** 应用所处的业务域标识，不能为 {@code null} 或空白 */
    private String bizDomain;

    public static AppCodeGenConfig from(AppReleasing_Manifest manifest) {
        AppCodeGenConfig config = new AppCodeGenConfig();

        config.setCode(manifest.getCode());
        config.setBizDomain(manifest.getBizDomain());

        return config;
    }

    public static AppCodeGenConfig from(AppInstallation_Manifest manifest) {
        AppCodeGenConfig config = new AppCodeGenConfig();

        config.setCode(manifest.getCode());
        config.setBizDomain(manifest.getBizDomain());

        return config;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizDomain() {
        return this.bizDomain;
    }

    public void setBizDomain(String appDomain) {
        this.bizDomain = appDomain.toLowerCase(Locale.ROOT);
    }

    /** 在 xlib 函数中调用 */
    public String genTableName(String entityName) {
        String prefix = getBizDomain().replace('-', '_');
        String name = StringHelper.camelCaseToUnderscore(entityName, true);

        return "tbl_" + prefix + '_' + name;
    }
}
