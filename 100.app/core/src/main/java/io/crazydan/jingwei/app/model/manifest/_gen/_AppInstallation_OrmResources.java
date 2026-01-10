package io.crazydan.jingwei.app.model.manifest._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.crazydan.jingwei.app.model.manifest.AppInstallation_OrmResources;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from /jingwei/app/schema/manifest/installation.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _AppInstallation_OrmResources extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: 
     * 
     */
    private KeyedList<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> _body = KeyedList.emptyList();
    
    /**
     * 
     * xml name: 
     *  
     */
    
    public java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> getBody(){
      return _body;
    }

    
    public void setBody(java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> value){
        checkAllowChange();
        
        this._body = KeyedList.fromList(value, io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
           
    }

    
    public io.crazydan.jingwei.app.model.manifest.AppPackage_Resource getResource(String name){
        return this._body.getByKey(name);
    }

    public boolean hasResource(String name){
        return this._body.containsKey(name);
    }

    public void addResource(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource item) {
        checkAllowChange();
        java.util.List<io.crazydan.jingwei.app.model.manifest.AppPackage_Resource> list = this.getBody();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.crazydan.jingwei.app.model.manifest.AppPackage_Resource::getPath);
            setBody(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_body(){
        return this._body.keySet();
    }

    public boolean hasBody(){
        return !this._body.isEmpty();
    }
    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._body = io.nop.api.core.util.FreezeHelper.deepFreeze(this._body);
            
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("body",this.getBody());
    }

    public AppInstallation_OrmResources cloneInstance(){
        AppInstallation_OrmResources instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(AppInstallation_OrmResources instance){
        super.copyTo(instance);
        
        instance.setBody(this.getBody());
    }

    protected AppInstallation_OrmResources newInstance(){
        return (AppInstallation_OrmResources) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
