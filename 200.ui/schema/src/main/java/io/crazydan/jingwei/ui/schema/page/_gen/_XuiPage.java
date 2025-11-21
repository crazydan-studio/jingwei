package io.crazydan.jingwei.ui.schema.page._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.page.XuiPage;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/ui/schema/page.xdef <p>
 * > 一个包含完整业务处理组件的应用窗口。
 * > 其本质就是一个占满整个视窗的 UI 组件。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _XuiPage extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: import
     * 
     */
    private KeyedList<io.crazydan.jingwei.ui.schema.page.XuiComponentImport> _imports = KeyedList.emptyList();
    
    /**
     *  
     * xml name: messages
     * 
     */
    private KeyedList<io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage> _messages = KeyedList.emptyList();
    
    /**
     *  组件树
     * xml name: template
     * >
     */
    private io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeRoot _template ;
    
    /**
     * 
     * xml name: import
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.ui.schema.page.XuiComponentImport> getImports(){
      return _imports;
    }

    
    public void setImports(java.util.List<io.crazydan.jingwei.ui.schema.page.XuiComponentImport> value){
        checkAllowChange();
        
        this._imports = KeyedList.fromList(value, io.crazydan.jingwei.ui.schema.page.XuiComponentImport::getAs);
           
    }

    
    public io.crazydan.jingwei.ui.schema.page.XuiComponentImport getImport(String name){
        return this._imports.getByKey(name);
    }

    public boolean hasImport(String name){
        return this._imports.containsKey(name);
    }

    public void addImport(io.crazydan.jingwei.ui.schema.page.XuiComponentImport item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.ui.schema.page.XuiComponentImport> list = this.getImports();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.ui.schema.page.XuiComponentImport::getAs);
            setImports(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_imports(){
        return this._imports.keySet();
    }

    public boolean hasImports(){
        return !this._imports.isEmpty();
    }
    
    /**
     * 
     * xml name: messages
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage> getMessages(){
      return _messages;
    }

    
    public void setMessages(java.util.List<io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage> value){
        checkAllowChange();
        
        this._messages = KeyedList.fromList(value, io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage::getName);
           
    }

    
    public io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage getMessage(String name){
        return this._messages.getByKey(name);
    }

    public boolean hasMessage(String name){
        return this._messages.containsKey(name);
    }

    public void addMessage(io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage> list = this.getMessages();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage::getName);
            setMessages(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_messages(){
        return this._messages.keySet();
    }

    public boolean hasMessages(){
        return !this._messages.isEmpty();
    }
    
    /**
     * 组件树
     * xml name: template
     *  >
     */
    
    public io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeRoot getTemplate(){
      return _template;
    }

    
    public void setTemplate(io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeRoot value){
        checkAllowChange();
        
        this._template = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._imports = io.nop.api.core.util.FreezeHelper.deepFreeze(this._imports);
            
           this._messages = io.nop.api.core.util.FreezeHelper.deepFreeze(this._messages);
            
           this._template = io.nop.api.core.util.FreezeHelper.deepFreeze(this._template);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("imports",this.getImports());
        out.putNotNull("messages",this.getMessages());
        out.putNotNull("template",this.getTemplate());
    }

    public XuiPage cloneInstance(){
        XuiPage instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(XuiPage instance){
        super.copyTo(instance);
        
        instance.setImports(this.getImports());
        instance.setMessages(this.getMessages());
        instance.setTemplate(this.getTemplate());
    }

    protected XuiPage newInstance(){
        return (XuiPage) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
