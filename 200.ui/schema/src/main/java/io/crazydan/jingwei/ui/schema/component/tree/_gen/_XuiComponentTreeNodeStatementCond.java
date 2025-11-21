package io.crazydan.jingwei.ui.schema.component.tree._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementCond;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/component/tree.xdef <p>
 * > 在 `test` 表达式的结果为 `true` 时，获得其子节点。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiComponentTreeNodeStatementCond extends io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNode {
    
    /**
     *  条件表达式
     * xml name: test
     * > 如 `${name != null}`
     */
    private java.lang.String _test ;
    
    /**
     * 条件表达式
     * xml name: test
     *  > 如 `${name != null}`
     */
    
    public java.lang.String getTest(){
      return _test;
    }

    
    public void setTest(java.lang.String value){
        checkAllowChange();
        
        this._test = value;
           
    }

    

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
        
        out.putNotNull("test",this.getTest());
    }

    public XuiComponentTreeNodeStatementCond cloneInstance(){
        XuiComponentTreeNodeStatementCond instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiComponentTreeNodeStatementCond instance){
        super.copyTo(instance);
        
        instance.setTest(this.getTest());
    }

    protected XuiComponentTreeNodeStatementCond newInstance(){
        return (XuiComponentTreeNodeStatementCond) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
