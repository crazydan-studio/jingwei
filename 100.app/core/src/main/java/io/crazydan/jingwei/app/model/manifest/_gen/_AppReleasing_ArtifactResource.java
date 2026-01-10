package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_ArtifactResource;
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
    private KeyedList<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> _models = KeyedList.emptyList();
    
    /**
     *  应用打包资源
     * xml name: ui
     * 
     */
    private KeyedList<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> _uis = KeyedList.emptyList();
    
    /**
     * 应用打包资源
     * xml name: model
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> getModels(){
      return _models;
    }

    
    public void setModels(java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> value){
        checkAllowChange();
        
        this._models = KeyedList.fromList(value, io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getModel(String name){
        return this._models.getByKey(name);
    }

    public boolean hasModel(String name){
        return this._models.containsKey(name);
    }

    public void addModel(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> list = this.getModels();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
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
     * xml name: ui
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> getUis(){
      return _uis;
    }

    
    public void setUis(java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> value){
        checkAllowChange();
        
        this._uis = KeyedList.fromList(value, io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getUi(String name){
        return this._uis.getByKey(name);
    }

    public boolean hasUi(String name){
        return this._uis.containsKey(name);
    }

    public void addUi(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> list = this.getUis();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
            setUis(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_uis(){
        return this._uis.keySet();
    }

    public boolean hasUis(){
        return !this._uis.isEmpty();
    }
    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._models = io.nop.api.core.util.FreezeHelper.deepFreeze(this._models);
            
           this._uis = io.nop.api.core.util.FreezeHelper.deepFreeze(this._uis);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("models",this.getModels());
        out.putNotNull("uis",this.getUis());
    }

    public AppReleasing_ArtifactResource cloneInstance(){
        AppReleasing_ArtifactResource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_ArtifactResource instance){
        super.copyTo(instance);
        
        instance.setModels(this.getModels());
        instance.setUis(this.getUis());
    }

    protected AppReleasing_ArtifactResource newInstance(){
        return (AppReleasing_ArtifactResource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
