package io.crazydan.jingwei.store.dao.entity._gen;

import io.nop.orm.model.IEntityModel;
import io.nop.orm.support.DynamicOrmEntity;
import io.nop.orm.support.OrmEntitySet; //NOPMD - suppressed UnusedImports - Auto Gen Code
import io.nop.orm.IOrmEntitySet; //NOPMD - suppressed UnusedImports - Auto Gen Code
import io.nop.api.core.convert.ConvertHelper;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

import io.crazydan.jingwei.store.dao.entity.PersonRelationship;

// tell cpd to start ignoring code - CPD-OFF
/**
 *  个体间的（直接）关系: o_person_relationship
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable","java:S3008","java:S1602","java:S1128","java:S1161",
        "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S115","java:S101","java:S3776"})
public class _PersonRelationship extends DynamicOrmEntity{
    
    /* 对象 ID: oid VARCHAR */
    public static final String PROP_NAME_oid = "oid";
    public static final int PROP_ID_oid = 1;
    
    /* 关系源对象 ID: source_id VARCHAR */
    public static final String PROP_NAME_sourceId = "sourceId";
    public static final int PROP_ID_sourceId = 2;
    
    /* 关系目标对象 ID: target_id VARCHAR */
    public static final String PROP_NAME_targetId = "targetId";
    public static final int PROP_ID_targetId = 3;
    
    /* 关系类型: type VARCHAR */
    public static final String PROP_NAME_type = "type";
    public static final int PROP_ID_type = 4;
    

    private static int _PROP_ID_BOUND = 5;

    
    /* relation:  */
    public static final String PROP_NAME_source = "source";
    
    /* relation:  */
    public static final String PROP_NAME_target = "target";
    

    protected static final List<String> PK_PROP_NAMES = Arrays.asList(PROP_NAME_oid);
    protected static final int[] PK_PROP_IDS = new int[]{PROP_ID_oid};

    private static final String[] PROP_ID_TO_NAME = new String[5];
    private static final Map<String,Integer> PROP_NAME_TO_ID = new HashMap<>();
    static{
      
          PROP_ID_TO_NAME[PROP_ID_oid] = PROP_NAME_oid;
          PROP_NAME_TO_ID.put(PROP_NAME_oid, PROP_ID_oid);
      
          PROP_ID_TO_NAME[PROP_ID_sourceId] = PROP_NAME_sourceId;
          PROP_NAME_TO_ID.put(PROP_NAME_sourceId, PROP_ID_sourceId);
      
          PROP_ID_TO_NAME[PROP_ID_targetId] = PROP_NAME_targetId;
          PROP_NAME_TO_ID.put(PROP_NAME_targetId, PROP_ID_targetId);
      
          PROP_ID_TO_NAME[PROP_ID_type] = PROP_NAME_type;
          PROP_NAME_TO_ID.put(PROP_NAME_type, PROP_ID_type);
      
    }

    
    /* 对象 ID: oid */
    private java.lang.String _oid;
    
    /* 关系源对象 ID: source_id */
    private java.lang.String _sourceId;
    
    /* 关系目标对象 ID: target_id */
    private java.lang.String _targetId;
    
    /* 关系类型: type */
    private java.lang.String _type;
    

    public _PersonRelationship(){
        // for debug
    }

    protected PersonRelationship newInstance(){
        PersonRelationship entity = new PersonRelationship();
        entity.orm_attach(orm_enhancer());
        entity.orm_entityModel(orm_entityModel());
        return entity;
    }

    @Override
    public PersonRelationship cloneInstance() {
        PersonRelationship entity = newInstance();
        orm_forEachInitedProp((value, propId) -> {
            entity.orm_propValue(propId,value);
        });
        return entity;
    }

    @Override
    public String orm_entityName() {
      // 如果存在实体模型对象，则以模型对象上的设置为准
      IEntityModel entityModel = orm_entityModel();
      if(entityModel != null)
          return entityModel.getName();
      return "io.crazydan.jingwei.store.dao.entity.PersonRelationship";
    }

    @Override
    public int orm_propIdBound(){
      IEntityModel entityModel = orm_entityModel();
      if(entityModel != null)
          return entityModel.getPropIdBound();
      return _PROP_ID_BOUND;
    }

    @Override
    public Object orm_id() {
    
        return buildSimpleId(PROP_ID_oid);
     
    }

    @Override
    public boolean orm_isPrimary(int propId) {
        
            return propId == PROP_ID_oid;
          
    }

    @Override
    public String orm_propName(int propId) {
        if(propId >= PROP_ID_TO_NAME.length)
            return super.orm_propName(propId);
        String propName = PROP_ID_TO_NAME[propId];
        if(propName == null)
           return super.orm_propName(propId);
        return propName;
    }

    @Override
    public int orm_propId(String propName) {
        Integer propId = PROP_NAME_TO_ID.get(propName);
        if(propId == null)
            return super.orm_propId(propName);
        return propId;
    }

    @Override
    public Object orm_propValue(int propId) {
        switch(propId){
        
            case PROP_ID_oid:
               return getOid();
        
            case PROP_ID_sourceId:
               return getSourceId();
        
            case PROP_ID_targetId:
               return getTargetId();
        
            case PROP_ID_type:
               return getType();
        
           default:
              return super.orm_propValue(propId);
        }
    }

    

    @Override
    public void orm_propValue(int propId, Object value){
        switch(propId){
        
            case PROP_ID_oid:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_oid));
               }
               setOid(typedValue);
               break;
            }
        
            case PROP_ID_sourceId:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_sourceId));
               }
               setSourceId(typedValue);
               break;
            }
        
            case PROP_ID_targetId:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_targetId));
               }
               setTargetId(typedValue);
               break;
            }
        
            case PROP_ID_type:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_type));
               }
               setType(typedValue);
               break;
            }
        
           default:
              super.orm_propValue(propId,value);
        }
    }

    @Override
    public void orm_internalSet(int propId, Object value) {
        switch(propId){
        
            case PROP_ID_oid:{
               onInitProp(propId);
               this._oid = (java.lang.String)value;
               orm_id(); // 如果是设置主键字段，则触发watcher
               break;
            }
        
            case PROP_ID_sourceId:{
               onInitProp(propId);
               this._sourceId = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_targetId:{
               onInitProp(propId);
               this._targetId = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_type:{
               onInitProp(propId);
               this._type = (java.lang.String)value;
               
               break;
            }
        
           default:
              super.orm_internalSet(propId,value);
        }
    }

    
    /**
     * 对象 ID: oid
     */
    public final java.lang.String getOid(){
         onPropGet(PROP_ID_oid);
         return _oid;
    }

    /**
     * 对象 ID: oid
     */
    public final void setOid(java.lang.String value){
        if(onPropSet(PROP_ID_oid,value)){
            this._oid = value;
            internalClearRefs(PROP_ID_oid);
            orm_id();
        }
    }
    
    /**
     * 关系源对象 ID: source_id
     */
    public final java.lang.String getSourceId(){
         onPropGet(PROP_ID_sourceId);
         return _sourceId;
    }

    /**
     * 关系源对象 ID: source_id
     */
    public final void setSourceId(java.lang.String value){
        if(onPropSet(PROP_ID_sourceId,value)){
            this._sourceId = value;
            internalClearRefs(PROP_ID_sourceId);
            
        }
    }
    
    /**
     * 关系目标对象 ID: target_id
     */
    public final java.lang.String getTargetId(){
         onPropGet(PROP_ID_targetId);
         return _targetId;
    }

    /**
     * 关系目标对象 ID: target_id
     */
    public final void setTargetId(java.lang.String value){
        if(onPropSet(PROP_ID_targetId,value)){
            this._targetId = value;
            internalClearRefs(PROP_ID_targetId);
            
        }
    }
    
    /**
     * 关系类型: type
     */
    public final java.lang.String getType(){
         onPropGet(PROP_ID_type);
         return _type;
    }

    /**
     * 关系类型: type
     */
    public final void setType(java.lang.String value){
        if(onPropSet(PROP_ID_type,value)){
            this._type = value;
            internalClearRefs(PROP_ID_type);
            
        }
    }
    
    /**
     * 
     */
    public final io.crazydan.jingwei.store.dao.entity.Person getSource(){
       return (io.crazydan.jingwei.store.dao.entity.Person)internalGetRefEntity(PROP_NAME_source);
    }

    public final void setSource(io.crazydan.jingwei.store.dao.entity.Person refEntity){
   
           if(refEntity == null){
           
                   this.setSourceId(null);
               
           }else{
           internalSetRefEntity(PROP_NAME_source, refEntity,()->{
           
                           this.setSourceId(refEntity.getOid());
                       
           });
           }
       
    }
       
    /**
     * 
     */
    public final io.crazydan.jingwei.store.dao.entity.Person getTarget(){
       return (io.crazydan.jingwei.store.dao.entity.Person)internalGetRefEntity(PROP_NAME_target);
    }

    public final void setTarget(io.crazydan.jingwei.store.dao.entity.Person refEntity){
   
           if(refEntity == null){
           
                   this.setTargetId(null);
               
           }else{
           internalSetRefEntity(PROP_NAME_target, refEntity,()->{
           
                           this.setTargetId(refEntity.getOid());
                       
           });
           }
       
    }
       
}
// resume CPD analysis - CPD-ON
