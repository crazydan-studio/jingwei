package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppLocalStore_App;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/local-store.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppLocalStore_App extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: code
     * 
     */
    private java.lang.String _code ;
    
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
        
        out.putNotNull("code",this.getCode());
    }

    public AppLocalStore_App cloneInstance(){
        AppLocalStore_App instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppLocalStore_App instance){
        super.copyTo(instance);
        
        instance.setCode(this.getCode());
    }

    protected AppLocalStore_App newInstance(){
        return (AppLocalStore_App) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
