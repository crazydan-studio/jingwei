package io.crazydan.jingwei.ui.schema.component.tree._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/component/tree.xdef <p>
 * > 在 `test` 表达式的结果为 `true` 时，获得其子节点。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiComponentTreeNodeStatementChooseWhen extends io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementCond {
    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
    }

    public XuiComponentTreeNodeStatementChooseWhen cloneInstance(){
        XuiComponentTreeNodeStatementChooseWhen instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiComponentTreeNodeStatementChooseWhen instance){
        super.copyTo(instance);
        
    }

    protected XuiComponentTreeNodeStatementChooseWhen newInstance(){
        return (XuiComponentTreeNodeStatementChooseWhen) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
