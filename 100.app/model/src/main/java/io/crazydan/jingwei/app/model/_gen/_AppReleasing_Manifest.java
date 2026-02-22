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
     *  
     * xml name: description
     * 
     */
    private java.lang.String _description ;
    
    /**
     *  
     * xml name: title
     * 
     */
    private java.lang.String _title ;
    
    /**
     *  
     * xml name: version
     * 
     */
    private java.lang.String _version ;
    
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
     * xml name: title
     *  
     */
    
    public java.lang.String getTitle(){
      return _title;
    }

    
    public void setTitle(java.lang.String value){
        checkAllowChange();
        
        this._title = value;
           
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
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("author",this.getAuthor());
        out.putNotNull("code",this.getCode());
        out.putNotNull("description",this.getDescription());
        out.putNotNull("title",this.getTitle());
        out.putNotNull("version",this.getVersion());
    }

    public AppReleasing_Manifest cloneInstance(){
        AppReleasing_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppReleasing_Manifest instance){
        super.copyTo(instance);
        
        instance.setAuthor(this.getAuthor());
        instance.setCode(this.getCode());
        instance.setDescription(this.getDescription());
        instance.setTitle(this.getTitle());
        instance.setVersion(this.getVersion());
    }

    protected AppReleasing_Manifest newInstance(){
        return (AppReleasing_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
