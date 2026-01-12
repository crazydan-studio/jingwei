package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppReleasing_Manifest;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/releasing.xdef <p>
 * > 记录应用发布包中的资源清单。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppReleasing_Manifest extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  应用的构建产物
     * xml name: artifactResource
     * > 将被释放到应用安装目录中的构建产物资源。
     */
    private io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource _artifactResource ;
    
    /**
     *  应用的作者信息
     * xml name: author
     * > 作者可以是个人，也可以是组织。
     */
    private io.crazydan.jingwei.app.model.AppReleasing_Author _author ;
    
    /**
     *  
     * xml name: code
     * 
     */
    private java.lang.String _code ;
    
    /**
     *  应用的编码资源
     * xml name: coderResource
     * > 用于生成应用代码的 DSL 资源。
     */
    private io.crazydan.jingwei.app.model.AppReleasing_CoderResource _coderResource ;
    
    /**
     *  
     * xml name: description
     * 
     */
    private java.lang.String _description ;
    
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
     *  应用的需求设计资源
     * xml name: requirementResource
     * > 应用功能、模型、UI 设计的需求说明文档。
     */
    private io.crazydan.jingwei.app.model.AppReleasing_RequirementResource _requirementResource ;
    
    /**
     *  
     * xml name: version
     * 
     */
    private java.lang.String _version ;
    
    /**
     * 应用的构建产物
     * xml name: artifactResource
     *  > 将被释放到应用安装目录中的构建产物资源。
     */
    
    public io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource getArtifactResource(){
      return _artifactResource;
    }

    
    public void setArtifactResource(io.crazydan.jingwei.app.model.AppReleasing_ArtifactResource value){
        checkAllowChange();
        
        this._artifactResource = value;
           
    }

    
    /**
     * 应用的作者信息
     * xml name: author
     *  > 作者可以是个人，也可以是组织。
     */
    
    public io.crazydan.jingwei.app.model.AppReleasing_Author getAuthor(){
      return _author;
    }

    
    public void setAuthor(io.crazydan.jingwei.app.model.AppReleasing_Author value){
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
     * 应用的编码资源
     * xml name: coderResource
     *  > 用于生成应用代码的 DSL 资源。
     */
    
    public io.crazydan.jingwei.app.model.AppReleasing_CoderResource getCoderResource(){
      return _coderResource;
    }

    
    public void setCoderResource(io.crazydan.jingwei.app.model.AppReleasing_CoderResource value){
        checkAllowChange();
        
        this._coderResource = value;
           
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
     * 应用的需求设计资源
     * xml name: requirementResource
     *  > 应用功能、模型、UI 设计的需求说明文档。
     */
    
    public io.crazydan.jingwei.app.model.AppReleasing_RequirementResource getRequirementResource(){
      return _requirementResource;
    }

    
    public void setRequirementResource(io.crazydan.jingwei.app.model.AppReleasing_RequirementResource value){
        checkAllowChange();
        
        this._requirementResource = value;
           
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
        
           this._artifactResource = io.nop.api.core.util.FreezeHelper.deepFreeze(this._artifactResource);
            
           this._author = io.nop.api.core.util.FreezeHelper.deepFreeze(this._author);
            
           this._coderResource = io.nop.api.core.util.FreezeHelper.deepFreeze(this._coderResource);
            
           this._requirementResource = io.nop.api.core.util.FreezeHelper.deepFreeze(this._requirementResource);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("artifactResource",this.getArtifactResource());
        out.putNotNull("author",this.getAuthor());
        out.putNotNull("code",this.getCode());
        out.putNotNull("coderResource",this.getCoderResource());
        out.putNotNull("description",this.getDescription());
        out.putNotNull("icon",this.getIcon());
        out.putNotNull("name",this.getName());
        out.putNotNull("requirementResource",this.getRequirementResource());
        out.putNotNull("version",this.getVersion());
    }

    public AppReleasing_Manifest cloneInstance(){
        AppReleasing_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_Manifest instance){
        super.copyTo(instance);
        
        instance.setArtifactResource(this.getArtifactResource());
        instance.setAuthor(this.getAuthor());
        instance.setCode(this.getCode());
        instance.setCoderResource(this.getCoderResource());
        instance.setDescription(this.getDescription());
        instance.setIcon(this.getIcon());
        instance.setName(this.getName());
        instance.setRequirementResource(this.getRequirementResource());
        instance.setVersion(this.getVersion());
    }

    protected AppReleasing_Manifest newInstance(){
        return (AppReleasing_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
