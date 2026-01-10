package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppReleasing_DesignRequirement;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 应用功能、模型、UI 设计的需求文档。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_DesignRequirement extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用打包资源
     * xml name: app
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_Resource _app ;
    
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
     * xml name: app
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getApp(){
      return _app;
    }

    
    public void setApp(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource value){
        checkAllowChange();
        
        this._app = value;
           
    }

    
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
        
           this._app = io.nop.api.core.util.FreezeHelper.deepFreeze(this._app);
            
           this._model = io.nop.api.core.util.FreezeHelper.deepFreeze(this._model);
            
           this._ui = io.nop.api.core.util.FreezeHelper.deepFreeze(this._ui);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("app",this.getApp());
        out.putNotNull("model",this.getModel());
        out.putNotNull("ui",this.getUi());
    }

    public AppReleasing_DesignRequirement cloneInstance(){
        AppReleasing_DesignRequirement instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_DesignRequirement instance){
        super.copyTo(instance);
        
        instance.setApp(this.getApp());
        instance.setModel(this.getModel());
        instance.setUi(this.getUi());
    }

    protected AppReleasing_DesignRequirement newInstance(){
        return (AppReleasing_DesignRequirement) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
