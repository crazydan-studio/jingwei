package io.crazydan.jingwei.ui.schema.component.tree._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChoose;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/component/tree.xdef <p>
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiComponentTreeNodeStatementChoose extends io.nop.core.resource.component.AbstractComponentModel implements io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeKeyed{
    
    /**
     *  
     * xml name: 
     * 
     */
    private java.lang.String _$tag ;
    
    /**
     *  缺省条件
     * xml name: otherwise
     * >
     */
    private io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseOtherwise _otherwise ;
    
    /**
     *  特定条件
     * xml name: when
     * > 在 `test` 表达式的结果为 `true` 时，获得其子节点。
     */
    private KeyedList<io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen> _whens = KeyedList.emptyList();
    
    /**
     *  唯一标识
     * xml name: xui-id
     * > 在父节点内，该标识必须唯一
     */
    private java.lang.String _xuiId ;
    
    /**
     * 
     * xml name: 
     *  
     */
    
    public java.lang.String get$tag(){
      return _$tag;
    }

    
    public void set$tag(java.lang.String value){
        checkAllowChange();
        
        this._$tag = value;
           
    }

    
    /**
     * 缺省条件
     * xml name: otherwise
     *  >
     */
    
    public io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseOtherwise getOtherwise(){
      return _otherwise;
    }

    
    public void setOtherwise(io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseOtherwise value){
        checkAllowChange();
        
        this._otherwise = value;
           
    }

    
    /**
     * 特定条件
     * xml name: when
     *  > 在 `test` 表达式的结果为 `true` 时，获得其子节点。
     */
    
    public java.util.List<io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen> getWhens(){
      return _whens;
    }

    
    public void setWhens(java.util.List<io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen> value){
        checkAllowChange();
        
        this._whens = KeyedList.fromList(value, io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen::getXuiId);
           
    }

    
    public io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen getWhen(String name){
        return this._whens.getByKey(name);
    }

    public boolean hasWhen(String name){
        return this._whens.containsKey(name);
    }

    public void addWhen(io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen> list = this.getWhens();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeStatementChooseWhen::getXuiId);
            setWhens(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_whens(){
        return this._whens.keySet();
    }

    public boolean hasWhens(){
        return !this._whens.isEmpty();
    }
    
    /**
     * 唯一标识
     * xml name: xui-id
     *  > 在父节点内，该标识必须唯一
     */
    
    public java.lang.String getXuiId(){
      return _xuiId;
    }

    
    public void setXuiId(java.lang.String value){
        checkAllowChange();
        
        this._xuiId = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._otherwise = io.nop.api.core.util.FreezeHelper.deepFreeze(this._otherwise);
            
           this._whens = io.nop.api.core.util.FreezeHelper.deepFreeze(this._whens);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("$tag",this.get$tag());
        out.putNotNull("otherwise",this.getOtherwise());
        out.putNotNull("whens",this.getWhens());
        out.putNotNull("xuiId",this.getXuiId());
    }

    public XuiComponentTreeNodeStatementChoose cloneInstance(){
        XuiComponentTreeNodeStatementChoose instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiComponentTreeNodeStatementChoose instance){
        super.copyTo(instance);
        
        instance.set$tag(this.get$tag());
        instance.setOtherwise(this.getOtherwise());
        instance.setWhens(this.getWhens());
        instance.setXuiId(this.getXuiId());
    }

    protected XuiComponentTreeNodeStatementChoose newInstance(){
        return (XuiComponentTreeNodeStatementChoose) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
