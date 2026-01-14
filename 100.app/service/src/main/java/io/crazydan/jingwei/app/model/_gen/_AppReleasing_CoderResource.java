package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppReleasing_CoderResource;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 用于生成应用代码的 DSL 资源。根据需求设计资源确定模型和 UI 设计资源是否必须包含。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_CoderResource extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用打包资源
     * xml name: modelDesign
     * 
     */
    private io.crazydan.jingwei.app.model.AppPackage_Resource _modelDesign ;
    
    /**
     *  应用打包资源
     * xml name: uiDesign
     * 
     */
    private io.crazydan.jingwei.app.model.AppPackage_Resource _uiDesign ;
    
    /**
     * 应用打包资源
     * xml name: modelDesign
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppPackage_Resource getModelDesign(){
      return _modelDesign;
    }

    
    public void setModelDesign(io.crazydan.jingwei.app.model.AppPackage_Resource value){
        checkAllowChange();
        
        this._modelDesign = value;
           
    }

    
    /**
     * 应用打包资源
     * xml name: uiDesign
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppPackage_Resource getUiDesign(){
      return _uiDesign;
    }

    
    public void setUiDesign(io.crazydan.jingwei.app.model.AppPackage_Resource value){
        checkAllowChange();
        
        this._uiDesign = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._modelDesign = io.nop.api.core.util.FreezeHelper.deepFreeze(this._modelDesign);
            
           this._uiDesign = io.nop.api.core.util.FreezeHelper.deepFreeze(this._uiDesign);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("modelDesign",this.getModelDesign());
        out.putNotNull("uiDesign",this.getUiDesign());
    }

    public AppReleasing_CoderResource cloneInstance(){
        AppReleasing_CoderResource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_CoderResource instance){
        super.copyTo(instance);
        
        instance.setModelDesign(this.getModelDesign());
        instance.setUiDesign(this.getUiDesign());
    }

    protected AppReleasing_CoderResource newInstance(){
        return (AppReleasing_CoderResource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
