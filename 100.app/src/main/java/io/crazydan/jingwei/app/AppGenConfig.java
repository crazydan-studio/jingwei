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

package io.crazydan.jingwei.app;

import java.util.Locale;

import io.nop.commons.util.StringHelper;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-01-06
 */
public class AppGenConfig {
    private String code;
    /** 应用所处的业务域标识 */
    private String domain;

    /** 数据库表前缀，不能为 {@code null} 或空白 */
    private String dbTablePrefix;
    /** 数据库查询空间，用于区分用户数据空间和开发数据空间，不能为 {@code null} 或空白 */
    private String dbQuerySpace;

    public static String genAppCode() {
        return StringHelper.generateUUID();
    }

    public static String genDbTablePrefix() {
        return "tbl_" + StringHelper.randomString(6).toLowerCase(Locale.ROOT) + '_';
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String appDomain) {
        this.domain = appDomain;
    }

    public String getDbTablePrefix() {
        return this.dbTablePrefix;
    }

    public void setDbTablePrefix(String dbTablePrefix) {
        this.dbTablePrefix = dbTablePrefix;
    }

    public String getDbQuerySpace() {
        return this.dbQuerySpace;
    }

    public void setDbQuerySpace(String dbQuerySpace) {
        this.dbQuerySpace = dbQuerySpace;
    }
}
