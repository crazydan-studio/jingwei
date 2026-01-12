package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 将被释放到应用安装目录中的构建产物资源。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_ArtifactResource extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用打包资源
     * xml name: model
     * 
     */
    private KeyedList<io.crazydan.jingwei.app.model.AppPackage_Resource> _models = KeyedList.emptyList();
    
    /**
     *  应用打包资源
     * xml name: orm
     * 
     */
    private KeyedList<io.crazydan.jingwei.app.model.AppPackage_Resource> _orms = KeyedList.emptyList();
    
    /**
     *  应用打包资源
     * xml name: page
     * 
     */
    private KeyedList<io.crazydan.jingwei.app.model.AppPackage_Resource> _pages = KeyedList.emptyList();
    
    /**
     * 应用打包资源
     * xml name: model
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> getModels(){
      return _models;
    }

    
    public void setModels(java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> value){
        checkAllowChange();
        
        this._models = KeyedList.fromList(value, io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.AppPackage_Resource getModel(String name){
        return this._models.getByKey(name);
    }

    public boolean hasModel(String name){
        return this._models.containsKey(name);
    }

    public void addModel(io.crazydan.jingwei.app.model.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> list = this.getModels();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
            setModels(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_models(){
        return this._models.keySet();
    }

    public boolean hasModels(){
        return !this._models.isEmpty();
    }
    
    /**
     * 应用打包资源
     * xml name: orm
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> getOrms(){
      return _orms;
    }

    
    public void setOrms(java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> value){
        checkAllowChange();
        
        this._orms = KeyedList.fromList(value, io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.AppPackage_Resource getOrm(String name){
        return this._orms.getByKey(name);
    }

    public boolean hasOrm(String name){
        return this._orms.containsKey(name);
    }

    public void addOrm(io.crazydan.jingwei.app.model.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> list = this.getOrms();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
            setOrms(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_orms(){
        return this._orms.keySet();
    }

    public boolean hasOrms(){
        return !this._orms.isEmpty();
    }
    
    /**
     * 应用打包资源
     * xml name: page
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> getPages(){
      return _pages;
    }

    
    public void setPages(java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> value){
        checkAllowChange();
        
        this._pages = KeyedList.fromList(value, io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.AppPackage_Resource getPage(String name){
        return this._pages.getByKey(name);
    }

    public boolean hasPage(String name){
        return this._pages.containsKey(name);
    }

    public void addPage(io.crazydan.jingwei.app.model.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.AppPackage_Resource> list = this.getPages();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.AppPackage_Resource::getPath);
            setPages(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_pages(){
        return this._pages.keySet();
    }

    public boolean hasPages(){
        return !this._pages.isEmpty();
    }
    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._models = io.nop.api.core.util.FreezeHelper.deepFreeze(this._models);
            
           this._orms = io.nop.api.core.util.FreezeHelper.deepFreeze(this._orms);
            
           this._pages = io.nop.api.core.util.FreezeHelper.deepFreeze(this._pages);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("models",this.getModels());
        out.putNotNull("orms",this.getOrms());
        out.putNotNull("pages",this.getPages());
    }

    public AppReleasing_ArtifactResource cloneInstance(){
        AppReleasing_ArtifactResource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_ArtifactResource instance){
        super.copyTo(instance);
        
        instance.setModels(this.getModels());
        instance.setOrms(this.getOrms());
        instance.setPages(this.getPages());
    }

    protected AppReleasing_ArtifactResource newInstance(){
        return (AppReleasing_ArtifactResource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
