package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppReleasing_RequirementResource;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 应用功能、模型、UI 设计的需求说明文档。应用功能需求必须指定，而模型和 UI 需求必须至少指定一项。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_RequirementResource extends io.nop.core.resource.component.AbstractComponentModel {
    
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

    public AppReleasing_RequirementResource cloneInstance(){
        AppReleasing_RequirementResource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_RequirementResource instance){
        super.copyTo(instance);
        
        instance.setModelDesign(this.getModelDesign());
        instance.setUiDesign(this.getUiDesign());
    }

    protected AppReleasing_RequirementResource newInstance(){
        return (AppReleasing_RequirementResource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
