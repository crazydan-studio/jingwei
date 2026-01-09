package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppPackage_Author;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/package.xdef <p>
 * > 作者可以是个人，也可以是组织。
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppPackage_Author extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: email
     * 
     */
    private java.lang.String _email ;
    
    /**
     *  
     * xml name: name
     * 
     */
    private java.lang.String _name ;
    
    /**
     *  
     * xml name: url
     * 
     */
    private java.lang.String _url ;
    
    /**
     * 
     * xml name: email
     *  
     */
    
    public java.lang.String getEmail(){
      return _email;
    }

    
    public void setEmail(java.lang.String value){
        checkAllowChange();
        
        this._email = value;
           
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
     * xml name: url
     *  
     */
    
    public java.lang.String getUrl(){
      return _url;
    }

    
    public void setUrl(java.lang.String value){
        checkAllowChange();
        
        this._url = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("email",this.getEmail());
        out.putNotNull("name",this.getName());
        out.putNotNull("url",this.getUrl());
    }

    public AppPackage_Author cloneInstance(){
        AppPackage_Author instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppPackage_Author instance){
        super.copyTo(instance);
        
        instance.setEmail(this.getEmail());
        instance.setName(this.getName());
        instance.setUrl(this.getUrl());
    }

    protected AppPackage_Author newInstance(){
        return (AppPackage_Author) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
