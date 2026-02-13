## 实体 (`<entity>`) 定义

- **name**：实体名，如 `AppEntity`。
- **displayName**：实体显示名称，如 `应用`。
- **db:estimatedRowCount**：数据量预估。基于 **10 个活跃用户，持续使用 50 年** 所产生的业务数据量进行估算。
- **orm:createrProp**：表示创建者的实体属性名，如 `createdBy`。
- **orm:createTimeProp**：表示创建时间的实体属性名，如 `createdAt`。
- **orm:updaterProp**：表示更新者的实体属性名，如 `updatedBy`。
- **orm:updateTimeProp**：表示更新时间的实体属性名，如 `updatedAt`。
- **orm:deleteFlagProp**：表示软删除的实体属性名，如 `deleted`。

### 实体唯一键 (`<orm:unique-keys>`) 定义

- 仅用于定义除主键以外需要在数据库层面保证唯一性的属性或属性组合，且不同的组合定义不同的唯一键。
- 每个 `<key>` 的 `name` 属性值必须以 `uk_` 为前缀。

```xml
<orm:unique-keys>
  <key name="uk_name">name</key>
  <key name="uk_version">appCode,versionCode</key>
</orm:unique-keys>
```

### 实体属性 (`<attr>`) 定义

#### 定义规范

- **结构展开**：不使用 JSON 等复杂类型属性，所有业务字段必须平铺展开定义。
- **长度限定**：字符串、文本等类型的属性**必须**根据业务需要或关联字典项显式约束其最小、最大长度。
- **敏感数据**：密码、密钥等敏感属性，需设置 `ui:showable="false"`，通常允许 `orm/ui:insertable="true"` 和 `orm/ui:updatable="true"`，
  但禁止 `queryable="true"` 和 `sortable="true"`。
- **日期时间**：所有日期、时间、时间戳属性的 `domain` 均设置为 `date` 或 `datetime`，并通过 `ui:datePattern` 指定显示格式。
- **描述说明** (`<description>`)：用于指导用户输入，需用**简洁易懂的业务语言**描述该属性的作用、格式、示例等，**避免使用技术术语**，且**不要重复属性名**。

#### 结构说明

- **name**：属性名，如 `age`。
- **propId**：属性 ID。业务属性的 `propId` 从 20 开始，在同模型内连续递增分配，最大不超过 2000。
- **displayName**：属性显示名称，如 `年龄`。
- **mandatory**：是否为必填属性。
- **virtual**：是否为虚拟属性。此类属性的值通过计算得到，不生成对应的数据库字段。
  其计算逻辑写在 `<computed>` 标签内，可使用 `entity` 引用当前实体的其他属性，或调用相应的 **XPL 标签函数**
  - 对于简单的对关联对象属性的取值，优先采用**属性映射**机制，如 `<attr name="parentName" mapTo="parent.name"/>`
- **internal**：是否为内部属性。此类属性仅参与后台逻辑，前端不可直接新增或更新。
- **queryable**：是否可参与过滤查询。`queryable="false"` 的属性不能出现在过滤条件中。
- **sortable**：是否可参与查询结果排序。`sortable="false"` 的属性不能出现在排序条件中。
- **allowFilterOp**：可对属性应用的过滤运算符，如 `eq,gt,lt`。
- **orm:primary**：是否为主键属性。主键属性始终为 `id`。
- **orm:insertable**：属性值是否可插入到数据库中。只有 `orm:insertable="true"` 的属性的值才能在实体新增时保存到数据库中。
- **orm:updatable**：属性值是否可更新到数据库中。只有 `orm:updatable="true"` 的属性的值才能在实体更新时保存到数据库中。
- **orm:precision**：属性值在数据库中的长度限定。
- **orm:scale**：属性值在数据库中的浮点数精度。
- **domain**：属性对应的数据域，也即业务层面的属性类型，如 `file`、`date` 等。
- **mapTo**：属性映射，如 `<attr name="userName" mapTo="user.name"/>`
  表示将实体的 `userName` 属性映射到当前实体关联属性 `user` 的 `name` 属性上。
  此类属性不是虚拟属性，可以对此类属性根据业务需求做插入、修改、查询和排序。
- **dict**：属性所引用的数据字典名，如 `user-status`。引用字典的属性，其必须配置 `domain="string"`。
- **biz:codeRule**：业务编码的生成规则，如 `biz:codeRule="D{@year}{@month}{@seq:5}"`
  对应生成的是 `D20250812345` 形式的唯一编码；`biz:codeRule="{@uuid}"` 对应生成的则是 UUID 值
  - `{@year}`：年份，固定 4 位数字
  - `{@month}`：月份，固定 2 位数字
  - `{@dayOfMonth}`：月内的日期，1 到 31
  - `{@hour}`：小时，固定 2 位数字
  - `{@minute}`：分钟，固定 2 位数字
  - `{@second}`：秒，固定 2 位数字
  - `{@randNumber:N}`：随机数，生成 N 位随机数字
  - `{@seq:N}`：根据顺序号递增，取固定 N 位数字
- **defaultValue**：属性的缺省值。
- **ui:showable**：是否可以在新增、编辑实体以外的 UI 中显示该属性。
- **ui:insertable**：属性是否可在 UI 侧做新增，若 `ui:insertable="false"` 则在新增表单中不显示，并且不向后台回传该属性值。
- **ui:updatable**：属性是否可在 UI 侧做更新，若 `ui:updatable="false"` 则在编辑表单中为只读的，并且不向后台回传该属性值。
- **ui:maskPattern**：对于手机号等需脱敏显示的属性，可通过该配置控制其值的显示形式，例如
  `ui:maskPattern="3*4"` 表示保留前 3 位和后 4 位，中间用 `*` 填充。
- **ui:datePattern**：日期/时间属性值的显示形式，如 `ui:datePattern="yyyy-MM-dd HH:mm:ss"`
  对应的日期显示内容为 `2026-02-13 16:29:24`。

#### 值约束 (`<constraint>`)

- **pattern**：通过正则表达式限定文本内容。
- **minValue**：最小值。
- **maxValue**：最大值。
- **minLength**：最小长度。
- **maxLength**：最大长度。
- **minFileSize**：最小文件尺寸。
- **maxFileSize**：最大文件尺寸。
- **allowedFileTypes**：允许的文件类型。对于 `domain="file/fileList"` 的属性，必须配置该项。

#### 配置类型

- **domain-name** 可选值：
  - entityRef：建立实体关联
  - userFlag：人员标识
  - deleteFlag：软删除标识
  - uuid：UUID，不含短横线
  - file：文件类型
  - fileList：文件列表类型
  - string：字符串
  - long
  - int
  - double
  - float
  - boolean
  - date
  - datetime
  - url
  - text：纯文本
  - json
  - xml
  - html
  - markdown
- **file-size**：文件尺寸。支持纯数字或带单位的数字，如 1024、2K、1.2G 等，不带单位的数字表示字节数

### 实体关联

#### 定义规范

- 实体关联**只有**一对一（`one-to-one`）和一对多（`one-to-many`）两种类型。
- **禁止**建立多对多关系，必须通过中间模型，将其转换为一对一和一对多关系。
- 对于任意关联类型，都必须为对应属性配置 `domain="entityRef"`。
- `ref:targetAttr` 仅在一对多关系属性上指向目标端的一对一属性。
- 在一对一关系中，存在以**关联属性名 + Id** 形式命名的隐性属性 (如 `userId`)，
  用于 UI 侧在保存数据时建立父实体与子实体的关联。该属性的默认配置为 `queryable="true" sortable="true"`。

#### 一对一 (`ref:type="one-to-one"`)

从父实体 (源端) 关联子实体 (目标端)。
**禁止**在父实体上显式定义外键属性 (如 `managerId`)，关联属性名应体现业务含义：

```xml
<attr name="user" domain="entityRef"
      ref:target="UserEntity" ref:type="one-to-one"
/>
```

#### 一对多 (`ref:type="one-to-many"`)

从父实体 (源端) 关联子实体 (目标端)。
在父实体上的关联属性名为集合形式 (如 `users`)，并通过 `ref:targetAttr`
指向子实体中以 `one-to-one` 反向关联回来的属性 (如 `group`)。
对于需要在父实体被删除时级联删除子实体的情况，需要配置 `ref:cascadeDelete="true"`：

```xml
<attr name="users" domain="entityRef"
      ref:target="UserEntity" ref:targetAttr="group" ref:type="one-to-many"
      ref:cascadeDelete="true"
/>
```

### 实体固定属性定义

#### 定义规范

- 主键 `id` 是每个实体必须定义的属性，**禁止定义复合主键**。
- 实体固定属性的 `propId` 从 1 开始递增，最大不超过 20。其排在实体业务属性之前。
- 对于主要实体，必须定义软删除属性 `deleted`。
- 对于需要启用审计支持的实体，则需要定义审计属性。

#### 主键 (`id`) 属性

```xml
<attr name="id" propId="1" orm:primary="true"
      domain="uuid" displayName="ID"
      mandatory="true"
      queryable="true"
      insertable="true" updatable="false"
/>
```

注意，主键属性的 `orm:primary` 必须配置为 `true`。

#### 审计属性

```xml
<attr name="createdBy" propId="2"
      domain="userFlag" displayName="创建者"
      queryable="true" sortable="true"
      orm:insertable="true" orm:updatable="false"
      ui:insertable="false" ui:updatable="false"
/>
<attr name="updatedBy" propId="3"
      domain="userFlag" displayName="更新者"
      queryable="true" sortable="true"
      orm:insertable="true" orm:updatable="true"
      ui:insertable="false" ui:updatable="false"
/>
<attr name="createdAt" propId="4"
      domain="datetime" displayName="创建时间"
      queryable="true" sortable="true"
      orm:insertable="true" orm:updatable="false"
      ui:insertable="false" ui:updatable="false"
      datePattern="yyyy-MM-dd HH:mm:ss"
/>
<attr name="updatedAt" propId="5"
      domain="datetime" displayName="更新时间"
      queryable="true" sortable="true"
      orm:insertable="true" orm:updatable="true"
      ui:insertable="false" ui:updatable="false"
      datePattern="yyyy-MM-dd HH:mm:ss"
/>
```

#### 软删除 (`deleted`) 属性

```xml
<attr name="deleted" propId="6"
      domain="deleteFlag" displayName="是否已删除"
      queryable="true" sortable="true"
      orm:insertable="true" orm:updatable="true"
      ui:insertable="false" ui:updatable="false"
      defaultValue="false"
/>
```

## 数据字典 (`<dict>`) 定义

- 状态 (如 status, state)、有限枚举 (选项 ≤ 20，如支付方式) 等属性应定义为字典。
- 字典 `name` 格式为 `相关模型-用途`，例如 `user-status`。
- 每个选项 (`<option>`) 的 `value` 为 3 位数字代码 (如 `010`)，`code` 为具备业务含义的常量名。
- `boolean` 类型不定义为字典。

## 业务操作 (`<action>`) 定义

- 仅定义除增删改查以外的实体数据操作。
- 对于不涉及实体数据变更的接口需配置 `type="query"`，而影响实体数据变化的接口则配置 `type="mutation"`。
- `name` 以 `实体名__操作名` 形式命名，如 `UserEntity__changePassword`。
