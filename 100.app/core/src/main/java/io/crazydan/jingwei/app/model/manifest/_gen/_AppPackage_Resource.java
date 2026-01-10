package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppPackage_Resource;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/resource.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppPackage_Resource extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: path
     * 
     */
    private java.lang.String _path ;
    
    /**
     * 
     * xml name: path
     *  
     */
    
    public java.lang.String getPath(){
      return _path;
    }

    
    public void setPath(java.lang.String value){
        checkAllowChange();
        
        this._path = value;
           
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
        
        out.putNotNull("path",this.getPath());
    }

    public AppPackage_Resource cloneInstance(){
        AppPackage_Resource instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppPackage_Resource instance){
        super.copyTo(instance);
        
        instance.setPath(this.getPath());
    }

    protected AppPackage_Resource newInstance(){
        return (AppPackage_Resource) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
