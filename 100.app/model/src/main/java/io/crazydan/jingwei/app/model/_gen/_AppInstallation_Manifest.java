package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppInstallation_Manifest;
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
     * xml name: bizDomain
     * 
     */
    private java.lang.String _bizDomain ;
    
    /**
     *  
     * xml name: code
     * 
     */
    private java.lang.String _code ;
    
    /**
     *  源码资源
     * xml name: coderResource
     * > 用于重新生成模型和页面代码的 DSL 资源。
     */
    private io.crazydan.jingwei.app.model.AppInstallation_CoderResource _coderResource ;
    
    /**
     *  模型资源
     * xml name: modelResources
     * 
     */
    private io.crazydan.jingwei.app.model.AppInstallation_ModelResources _modelResources ;
    
    /**
     *  ORM 资源
     * xml name: ormResources
     * 
     */
    private io.crazydan.jingwei.app.model.AppInstallation_OrmResources _ormResources ;
    
    /**
     *  页面资源
     * xml name: pageResources
     * 
     */
    private io.crazydan.jingwei.app.model.AppInstallation_PageResources _pageResources ;
    
    /**
     *  
     * xml name: version
     * 
     */
    private java.lang.String _version ;
    
    /**
     * 
     * xml name: bizDomain
     *  
     */
    
    public java.lang.String getBizDomain(){
      return _bizDomain;
    }

    
    public void setBizDomain(java.lang.String value){
        checkAllowChange();
        
        this._bizDomain = value;
           
    }

    
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
     * 源码资源
     * xml name: coderResource
     *  > 用于重新生成模型和页面代码的 DSL 资源。
     */
    
    public io.crazydan.jingwei.app.model.AppInstallation_CoderResource getCoderResource(){
      return _coderResource;
    }

    
    public void setCoderResource(io.crazydan.jingwei.app.model.AppInstallation_CoderResource value){
        checkAllowChange();
        
        this._coderResource = value;
           
    }

    
    /**
     * 模型资源
     * xml name: modelResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppInstallation_ModelResources getModelResources(){
      return _modelResources;
    }

    
    public void setModelResources(io.crazydan.jingwei.app.model.AppInstallation_ModelResources value){
        checkAllowChange();
        
        this._modelResources = value;
           
    }

    
    /**
     * ORM 资源
     * xml name: ormResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppInstallation_OrmResources getOrmResources(){
      return _ormResources;
    }

    
    public void setOrmResources(io.crazydan.jingwei.app.model.AppInstallation_OrmResources value){
        checkAllowChange();
        
        this._ormResources = value;
           
    }

    
    /**
     * 页面资源
     * xml name: pageResources
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppInstallation_PageResources getPageResources(){
      return _pageResources;
    }

    
    public void setPageResources(io.crazydan.jingwei.app.model.AppInstallation_PageResources value){
        checkAllowChange();
        
        this._pageResources = value;
           
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
        
           this._coderResource = io.nop.api.core.util.FreezeHelper.deepFreeze(this._coderResource);
            
           this._modelResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._modelResources);
            
           this._ormResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._ormResources);
            
           this._pageResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._pageResources);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("bizDomain",this.getBizDomain());
        out.putNotNull("code",this.getCode());
        out.putNotNull("coderResource",this.getCoderResource());
        out.putNotNull("modelResources",this.getModelResources());
        out.putNotNull("ormResources",this.getOrmResources());
        out.putNotNull("pageResources",this.getPageResources());
        out.putNotNull("version",this.getVersion());
    }

    public AppInstallation_Manifest cloneInstance(){
        AppInstallation_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppInstallation_Manifest instance){
        super.copyTo(instance);
        
        instance.setBizDomain(this.getBizDomain());
        instance.setCode(this.getCode());
        instance.setCoderResource(this.getCoderResource());
        instance.setModelResources(this.getModelResources());
        instance.setOrmResources(this.getOrmResources());
        instance.setPageResources(this.getPageResources());
        instance.setVersion(this.getVersion());
    }

    protected AppInstallation_Manifest newInstance(){
        return (AppInstallation_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
