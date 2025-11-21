package io.crazydan.jingwei.ui.schema.page._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.page.DynamicXuiPage;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /_delta/default/jingwei/ui/schema/page.xdef <p>
 * > 用于适配 `DynamicXuiComponent`。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _DynamicXuiPage extends io.crazydan.jingwei.ui.schema.component.DynamicXuiComponent {
    

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

    public DynamicXuiPage cloneInstance(){
        DynamicXuiPage instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(DynamicXuiPage instance){
        super.copyTo(instance);
        
    }

    protected DynamicXuiPage newInstance(){
        return (DynamicXuiPage) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
