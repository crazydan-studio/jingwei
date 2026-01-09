package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppPostInstallation_Manifest;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/post-installation.xdef <p>
 * > 记录应用安装后的资源清单，用于校验已安装应用的完整性。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppPostInstallation_Manifest extends io.nop.core.resource.component.AbstractComponentModel {
    
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
     *  模块资源
     * xml name: moduleResources
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppPostInstallation_ModuleResource _moduleResources ;
    
    /**
     *  UI 资源
     * xml name: uiResources
     * 
     */
    private io.crazydan.jingwei.app.model.manifest.AppPostInstallation_UiResources _uiResources ;
    
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
     * 模块资源
     * xml name: moduleResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPostInstallation_ModuleResource getModuleResources(){
      return _moduleResources;
    }

    
    public void setModuleResources(io.crazydan.jingwei.app.model.manifest.AppPostInstallation_ModuleResource value){
        checkAllowChange();
        
        this._moduleResources = value;
           
    }

    
    /**
     * UI 资源
     * xml name: uiResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPostInstallation_UiResources getUiResources(){
      return _uiResources;
    }

    
    public void setUiResources(io.crazydan.jingwei.app.model.manifest.AppPostInstallation_UiResources value){
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
        
           this._moduleResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._moduleResources);
            
           this._uiResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._uiResources);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("code",this.getCode());
        out.putNotNull("installedAt",this.getInstalledAt());
        out.putNotNull("moduleResources",this.getModuleResources());
        out.putNotNull("uiResources",this.getUiResources());
        out.putNotNull("version",this.getVersion());
    }

    public AppPostInstallation_Manifest cloneInstance(){
        AppPostInstallation_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppPostInstallation_Manifest instance){
        super.copyTo(instance);
        
        instance.setCode(this.getCode());
        instance.setInstalledAt(this.getInstalledAt());
        instance.setModuleResources(this.getModuleResources());
        instance.setUiResources(this.getUiResources());
        instance.setVersion(this.getVersion());
    }

    protected AppPostInstallation_Manifest newInstance(){
        return (AppPostInstallation_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
