
package io.crazydan.jingwei.store.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import io.crazydan.jingwei.store.dao.entity.Person;

@BizModel("Person")
public class PersonBizModel extends CrudBizModel<Person>{
    public PersonBizModel(){
        setEntityName(Person.class.getName());
    }
}
