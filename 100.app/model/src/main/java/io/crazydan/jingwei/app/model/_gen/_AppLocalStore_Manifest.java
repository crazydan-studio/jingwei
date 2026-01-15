package io.crazydan.jingwei.app.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.AppLocalStore_Manifest;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/local-store.xdef <p>
 * > 记录本地应用仓库的应用列表及其状态。
 * >
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppLocalStore_Manifest extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: enabledApps
     * 
     */
    private io.crazydan.jingwei.app.model.AppLocalStore_EnabledApps _enabledApps ;
    
    /**
     * 
     * xml name: enabledApps
     *  
     */
    
    public io.crazydan.jingwei.app.model.AppLocalStore_EnabledApps getEnabledApps(){
      return _enabledApps;
    }

    
    public void setEnabledApps(io.crazydan.jingwei.app.model.AppLocalStore_EnabledApps value){
        checkAllowChange();
        
        this._enabledApps = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._enabledApps = io.nop.api.core.util.FreezeHelper.deepFreeze(this._enabledApps);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("enabledApps",this.getEnabledApps());
    }

    public AppLocalStore_Manifest cloneInstance(){
        AppLocalStore_Manifest instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppLocalStore_Manifest instance){
        super.copyTo(instance);
        
        instance.setEnabledApps(this.getEnabledApps());
    }

    protected AppLocalStore_Manifest newInstance(){
        return (AppLocalStore_Manifest) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
