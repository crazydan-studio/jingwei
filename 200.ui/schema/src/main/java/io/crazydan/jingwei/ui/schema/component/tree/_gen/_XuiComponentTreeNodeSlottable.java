package io.crazydan.jingwei.ui.schema.component.tree._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeSlottable;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/component/tree.xdef <p>
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiComponentTreeNodeSlottable extends io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNode {
    
    /**
     *  对应的插槽名字
     * xml name: xui-slot
     * > 其将替换所属组件在 `<template/>` 中定义的同名 `<slot/>` 节点
     */
    private java.lang.String _xuiSlot ;
    
    /**
     * 对应的插槽名字
     * xml name: xui-slot
     *  > 其将替换所属组件在 `<template/>` 中定义的同名 `<slot/>` 节点
     */
    
    public java.lang.String getXuiSlot(){
      return _xuiSlot;
    }

    
    public void setXuiSlot(java.lang.String value){
        checkAllowChange();
        
        this._xuiSlot = value;
           
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
        
        out.putNotNull("xuiSlot",this.getXuiSlot());
    }

    public XuiComponentTreeNodeSlottable cloneInstance(){
        XuiComponentTreeNodeSlottable instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiComponentTreeNodeSlottable instance){
        super.copyTo(instance);
        
        instance.setXuiSlot(this.getXuiSlot());
    }

    protected XuiComponentTreeNodeSlottable newInstance(){
        return (XuiComponentTreeNodeSlottable) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
