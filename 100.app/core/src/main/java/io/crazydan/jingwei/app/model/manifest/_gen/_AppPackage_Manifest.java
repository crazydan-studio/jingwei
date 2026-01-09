package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppPackage_Manifest;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/package.xdef <p>
 * > 记录应用安装包的资源清单，用于提供详细的应用安装包信息。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppPackage_Manifest extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用作者信息
     * xml name: author
     * > 作者可以是个人，也可以是组织。
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_Author _author ;
    
    /**
     *  
     * xml name: code
     * 
     */
    private java.lang.String _code ;
    
    /**
     *  应用编码资源
     * xml name: coderResources
     * > 用于生成应用代码的 DSL 资源。
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_CoderResources _coderResources ;
    
    /**
     *  
     * xml name: description
     * 
     */
    private java.lang.String _description ;
    
    /**
     *  应用设计需求
     * xml name: designRequirements
     * > 应用功能、模型、UI 设计的需求文档。
     */
    private io.crazydan.jingwei.app.model.manifest.AppPackage_DesignRequirements _designRequirements ;
    
    /**
     *  
     * xml name: icon
     * 
     */
    private java.lang.String _icon ;
    
    /**
     *  
     * xml name: name
     * 
     */
    private java.lang.String _name ;
    
    /**
     *  
     * xml name: version
     * 
     */
    private java.lang.String _version ;
    
    /**
     * 应用作者信息
     * xml name: author
     *  > 作者可以是个人，也可以是组织。
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Author getAuthor(){
      return _author;
    }

    
    public void setAuthor(io.crazydan.jingwei.app.model.manifest.AppPackage_Author value){
        checkAllowChange();
        
        this._author = value;
           
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
     * 应用编码资源
     * xml name: coderResources
     *  > 用于生成应用代码的 DSL 资源。
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_CoderResources getCoderResources(){
      return _coderResources;
    }

    
    public void setCoderResources(io.crazydan.jingwei.app.model.manifest.AppPackage_CoderResources value){
        checkAllowChange();
        
        this._coderResources = value;
           
    }

    
    /**
     * 
     * xml name: description
     *  
     */
    
    public java.lang.String getDescription(){
      return _description;
    }

    
    public void setDescription(java.lang.String value){
        checkAllowChange();
        
        this._description = value;
           
    }

    
    /**
     * 应用设计需求
     * xml name: designRequirements
     *  > 应用功能、模型、UI 设计的需求文档。
     */
    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_DesignRequirements getDesignRequirements(){
      return _designRequirements;
    }

    
    public void setDesignRequirements(io.crazydan.jingwei.app.model.manifest.AppPackage_DesignRequirements value){
        checkAllowChange();
        
        this._designRequirements = value;
           
    }

    
    /**
     * 
     * xml name: icon
     *  
     */
    
    public java.lang.String getIcon(){
      return _icon;
    }

    
    public void setIcon(java.lang.String value){
        checkAllowChange();
        
        this._icon = value;
           
    }

    
    /**
     * 
     * xml name: name
     *  
     */
    
    public java.lang.String getName(){
      return _name;
    }

    
    public void setName(java.lang.String value){
        checkAllowChange();
        
        this._name = value;
           
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
        
           this._author = io.nop.api.core.util.FreezeHelper.deepFreeze(this._author);
            
           this._coderResources = io.nop.api.core.util.FreezeHelper.deepFreeze(this._coderResources);
            
           this._designRequirements = io.nop.api.core.util.FreezeHelper.deepFreeze(this._designRequirements);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("author",this.getAuthor());
        out.putNotNull("code",this.getCode());
        out.putNotNull("coderResources",this.getCoderResources());
        out.putNotNull("description",this.getDescription());
        out.putNotNull("designRequirements",this.getDesignRequirements());
        out.putNotNull("icon",this.getIcon());
        out.putNotNull("name",this.getName());
        out.putNotNull("version",this.getVersion());
    }

    public AppPackage_Manifest cloneInstance(){
        AppPackage_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppPackage_Manifest instance){
        super.copyTo(instance);
        
        instance.setAuthor(this.getAuthor());
        instance.setCode(this.getCode());
        instance.setCoderResources(this.getCoderResources());
        instance.setDescription(this.getDescription());
        instance.setDesignRequirements(this.getDesignRequirements());
        instance.setIcon(this.getIcon());
        instance.setName(this.getName());
        instance.setVersion(this.getVersion());
    }

    protected AppPackage_Manifest newInstance(){
        return (AppPackage_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
