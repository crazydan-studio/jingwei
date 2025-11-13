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

import io.crazydan.jingwei.store.dao.entity.Person;

// tell cpd to start ignoring code - CPD-OFF
/**
 *  个体: o_person
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable","java:S3008","java:S1602","java:S1128","java:S1161",
        "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S115","java:S101","java:S3776"})
public class _Person extends DynamicOrmEntity{
    
    /* 对象 ID: oid VARCHAR */
    public static final String PROP_NAME_oid = "oid";
    public static final int PROP_ID_oid = 1;
    
    /* 名: first_name VARCHAR */
    public static final String PROP_NAME_firstName = "firstName";
    public static final int PROP_ID_firstName = 2;
    
    /* 姓: family_name VARCHAR */
    public static final String PROP_NAME_familyName = "familyName";
    public static final int PROP_ID_familyName = 3;
    
    /* 性别: gender VARCHAR */
    public static final String PROP_NAME_gender = "gender";
    public static final int PROP_ID_gender = 4;
    
    /* 出生日期: birthdate BIGINT */
    public static final String PROP_NAME_birthdate = "birthdate";
    public static final int PROP_ID_birthdate = 5;
    
    /* 身份证号: id_card_number VARCHAR */
    public static final String PROP_NAME_idCardNumber = "idCardNumber";
    public static final int PROP_ID_idCardNumber = 6;
    
    /* 「我」？: myself BOOLEAN */
    public static final String PROP_NAME_myself = "myself";
    public static final int PROP_ID_myself = 7;
    
    /* 是否已删除: deleted BOOLEAN */
    public static final String PROP_NAME_deleted = "deleted";
    public static final int PROP_ID_deleted = 8;
    

    private static int _PROP_ID_BOUND = 9;

    
    /* relation:  */
    public static final String PROP_NAME_relationships = "relationships";
    
    /* relation:  */
    public static final String PROP_NAME_inverseRelationships = "inverseRelationships";
    

    protected static final List<String> PK_PROP_NAMES = Arrays.asList(PROP_NAME_oid);
    protected static final int[] PK_PROP_IDS = new int[]{PROP_ID_oid};

    private static final String[] PROP_ID_TO_NAME = new String[9];
    private static final Map<String,Integer> PROP_NAME_TO_ID = new HashMap<>();
    static{
      
          PROP_ID_TO_NAME[PROP_ID_oid] = PROP_NAME_oid;
          PROP_NAME_TO_ID.put(PROP_NAME_oid, PROP_ID_oid);
      
          PROP_ID_TO_NAME[PROP_ID_firstName] = PROP_NAME_firstName;
          PROP_NAME_TO_ID.put(PROP_NAME_firstName, PROP_ID_firstName);
      
          PROP_ID_TO_NAME[PROP_ID_familyName] = PROP_NAME_familyName;
          PROP_NAME_TO_ID.put(PROP_NAME_familyName, PROP_ID_familyName);
      
          PROP_ID_TO_NAME[PROP_ID_gender] = PROP_NAME_gender;
          PROP_NAME_TO_ID.put(PROP_NAME_gender, PROP_ID_gender);
      
          PROP_ID_TO_NAME[PROP_ID_birthdate] = PROP_NAME_birthdate;
          PROP_NAME_TO_ID.put(PROP_NAME_birthdate, PROP_ID_birthdate);
      
          PROP_ID_TO_NAME[PROP_ID_idCardNumber] = PROP_NAME_idCardNumber;
          PROP_NAME_TO_ID.put(PROP_NAME_idCardNumber, PROP_ID_idCardNumber);
      
          PROP_ID_TO_NAME[PROP_ID_myself] = PROP_NAME_myself;
          PROP_NAME_TO_ID.put(PROP_NAME_myself, PROP_ID_myself);
      
          PROP_ID_TO_NAME[PROP_ID_deleted] = PROP_NAME_deleted;
          PROP_NAME_TO_ID.put(PROP_NAME_deleted, PROP_ID_deleted);
      
    }

    
    /* 对象 ID: oid */
    private java.lang.String _oid;
    
    /* 名: first_name */
    private java.lang.String _firstName;
    
    /* 姓: family_name */
    private java.lang.String _familyName;
    
    /* 性别: gender */
    private java.lang.String _gender;
    
    /* 出生日期: birthdate */
    private java.lang.Long _birthdate;
    
    /* 身份证号: id_card_number */
    private java.lang.String _idCardNumber;
    
    /* 「我」？: myself */
    private java.lang.Boolean _myself;
    
    /* 是否已删除: deleted */
    private java.lang.Boolean _deleted;
    

    public _Person(){
        // for debug
    }

    protected Person newInstance(){
        Person entity = new Person();
        entity.orm_attach(orm_enhancer());
        entity.orm_entityModel(orm_entityModel());
        return entity;
    }

    @Override
    public Person cloneInstance() {
        Person entity = newInstance();
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
      return "io.crazydan.jingwei.store.dao.entity.Person";
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
        
            case PROP_ID_firstName:
               return getFirstName();
        
            case PROP_ID_familyName:
               return getFamilyName();
        
            case PROP_ID_gender:
               return getGender();
        
            case PROP_ID_birthdate:
               return getBirthdate();
        
            case PROP_ID_idCardNumber:
               return getIdCardNumber();
        
            case PROP_ID_myself:
               return getMyself();
        
            case PROP_ID_deleted:
               return getDeleted();
        
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
        
            case PROP_ID_firstName:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_firstName));
               }
               setFirstName(typedValue);
               break;
            }
        
            case PROP_ID_familyName:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_familyName));
               }
               setFamilyName(typedValue);
               break;
            }
        
            case PROP_ID_gender:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_gender));
               }
               setGender(typedValue);
               break;
            }
        
            case PROP_ID_birthdate:{
               java.lang.Long typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toLong(value,
                       err-> newTypeConversionError(PROP_NAME_birthdate));
               }
               setBirthdate(typedValue);
               break;
            }
        
            case PROP_ID_idCardNumber:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_idCardNumber));
               }
               setIdCardNumber(typedValue);
               break;
            }
        
            case PROP_ID_myself:{
               java.lang.Boolean typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toBoolean(value,
                       err-> newTypeConversionError(PROP_NAME_myself));
               }
               setMyself(typedValue);
               break;
            }
        
            case PROP_ID_deleted:{
               java.lang.Boolean typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toBoolean(value,
                       err-> newTypeConversionError(PROP_NAME_deleted));
               }
               setDeleted(typedValue);
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
        
            case PROP_ID_firstName:{
               onInitProp(propId);
               this._firstName = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_familyName:{
               onInitProp(propId);
               this._familyName = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_gender:{
               onInitProp(propId);
               this._gender = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_birthdate:{
               onInitProp(propId);
               this._birthdate = (java.lang.Long)value;
               
               break;
            }
        
            case PROP_ID_idCardNumber:{
               onInitProp(propId);
               this._idCardNumber = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_myself:{
               onInitProp(propId);
               this._myself = (java.lang.Boolean)value;
               
               break;
            }
        
            case PROP_ID_deleted:{
               onInitProp(propId);
               this._deleted = (java.lang.Boolean)value;
               
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
     * 名: first_name
     */
    public final java.lang.String getFirstName(){
         onPropGet(PROP_ID_firstName);
         return _firstName;
    }

    /**
     * 名: first_name
     */
    public final void setFirstName(java.lang.String value){
        if(onPropSet(PROP_ID_firstName,value)){
            this._firstName = value;
            internalClearRefs(PROP_ID_firstName);
            
        }
    }
    
    /**
     * 姓: family_name
     */
    public final java.lang.String getFamilyName(){
         onPropGet(PROP_ID_familyName);
         return _familyName;
    }

    /**
     * 姓: family_name
     */
    public final void setFamilyName(java.lang.String value){
        if(onPropSet(PROP_ID_familyName,value)){
            this._familyName = value;
            internalClearRefs(PROP_ID_familyName);
            
        }
    }
    
    /**
     * 性别: gender
     */
    public final java.lang.String getGender(){
         onPropGet(PROP_ID_gender);
         return _gender;
    }

    /**
     * 性别: gender
     */
    public final void setGender(java.lang.String value){
        if(onPropSet(PROP_ID_gender,value)){
            this._gender = value;
            internalClearRefs(PROP_ID_gender);
            
        }
    }
    
    /**
     * 出生日期: birthdate
     */
    public final java.lang.Long getBirthdate(){
         onPropGet(PROP_ID_birthdate);
         return _birthdate;
    }

    /**
     * 出生日期: birthdate
     */
    public final void setBirthdate(java.lang.Long value){
        if(onPropSet(PROP_ID_birthdate,value)){
            this._birthdate = value;
            internalClearRefs(PROP_ID_birthdate);
            
        }
    }
    
    /**
     * 身份证号: id_card_number
     */
    public final java.lang.String getIdCardNumber(){
         onPropGet(PROP_ID_idCardNumber);
         return _idCardNumber;
    }

    /**
     * 身份证号: id_card_number
     */
    public final void setIdCardNumber(java.lang.String value){
        if(onPropSet(PROP_ID_idCardNumber,value)){
            this._idCardNumber = value;
            internalClearRefs(PROP_ID_idCardNumber);
            
        }
    }
    
    /**
     * 「我」？: myself
     */
    public final java.lang.Boolean getMyself(){
         onPropGet(PROP_ID_myself);
         return _myself;
    }

    /**
     * 「我」？: myself
     */
    public final void setMyself(java.lang.Boolean value){
        if(onPropSet(PROP_ID_myself,value)){
            this._myself = value;
            internalClearRefs(PROP_ID_myself);
            
        }
    }
    
    /**
     * 是否已删除: deleted
     */
    public final java.lang.Boolean getDeleted(){
         onPropGet(PROP_ID_deleted);
         return _deleted;
    }

    /**
     * 是否已删除: deleted
     */
    public final void setDeleted(java.lang.Boolean value){
        if(onPropSet(PROP_ID_deleted,value)){
            this._deleted = value;
            internalClearRefs(PROP_ID_deleted);
            
        }
    }
    
    private final OrmEntitySet<io.crazydan.jingwei.store.dao.entity.PersonRelationship> _relationships = new OrmEntitySet<>(this, PROP_NAME_relationships,
        io.crazydan.jingwei.store.dao.entity.PersonRelationship.PROP_NAME_source, null,io.crazydan.jingwei.store.dao.entity.PersonRelationship.class);

    /**
     * 。 refPropName: source, keyProp: {rel.keyProp}
     */
    public final IOrmEntitySet<io.crazydan.jingwei.store.dao.entity.PersonRelationship> getRelationships(){
       return _relationships;
    }
       
    private final OrmEntitySet<io.crazydan.jingwei.store.dao.entity.PersonRelationship> _inverseRelationships = new OrmEntitySet<>(this, PROP_NAME_inverseRelationships,
        io.crazydan.jingwei.store.dao.entity.PersonRelationship.PROP_NAME_target, null,io.crazydan.jingwei.store.dao.entity.PersonRelationship.class);

    /**
     * 。 refPropName: target, keyProp: {rel.keyProp}
     */
    public final IOrmEntitySet<io.crazydan.jingwei.store.dao.entity.PersonRelationship> getInverseRelationships(){
       return _inverseRelationships;
    }
       
}
// resume CPD analysis - CPD-ON
