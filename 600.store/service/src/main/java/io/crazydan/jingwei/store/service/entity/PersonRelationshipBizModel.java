
package io.crazydan.jingwei.store.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import io.crazydan.jingwei.store.dao.entity.PersonRelationship;

@BizModel("PersonRelationship")
public class PersonRelationshipBizModel extends CrudBizModel<PersonRelationship>{
    public PersonRelationshipBizModel(){
        setEntityName(PersonRelationship.class.getName());
    }
}
