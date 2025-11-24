package io.crazydan.jingwei.ui.schema.component.message._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/component/message.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiComponentMessage extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  消息名
     * xml name: name
     * > 其名字必须为首字母大写的驼峰形式（可包含下划线）
     */
    private java.lang.String _name ;
    
    /**
     * 消息名
     * xml name: name
     *  > 其名字必须为首字母大写的驼峰形式（可包含下划线）
     */
    
    public java.lang.String getName(){
      return _name;
    }

    
    public void setName(java.lang.String value){
        checkAllowChange();
        
        this._name = value;
           
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
        
        out.putNotNull("name",this.getName());
    }

    public XuiComponentMessage cloneInstance(){
        XuiComponentMessage instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiComponentMessage instance){
        super.copyTo(instance);
        
        instance.setName(this.getName());
    }

    protected XuiComponentMessage newInstance(){
        return (XuiComponentMessage) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
