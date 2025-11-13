
package io.crazydan.jingwei.store.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import io.crazydan.jingwei.store.dao.entity.PersonRelationShip;

@BizModel("PersonRelationShip")
public class PersonRelationShipBizModel extends CrudBizModel<PersonRelationShip>{
    public PersonRelationShipBizModel(){
        setEntityName(PersonRelationShip.class.getName());
    }
}
