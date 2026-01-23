## **一、核心规范**

- **设计范围**：排除 User、Role、Permission、页面资源等通用模型
- **主键**：不做显式定义，由系统自动补充
- **唯一键**（orm:unique-keys）：仅需要在数据库表中保持唯一性的单一属性或复合属性，
  可配置多个唯一键 <key> 并以逗号分隔复合属性，其 name 以 uk_ 为前缀

### **1. 属性定义 <attr>**

- name、stdDomain、displayName（属性名称）、mandatory（是否必须）必填
- propId 从 20 开始，最大不超过 2000，且保持连续递增分配
- 不使用 JSON 属性，完全展开为具体的属性定义
- 对于密码、密钥等敏感数据，需设置 published 为 false，即数据不对外开放，且一般支持
  insertable（可新增）和 updatable（可更新），但不支持 queryable（可查询）和 sortable（可排序）
- 对于明确要求需做掩码处理的数据，则通过 maskPattern 配置掩码规则，如
  3*4，表示保留前三位和后四位，其余位均替换为 *。注：仅支持以 * 占位
- 日期、时间、时间戳类型属性的 stdDomain 均设置为 long，即日期的毫秒值。并同时配置 datePattern
  用于指定该类属性值的格式化模式，缺省设置为 yyyy-MM-dd HH:mm:ss
- 对于**虚属性**，其值是动态计算得到的，不对应数据库表字段，需设置 virtual 为 true，并在
  <computed> 内编写计算函数，如 return 'Hello, ' + entity.name;，其中 entity
  表示当前实例对象，即，通过对象上的其他属性值得到虚属性的值。注意，可参考【内置标签函数】小节的说明调用 xpl 标签函数
- 有关属性配置说明、约束限制以及填写注意事项等均放在 <description> 中。
  其最终将用于为用户输入提供指导，因此，需避免使用技术词汇，其内容应该简单易懂，并提供必要的示例，
  且不要再单独强调当前属性的名字
- 在描述说明、显示名称等文本内容中的中英文符号之间**必须添加空格**，但**中文标点和英文符号之间不能加空格**
- 对于文件类型的属性，其属性值的长度始终为 64 个字符，且自动根据该属性的业务意义配置 allowedFileTypes（允许的文件类型）

### **2. 数据字典 <dict>**

- 状态类属性，如 state、status 等，需要定义为字典
- 有限枚举值属性（≤20 个固定选项），比如支付方式等需要定义为字典
- 此类属性统一采用 string 类型
- 字典必须以**相关模型-用途**形式命名，如 card-status
- 字典项的 value 采用 3 位数字，比如 010
- boolean 类型不需要设置字典

### **3. 属性类型**

- std-domain 可选值：
  - uuid: UUID，不含短横线
  - file: 文件类型
  - fileList: 文件列表类型
  - string: 字符串
  - long
  - int
  - double
  - float
  - boolean
  - date
  - datetime
  - url
  - text: 纯文本
  - json
  - xml
  - html
  - markdown
- file-size: 文件大小，支持纯数字或带单位的数字，如 1024、2K、1.2G 等，不带单位的数字表示字节数

### **4. 内置标签函数**

对标签函数的调用形式如下：

```xml
<computed><![CDATA[
  let result = xpl <fn:GetBirthdayFromIdCardNumber value="${entity.idCardNumber}"/>;
  return result;
]]></computed>
```

当前可用的内置标签函数如下：

- <fn:GetBirthdayFromIdCardNumber value="id-card-number"/>: 从身份证号中获取出生日期
- <fn:CalculateAgeByIdCardNumber value="id-card-number"/>: 通过身份证号计算年龄

## **二、关联关系 <relation>**

### **1. 多对一关联**

- 从**子模型**出发关联**父模型**，即前者为关系的**源端**，后者为关系的**目标端**
- **禁止在目标端定义与源端相关的集合属性，集合属性只能在 <relation> 上通过 targetProp 指定**，
  且在子模型上的关联属性必须为标量（非集合）属性，如 userId
  ```xml
  <relation source="UserContact" sourceProp="userId"
            target="User" targetProp="contacts"
            targetPropDisplayName="联系方式"
  />
  ```

### **2. 一对一关联**

- 从**父模型**出发关联**子模型**，即前者为关系的**源端**，后者为关系的**目标端**
- **禁止在源端显式定义关联属性，如 managerId**，仅在 <relation> 上配置 sourceProp，且按业务命名，其名字不含主键信息
  ```xml
  <relation source="Department" sourceProp="manager"
            target="User"
  />
  ```

## **三、禁止属性**

系统自动管理以下属性，**禁止手动添加**：

- 审计属性：创建时间、更新时间、创建者、更新者
- 逻辑删除
- 乐观锁版本号

## **四、数据量预估**

- 设置 db:estimatedRowCount：基于 **100 用户 × 1 年** 的业务量估算。
