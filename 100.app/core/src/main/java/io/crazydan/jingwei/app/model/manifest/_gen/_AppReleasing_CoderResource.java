package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_CoderResource;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 用于生成应用代码的 DSL 资源。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_CoderResource extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用打包资源
     * xml name: model
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_Resource _model ;
    
    /**
     *  应用打包资源
     * xml name: ui
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_Resource _ui ;
    
    /**
     * 应用打包资源
     * xml name: model
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getModel(){
      return _model;
    }

    
    public void setModel(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource value){
        checkAllowChange();
        
        this._model = value;
           
    }

    
    /**
     * 应用打包资源
     * xml name: ui
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getUi(){
      return _ui;
    }

    
    public void setUi(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource value){
        checkAllowChange();
        
        this._ui = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._model = io.nop.api.core.util.FreezeHelper.deepFreeze(this._model);
            
           this._ui = io.nop.api.core.util.FreezeHelper.deepFreeze(this._ui);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("model",this.getModel());
        out.putNotNull("ui",this.getUi());
    }

    public AppReleasing_CoderResource cloneInstance(){
        AppReleasing_CoderResource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_CoderResource instance){
        super.copyTo(instance);
        
        instance.setModel(this.getModel());
        instance.setUi(this.getUi());
    }

    protected AppReleasing_CoderResource newInstance(){
        return (AppReleasing_CoderResource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
