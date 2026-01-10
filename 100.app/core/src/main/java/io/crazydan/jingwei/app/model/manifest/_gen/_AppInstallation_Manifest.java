package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_Manifest;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/installation.xdef <p>
 * > 记录应用安装包中的资源清单。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppInstallation_Manifest extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: code
     * 
     */
    private java.lang.String _code ;
    
    /**
     *  
     * xml name: installedAt
     * 
     */
    private java.time.LocalDateTime _installedAt ;
    
    /**
     *  模型资源
     * xml name: modelResources
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppInstallation_ModelResources _modelResources ;
    
    /**
     *  UI 资源
     * xml name: uiResources
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppInstallation_UiResources _uiResources ;
    
    /**
     *  
     * xml name: version
     * 
     */
    private java.lang.String _version ;
    
    /**
     * 
     * xml name: code
     *  
     */
    
    public java.lang.String getCode(){
      return _code;
    }

    
    public void setCode(java.lang.String value){
        checkAllowChange();
        
        this._code = value;
           
    }

    
    /**
     * 
     * xml name: installedAt
     *  
     */
    
    public java.time.LocalDateTime getInstalledAt(){
      return _installedAt;
    }

    
    public void setInstalledAt(java.time.LocalDateTime value){
        checkAllowChange();
        
        this._installedAt = value;
           
    }

    
    /**
     * 模型资源
     * xml name: modelResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppInstallation_ModelResources getModelResources(){
      return _modelResources;
    }

    
    public void setModelResources(io.crazydan.jingwei.app.model.manifest.AppInstallation_ModelResources value){
        checkAllowChange();
        
        this._modelResources = value;
           
    }

    
    /**
     * UI 资源
     * xml name: uiResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppInstallation_UiResources getUiResources(){
      return _uiResources;
    }

    
    public void setUiResources(io.crazydan.jingwei.app.model.manifest.AppInstallation_UiResources value){
        checkAllowChange();
        
        this._uiResources = value;
           
    }

    
    /**
     * 
     * xml name: version
     *  
     */
    
    public java.lang.String getVersion(){
      return _version;
    }

    
    public void setVersion(java.lang.String value){
        checkAllowChange();
        
        this._version = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._modelResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._modelResources);
            
           this._uiResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._uiResources);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("code",this.getCode());
        out.putNotNull("installedAt",this.getInstalledAt());
        out.putNotNull("modelResources",this.getModelResources());
        out.putNotNull("uiResources",this.getUiResources());
        out.putNotNull("version",this.getVersion());
    }

    public AppInstallation_Manifest cloneInstance(){
        AppInstallation_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppInstallation_Manifest instance){
        super.copyTo(instance);
        
        instance.setCode(this.getCode());
        instance.setInstalledAt(this.getInstalledAt());
        instance.setModelResources(this.getModelResources());
        instance.setUiResources(this.getUiResources());
        instance.setVersion(this.getVersion());
    }

    protected AppInstallation_Manifest newInstance(){
        return (AppInstallation_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
