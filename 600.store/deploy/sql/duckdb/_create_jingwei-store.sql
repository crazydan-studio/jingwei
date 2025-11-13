
CREATE TABLE o_person(
  oid VARCHAR(50) NOT NULL ,
  first_name VARCHAR(100) NOT NULL ,
  family_name VARCHAR(100) NOT NULL ,
  gender VARCHAR(100) default 'unset'   ,
  birthdate INT8  ,
  id_card_number VARCHAR(50)  ,
  myself BOOLEAN NOT NULL ,
  constraint PK_o_person primary key (oid)
);

CREATE TABLE o_person_relationship(
  oid VARCHAR(50) NOT NULL ,
  source_id VARCHAR(50) NOT NULL ,
  target_id VARCHAR(50) NOT NULL ,
  "type" VARCHAR(100) default 'unset'  NOT NULL ,
  constraint PK_o_person_relationship primary key (oid)
);


      COMMENT ON TABLE o_person IS '个体';
                
      COMMENT ON COLUMN o_person.oid IS '对象 ID';
                    
      COMMENT ON COLUMN o_person.first_name IS '名';
                    
      COMMENT ON COLUMN o_person.family_name IS '姓';
                    
      COMMENT ON COLUMN o_person.gender IS '性别';
                    
      COMMENT ON COLUMN o_person.birthdate IS '出生日期';
                    
      COMMENT ON COLUMN o_person.id_card_number IS '身份证号';
                    
      COMMENT ON COLUMN o_person.myself IS '「我」？';
                    
      COMMENT ON TABLE o_person_relationship IS '个体间的（直接）关系';
                
      COMMENT ON COLUMN o_person_relationship.oid IS '对象 ID';
                    
      COMMENT ON COLUMN o_person_relationship.source_id IS '关系源对象 ID';
                    
      COMMENT ON COLUMN o_person_relationship.target_id IS '关系目标对象 ID';
                    
      COMMENT ON COLUMN o_person_relationship."type" IS '关系类型';
                    
