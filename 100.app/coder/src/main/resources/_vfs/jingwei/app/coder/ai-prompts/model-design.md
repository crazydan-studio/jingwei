### 核心原则

- **设计范围**：聚焦于需求中描述的核心业务实体 (如 App, AppVersion)。排除通用模型 (如 User, Role, Permission 等)。
- **命名**：所有实体名均以 `Entity` 为后缀，如 UserEntity、AppEntity。
- **主键**：所有实体均以 id 作为主键，且不使用复合主键。
- **唯一键** (`orm:unique-keys`)：
  - 仅用于定义需要在数据库层面保证唯一性的、除主键之外的属性或属性组合。
  - 每个 `<key>` 的 `name` 属性必须以 `uk_` 为前缀。
  - 复合属性在 `entity-attr-csv-string` 中用英文逗号分隔。

### 属性 (`<attr>`) 定义规则

- **必填项**：`name`, `domain`, `displayName`, `mandatory` 必须填写。
- **属性ID**：业务属性的 `propId` 从 20 开始，在同模型内连续递增分配，最大不超过 2000。
- **结构展开**：不使用 JSON 等复杂类型属性，所有业务字段必须平铺展开定义。
- **长度限定**：字符串、文本等属性**必须**根据业务需要或关联字典项显式限定其最大长度。
- **敏感数据**：密码、密钥等敏感属性，需设置 `published="false"`，通常允许 `insertable="true"` 和 `updatable="true"`，但禁止 `queryable="true"` 和 `sortable="true"`。
- **数据掩码**：需脱敏显示的属性，通过 `maskPattern` 配置规则，例如 `3*4` 表示保留前 3位和后 4 位，中间用 `*` 填充。
- **日期时间**：所有日期、时间、时间戳属性，`domain` 均设为 `date` 或 `datetime`，并通过 `datePattern` 指定显示格式，默认值为 `yyyy-MM-dd HH:mm:ss`。
- **虚属性** (`virtual="true"`)：值由计算得到，无对应数据库字段。计算逻辑写在 `<computed>` 标签内，可使用 `entity` 引用当前实例的其他属性，或调用内置 XPL 标签函数
  - 对于简单的对关联对象属性的取值，优先采用**属性映射**机制
- **描述说明** (`<description>`)：用于指导用户输入，需用**简洁易懂的业务语言**描述该属性的作用、格式、示例等，**避免使用技术术语**，且**不要重复属性名**。
- **中英文排版**：在描述、显示名称等文本中，在中文与英文、数字之间**必须添加空格**，但中文标点与英文、数字之间**不能加空格**。
  > 示例：`这是一个示例 example 123。`
- **文件类型**：`domain` 为 `file` 或 `fileList` 的属性，其必须通过 `allowedFileTypes` 指定允许的 MIME 类型。
- **属性映射** (`mapTo`)：可以将属性映射到关联对象的属性上。不是虚属性。支持对其做插入、修改、查询和排序。
  > `<attr name="userName" mapTo="user.nam"/>` 表示定义的 `userName` 将被映射到 `user.name` 上

### 数据字典 (`<dict>`) 定义规则

- **何时定义**：状态 (如 status, state)、有限枚举 (选项 ≤ 20，如支付方式) 等属性应定义为字典。
- **类型**：关联字典的属性必须是 `string` 类型。
- **命名**：字典 `name` 采用 `kebab-case`，格式为 `相关模型-用途`，例如 `app-status`。
- **字典项**：每个选项 (`<option>`) 的 `value` 为 3 位数字代码 (如 `010`)，`code` 为 `MACRO_CASE` 格式的常量名。
- **布尔值**：`boolean` 类型属性不关联字典。

### **属性类型**

- `domain-name` 可选值：
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
- `file-size`: 文件大小，支持纯数字或带单位的数字，如 1024、2K、1.2G 等，不带单位的数字表示字节数

### 关联关系定义规则

- **只有**一对一、一对多两种关联类型
- **一对一 (`ref:type="one-to-one"`) **：从父实体 (源端) 关联子实体 (目标端)。
  **禁止**在父实体上显式定义外键属性 (如 `managerId`)，关联属性名应体现业务含义

```xml
<attr name="user" domain="entityRef"
      ref:target="UserEntity" ref:type="one-to-one"
/>
```

- **一对多 (`ref:type="one-to-many"`) **：从父实体 (源端) 关联子实体 (目标端)。
  在父实体上的关联属性名为集合形式 (如 `users`)，并通过 `ref:targetAttr`
  指向子实体中以 `one-to-one` 反向关联回来的属性 (如 `group`)。
  对于需要在父实体被删除时级联删除子实体的情况，需要配置 `ref:cascadeDelete="true"`

```xml
<attr name="users" domain="entityRef"
      ref:target="UserEntity" ref:targetAttr="group" ref:type="one-to-many"
      ref:cascadeDelete="true"
/>
```

- 所有关系都必须配置 `domain="entityRef"`
- `ref:target` 为关联的目标端实体 `<entity>` 名
- `ref:targetAttr` 仅在一对多关系属性上指向目标端的一对一属性
- **禁止**建立多对多关系，必须通过中间模型，将其转换为一对一和一对多关系

关联关系中的**隐性属性**：

- 在一对一关系中，存在以**关联属性名 + Id** 形式命名的隐性属性 (如 `userId`)，
  用于 UI 端保存数据时建立父实体与子实体的关联。其为 `published="true" queryable="true" sortable="true"`

### 默认属性定义规范

- 主键是每个实体必须定义的属性。
- 默认属性的 `propId` 从 1 开始递增，最大不超过 20。其排在业务属性之前。
- 对于主要实体，必须定义软删除属性，并在 `<entity>` 上设置 orm:deleteFlagProp="deleted"。
- 对于需要启用审计支持的实体，则需要定义审计属性，并在 `<entity>` 上配置 orm:createrProp, orm:createTimeProp, orm:updaterProp, orm:updateTimeProp。
- 除了主键属性名必须为 id 以外，软删除、审计属性可以自由命名，但优先采用下面示例中的名字。

定义示例：

- **主键 (`id`) 属性**：`orm:primary` 必须设置为 `true`

```xml
<attr name="id" propId="1" orm:primary="true"
      domain="uuid" displayName="ID"
      mandatory="true"
      published="true" queryable="true"
      insertable="true" updatable="false"
/>
```

- **审计属性**

```xml
<attr name="createdBy" propId="2"
      domain="userFlag" displayName="创建者"
      published="true" queryable="true" sortable="true"
      insertable="true" updatable="false"
/>
<attr name="updatedBy" propId="3"
      domain="userFlag" displayName="更新者"
      published="true" queryable="true" sortable="true"
      insertable="true" updatable="true"
/>
<attr name="createdAt" propId="4"
      domain="datetime" displayName="创建时间"
      published="true" queryable="true" sortable="true"
      insertable="true" updatable="false"
      datePattern="yyyy-MM-dd HH:mm:ss"
/>
<attr name="updatedAt" propId="5"
      domain="datetime" displayName="更新时间"
      published="true" queryable="true" sortable="true"
      insertable="true" updatable="true"
      datePattern="yyyy-MM-dd HH:mm:ss"
/>
```

- **软删除属性**

```xml
<attr name="deleted" propId="6"
      domain="deleteFlag" displayName="是否已删除"
      published="true" queryable="true" sortable="true"
      insertable="true" updatable="true"
      defaultValue="false"
/>
```

### 数据量预估 (`db:estimatedRowCount`)

- 基于 **100 个活跃用户，持续使用 1 年** 所产生的业务数据量进行估算。
- 为每个 `<entity>` 设置合理的预估行数。

### 业务接口 (`<action>`) 定义规则

- 仅定义实体增删改查以外的接口。
- 对于不涉及实体数据变更的接口需配置 `type="query"`，而影响实体数据变化的接口则配置 `type="mutation"`。
- 接口名以 `实体名__接口名` 形式命名，如 `UserEntity__changePassword`。
