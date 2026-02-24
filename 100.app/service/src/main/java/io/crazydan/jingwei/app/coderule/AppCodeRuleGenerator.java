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

package io.crazydan.jingwei.app.coderule;

import java.time.LocalDateTime;
import java.util.function.LongSupplier;

import io.nop.api.core.time.ISysCalendar;
import io.nop.dao.coderule.ICodeRule;
import io.nop.dao.coderule.ICodeRuleGenerator;
import io.nop.dao.seq.ISequenceGenerator;
import jakarta.inject.Inject;

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2026-02-24
 */
public class AppCodeRuleGenerator implements ICodeRuleGenerator {
    @Inject
    ISysCalendar sysCalendar;
    @Inject
    ICodeRule codeRule;
    @Inject
    ISequenceGenerator sequenceGenerator;

    @Override
    public String generate(String codePattern, Object bean) {
        String seqName = "default";
        LocalDateTime now = this.sysCalendar.getSysDateTime();
        LongSupplier seqGenerator = () -> this.sequenceGenerator.generateLong(seqName, false);

        return this.codeRule.generate(codePattern, now, seqGenerator, bean);
    }
}
