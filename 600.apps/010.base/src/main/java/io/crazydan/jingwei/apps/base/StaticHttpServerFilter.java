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

package io.crazydan.jingwei.apps.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import io.crazydan.duzhou.framework.commons.EvalExecutor;
import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.duzhou.framework.commons.ZipHelper;
import io.crazydan.duzhou.framework.exception.NopNeedMoreActionException;
import io.nop.commons.util.IoHelper;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.http.api.HttpApiConstants;
import io.nop.http.api.HttpStatus;
import io.nop.http.api.server.IHttpServerContext;
import io.nop.http.api.server.IHttpServerFilter;
import io.nop.xlang.api.ExprEvalAction;
import io.nop.xlang.api.XLang;
import io.nop.xlang.expr.ExprPhase;

import static io.crazydan.jingwei.app.AppCoreConfigs.CFG_APP_PORTAL_CODE;

/**
 * HTTP 请求过滤器
 * <p/>
 * 其实例在 /jingwei/apps/beans/app-base.beans.xml 中注册
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-07
 */
public class StaticHttpServerFilter implements IHttpServerFilter {
    private byte[] indexHtml;

    @Override
    public int order() {
        return IHttpServerFilter.NORMAL_PRIORITY - 100;
    }

    @Override
    public CompletionStage<Void> filterAsync(IHttpServerContext context, Supplier<CompletionStage<Void>> next) {
        // 耗时的操作不能在 IO 线程上执行
        return context.executeBlocking(() -> doFilter(context, next)) //
                      .thenApply(r -> null);
    }

    private CompletionStage<Void> doFilter(IHttpServerContext context, Supplier<CompletionStage<Void>> next)
            throws IOException {
        String path = context.getRequestPath();
        if (!path.equals("/")) {
            return next.get();
        }

        context.setResponseHeader(HttpApiConstants.HEADER_CONTENT_ENCODING, HttpApiConstants.DATA_TYPE_GZIP);
        // Note: Content-Type: text/html; charset=utf-8
        // 中的 charset 将在 ContextHttpServerFilter 中统一设置
        context.setResponseContentType(HttpApiConstants.CONTENT_TYPE_HTML);

        InputStream body = genIndexHtml();
        context.sendResponse(HttpStatus.SC_OK, body);

        return null;
    }

    private synchronized InputStream genIndexHtml() throws IOException {
        if (this.indexHtml == null) {
            InputStream input = getClass().getResourceAsStream("/template/index.html");
            String tpl = IoHelper.readText(input, StringHelper.ENCODING_UTF8);

            ExprEvalAction eval = XLang.newCompileTool()
                                       .allowUnregisteredScopeVar(true)
                                       .compileTemplateExpr(null, tpl, false, ExprPhase.eval);

            String html = //
                    EvalExecutor.exec((IEvalAction) eval,
                                      new Object[] { "apiContextRoot", "" },
                                      new Object[] { "staticContextRoot", "" },
                                      new Object[] { "portalCode", CFG_APP_PORTAL_CODE.get() },
                                      new Object[] {
                                              "needMoreActionCode", NopNeedMoreActionException.ERROR_CODE
                                      });

            this.indexHtml = ZipHelper.gzip(html);
        }

        return new ByteArrayInputStream(this.indexHtml);
    }
}
