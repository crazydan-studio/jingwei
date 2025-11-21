package io.crazydan.jingwei.ui.schema.component._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.ui.schema.component.DynamicXuiComponent;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /_delta/default/jingwei/ui/schema/component.xdef <p>
 * > 用于在本地优先应用中根据数据动态生成组件树，类似于在 Web 前端中的 Vue 等响应式框架所做的事情。
 * >
 * > 与组件相关的数据变更将实时生成相应的组件树，在与变更前的组件树做差量比较后，可对 UI 进行差量更新。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _DynamicXuiComponent extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  组件导入指令
     * xml name: import
     * > 单独定义，以避免扩展自 `component.xdef` 的模型重复生成 `XuiComponentImport`。
     */
    private KeyedList<io.crazydan.jingwei.ui.schema.component.XuiComponentImport> _imports = KeyedList.emptyList();
    
    /**
     *  
     * xml name: messages
     * 
     */
    private KeyedList<io.crazydan.jingwei.ui.schema.component.message.XuiComponentMessage> _messages = KeyedList.emptyList();
    
    /**
     *  支持 Xpl 控制标签的组件树模板
     * xml name: template
     * > - 在模板中的 `<if/>`、`<for/>` 等标签将直接映射到相应的 Xpl 控制标签上，并在运行期执行，从而动态获取组件树；
     * > - 该节点对应的模型属性为 `IXNodeGenerator` 类型；
     */
    private io.nop.core.lang.xml.IXNodeGenerator _template ;
    
    /**
     * 组件导入指令
     * xml name: import
     *  > 单独定义，以避免扩展自 `component.xdef` 的模型重复生成 `XuiComponentImport`。
     */
    
    public java.util.List<io.crazydan.jingwei.ui.schema.component.XuiComponentImport> getImports(){
      return _imports;
    }

    
    public void setImports(java.util.List<io.crazydan.jingwei.ui.schema.component.XuiComponentImport> value){
        checkAllowChange();
        
        this._imports = KeyedList.fromList(value, io.crazydan.jingwei.ui.schema.component.XuiComponentImport::getAs);
           
    }

    
    public io.crazydan.jingwei.ui.schema.component.XuiComponentImport getImport(String name){
        return this._imports.getByKey(name);
    }

    public boolean hasImport(String name){
        return this._imports.containsKey(name);
    }

    public void addImport(io.crazydan.jingwei.ui.schema.component.XuiComponentImport item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.ui.schema.component.XuiComponentImport> list = this.getImports();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.ui.schema.component.XuiComponentImport::getAs);
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
     * 支持 Xpl 控制标签的组件树模板
     * xml name: template
     *  > - 在模板中的 `<if/>`、`<for/>` 等标签将直接映射到相应的 Xpl 控制标签上，并在运行期执行，从而动态获取组件树；
     * > - 该节点对应的模型属性为 `IXNodeGenerator` 类型；
     */
    
    public io.nop.core.lang.xml.IXNodeGenerator getTemplate(){
      return _template;
    }

    
    public void setTemplate(io.nop.core.lang.xml.IXNodeGenerator value){
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
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("imports",this.getImports());
        out.putNotNull("messages",this.getMessages());
        out.putNotNull("template",this.getTemplate());
    }

    public DynamicXuiComponent cloneInstance(){
        DynamicXuiComponent instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(DynamicXuiComponent instance){
        super.copyTo(instance);
        
        instance.setImports(this.getImports());
        instance.setMessages(this.getMessages());
        instance.setTemplate(this.getTemplate());
    }

    protected DynamicXuiComponent newInstance(){
        return (DynamicXuiComponent) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
