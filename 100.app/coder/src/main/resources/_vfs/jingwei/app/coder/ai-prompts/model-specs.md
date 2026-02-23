## 模型实体定义 `<entity>`

每个模型实体对应一个 `<entity>` 节点，包含实体的基本信息和属性、唯一键等子节点。

### `<entity>` 配置

<!-- prettier-ignore -->
| 配置项 | 说明 | 是否必需 | 示例 |
|--------|------|----------|------|
| `name` | 实体名，以 `Entity` 为后缀，如 `UserEntity`。 | 是 | `name="UserEntity"` |
| `displayName` | 实体显示名称，如 `用户`。 | 是 | `displayName="用户"` |
| `db:estimatedRowCount` | 数据量预估。基于 **10 个活跃用户，持续使用 50 年** 产生的业务数据量估算。用于分库分表策略、查询优化器提示等。 | 否，但推荐 | `db:estimatedRowCount="10000"` |
| `orm:createrProp` | 表示创建者的实体属性名，通常为 `createdBy`。启用审计时必须填写。 | 条件必需 | `orm:createrProp="createdBy"` |
| `orm:createTimeProp` | 表示创建时间的实体属性名，通常为 `createdAt`。启用审计时必须填写。 | 条件必需 | `orm:createTimeProp="createdAt"` |
| `orm:updaterProp` | 表示更新者的实体属性名，通常为 `updatedBy`。启用审计时必须填写。 | 条件必需 | `orm:updaterProp="updatedBy"` |
| `orm:updateTimeProp` | 表示更新时间的实体属性名，通常为 `updatedAt`。启用审计时必须填写。 | 条件必需 | `orm:updateTimeProp="updatedAt"` |
| `orm:deleteFlagProp` | 表示逻辑删除的实体属性名，通常为 `deleted`。启用逻辑删除时必须填写。 | 条件必需 | `orm:deleteFlagProp="deleted"` |

**示例**：

```xml
<entity name="UserEntity" displayName="用户"
        db:estimatedRowCount="50000"
        orm:createrProp="createdBy" orm:createTimeProp="createdAt"
        orm:updaterProp="updatedBy" orm:updateTimeProp="updatedAt"
        orm:deleteFlagProp="deleted">
    <!-- 属性定义 -->
</entity>
```

**注意**：

- 逻辑删除仅更新 `orm:deleteFlagProp` 所指定的字段，不影响其关联子实体（子实体仍可见）。
- 审计属性、逻辑删除属性本身也需要在实体中定义，参见固定属性部分。

## 实体属性 `<attr>`

每个 `<attr>` 定义一个业务字段。定义时必须遵循以下规范。

### 通用规范

- **结构展开**：所有业务字段必须平铺，禁止使用 JSON 等复杂类型存储结构化数据。如有复杂结构应拆分为子实体并通过关联引用。
- **长度限定**：字符串、文本等类型必须显式约束最小/最大长度（通过 `<constraint>` 中的 `minLength` / `maxLength`）。
- **敏感数据**：密码、密钥等属性需设置为非公开，即 `published="false"`，同时必须禁止查询和排序（`queryable="false"`, `sortable="false"`）。通常允许插入/更新。
- **日期时间**：所有日期、时间、时间戳属性的 `domain` 应设为 `date`, `time` 或 `datetime`，并通过 `datePattern` 指定显示格式。
- **描述说明**：每个属性必须包含 `<description>`，用简洁的业务语言描述作用、格式、示例，避免技术术语，不要重复属性名。用于用户填写表单时的辅助提示信息。
- **序号分配**：`index` 在单个实体内必须唯一，业务属性从 20 开始连续递增，方便未来插入新属性时不必整体重排。

### `<attr>` 配置

<!-- prettier-ignore -->
| 配置项 | 说明 | 可选值/格式 | 是否必需 | 示例 |
|--------|------|-------------|----------|------|
| `name` | 属性名。 |  | 是 | `name="userName"` |
| `index` | 属性序号。业务属性取值范围为 20-2000；固定属性（id、审计等）取值范围为 1-19。需连续分配，不允许重复。 | 整数 | 是 | `index="20"` |
| `displayName` | 属性显示名称。 |  | 是 | `displayName="用户名"` |
| `mandatory` | 是否必填。 | `true`/`false` | 否，缺省为 `false` | `mandatory="true"` |
| `virtual` | 是否为虚拟属性（不生成数据库字段）。值通过计算得到，计算逻辑写在 `<computed>` 中。 | `true`/`false` | 否，缺省为 `false` | `virtual="true"` |
| `queryable` | 是否可参与过滤查询。若为 `false`，则任何查询条件都不能使用该属性。 | `true`/`false` | 是 | `queryable="true"` |
| `sortable` | 是否可参与排序。若为 `false`，则排序字段不能包含该属性。 | `true`/`false` | 是 | `sortable="true"` |
| `allowFilterOp` | 可用的过滤运算符，多个用逗号分隔。若 `queryable="true"` **必须**配置该属性，以明确查询所支持的操作。 | 见下方运算符列表 | 推荐必需 | `allowFilterOp="eq,like"` |
| `domain` | 数据域，即业务层面的属性类型。 | 见 domain 可选值及说明 | 是 | `domain="string"` |
| `mapTo` | 属性映射，如 `mapTo="user.name"` 表示该属性映射到关联实体 `user` 的 `name` 属性。常用于将关联实体的字段展平到当前实体上。 | 路径表达式 | 否 | `mapTo="category.name"` |
| `dict` | 引用的数据字典名，如 `auth/account-status`。此时 `domain` 必须为 `string`。 | 字典名 | 否 | `dict="auth/account-status"` |
| `codeRule` | 业务编码生成规则，如 `INV{@year}{@month}{@seq:5}`。 | 见 codeRule 占位符 | 否 | `codeRule="ORD{@year}{@seq:6}"` |
| `defaultValue` | 默认值。新建记录时若未提供该属性，则使用此默认值。 | 与属性类型匹配的字符串 | 否 | `defaultValue="active"` |
| `published` | 是否对外开放。若为 `false` 则在数据新增/编辑界面中不回显其值，且在查看界面中不显示。 | `true`/`false` | 否，缺省为 `true` | `published="false"` |
| `insertable` | 是否可在前端新增。若为 `false` 则在数据新增界面中不显示。 | `true`/`false` | 是 | `insertable="true"` |
| `updatable` | 是否可在前端被编辑。若为 `false` 则在数据编辑界面中只读。 | `true`/`false` | 是 | `updatable="true"` |
| `maskPattern` | 脱敏显示模式，如 `3*4` 表示保留前 3 位和后 4 位，中间用 `*` 填充。常用于手机号、身份证等敏感信息。 | 模式字符串，格式为 `前保留位数 * 后保留位数` | 否 | `maskPattern="3*4"` |
| `datePattern` | 日期/时间显示格式，如 `yyyy-MM-dd HH:mm:ss`。 | Java 日期格式 | 日期/时间类型需要 | `datePattern="yyyy-MM-dd"` |
| `orm:primary` | 是否为主键。只有 `id` 属性可设为 `true`。 | `true`/`false` | 仅主键需要 | `orm:primary="true"` |
| `orm:insertable` | 是否可插入数据库。 | `true`/`false` | 是 | `orm:insertable="true"` |
| `orm:updatable` | 是否可更新数据库。 | `true`/`false` | 是 | `orm:updatable="fatruelse"` |

### `domain` 可选值及说明

<!-- prettier-ignore -->
| 值 | 说明 | 约束要求 |
|----|------|----------|
| `entityRef` | 实体关联（一对一或一对多），用于指向另一个实体。 | 必须同时配置 `ref:*` |
| `userFlag` | 人员标识（通常用于创建者、更新者）。存储用户 ID。 | 无 |
| `deleteFlag` | 逻辑删除标识，值为布尔类型。 | 无 |
| `uuid` | UUID，不含短横线。 | 无 |
| `file` | 单个文件，存储文件 HASH。 | 必须配置文件相关约束 |
| `fileList` | 文件列表，存储多个文件 HASH 的 JSON 数组。 | 必须配置文件相关约束 |
| `string` | 字符串。 | 必须指定 `maxLength` |
| `long` | 长整数。 | 可指定 `minValue`/`maxValue` |
| `int` | 整数。 | 可指定 `minValue`/`maxValue` |
| `double` | 双精度浮点数。 | 可指定 `scale`、`minValue`/`maxValue` |
| `float` | 单精度浮点数。 | 可指定 `scale`、`minValue`/`maxValue` |
| `boolean` | 布尔值。 | 无 |
| `date` | 日期（年月日）。 | 需指定 `datePattern` |
| `time` | 时间（时分秒）。 | 需指定 `datePattern` |
| `datetime` | 日期时间。 | 需指定 `datePattern` |
| `url` | URL 地址。 | 必须指定 `maxLength`，可附加 `pattern` 验证格式 |
| `text` | 纯文本（大字段）。 | 必须指定 `maxLength`（字符数） |
| `svg` | SVG 内容。 | 同 `text` |
| `json` | JSON 内容。 | 同 `text` |
| `xml` | XML 内容。 | 同 `text` |
| `html` | HTML 内容。 | 同 `text` |
| `markdown` | Markdown 内容。 | 同 `text` |

### `codeRule` 占位符

业务编码生成规则支持以下占位符，系统将在新增数据时自动替换为实际值：

<!-- prettier-ignore -->
| 占位符 | 说明 | 示例 |
|--------|------|------|
| `{@uuid}` | UUID（无横线） | `a1b2c3d4e5f6...` |
| `{@year}` | 4 位年份 | `2026` |
| `{@month}` | 2 位月份（01-12） | `02` |
| `{@dayOfMonth}` | 月内日期（01-31） | `14` |
| `{@hour}` | 2 位小时（00-23） | `15` |
| `{@minute}` | 2 位分钟 | `30` |
| `{@second}` | 2 位秒 | `45` |
| `{@randNumber:N}` | N 位随机数字（0-9） | `{@randNumber:4}` → `8372` |
| `{@seq:N}` | N 位递增序列（需数据库支持，如自增序列或发号器） | `{@seq:5}` → `00012` |

规则示例：`ORD-{@year}{@month}{@seq:6}` 可能生成 `ORD-202602000123`。

### 值约束 `<constraint>` 配置

在 `<attr>` 内部可添加 `<constraint>` 子元素，定义更具体的校验规则。这些约束会在服务端和数据库端进行验证。

<!-- prettier-ignore -->
| 配置项 | 说明 | 适用类型 | 是否必需 | 示例 |
|--------|------|----------|----------|------|
| `pattern` | 正则表达式验证文本格式。 | 字符串 | 否 | `pattern="^[A-Z][a-z]+$"` |
| `scale` | 浮点数精度（小数点后位数）。 | 浮点数 | 否 | `scale="2"` |
| `minValue` | 最小值（包含）。 | 数字 | 否 | `minValue="0"` |
| `maxValue` | 最大值（包含）。 | 数字 | 否 | `maxValue="100"` |
| `minLength` | 最小长度（字符数）。 | 字符串 | 否 | `minLength="2"` |
| `maxLength` | 最大长度（字符数）。 | 字符串、文本、JSON 等 | **所有 domain 为 string/text/xml/json 等属性必须** | `maxLength="50"` |
| `minFileSize` | 最小文件尺寸（支持单位如 `1K`、`2M`）。 | 文件 | 否 | `minFileSize="1K"` |
| `maxFileSize` | 最大文件尺寸。 | 文件 | **file/fileList 类型属性必须** | `maxFileSize="10M"` |
| `allowedFileTypes` | 允许的 MIME 文件类型，逗号分隔。 | 文件 | **file/fileList 类型属性必须** | `allowedFileTypes="image/jpeg,image/png"` |

**文件尺寸单位**：支持纯数字（字节）或带单位（K、M、G，不区分大小写），如 `1024`、`2K`、`1.5M`。

### 虚拟属性与计算 `<computed>`

若 `virtual="true"`，则必须提供 `<computed>` 子元素，定义计算逻辑。计算逻辑可使用 XPL 模板语言（支持 Java 方法调用、上下文变量 `entity` 引用当前实体其他属性）。

**示例**：

```xml
<attr name="fullName" virtual="true" displayName="全名" queryable="false" sortable="false">
    <computed><![CDATA[
        return entity.firstName + ' ' + entity.lastName;
    ]]></computed>
    <description>由 firstName 和 lastName 拼接而成</description>
</attr>
```

**注意事项**：

- 虚拟属性不生成数据库列，因此不能作为查询条件，`queryable` 和 `sortable` 需设置为 `false`。
- 计算逻辑中可调用预定义的 XPL 标签库函数，例如处理日期、格式化等。

### 属性映射 `mapTo`

对于简单的关联对象属性取值，优先使用属性映射机制，而不是虚拟属性。映射属性可以像普通属性一样进行增删改查和排序，其值来自于关联实体的某个字段。

**示例**：

```xml
<attr name="categoryName" mapTo="category.name" displayName="分类名称"
      queryable="true" sortable="true" allowFilterOp="eq,like" />
```

- `mapTo` 的值是一个路径表达式，从当前实体出发，通过关联属性导航到目标属性。例如 `category` 是当前实体中指向 `CategoryEntity` 的一对一关联属性，`name` 是目标实体的属性。
- 映射属性通常是只读的（`orm:insertable="false" orm:updatable="false"`），因为修改它应直接修改源实体的字段。

### 过滤运算符 `allowFilterOp`

可选值及含义：

<!-- prettier-ignore -->
| 运算符 | 说明 |
|--------|------|
| `eq` | 等于 |
| `ne` | 不等于 |
| `gt` | 大于 |
| `ge` | 大于等于 |
| `lt` | 小于 |
| `le` | 小于等于 |
| `in` | 在集合中 |
| `notIn` | 不在集合中 |
| `startsWith` | 以 xxx 开头 |
| `endsWith` | 以 xxx 结尾 |
| `contains` | 包含 |
| `notContains` | 不包含 |
| `like` | 模糊匹配（需手动拼接 `%`） |
| `length` | 长度等于 |
| `regex` | 正则匹配 |
| `between` | 区间（数值） |
| `notBetween` | 不在区间（数值） |
| `dateBetween` | 日期区间 |
| `dateTimeBetween` | 日期时间区间 |
| `lengthBetween` | 长度区间 |

缺省值为 `eq,in,dateBetween,dateTimeBetween`。

## 实体固定属性

每个实体必须包含主键 `id`，可选包含审计属性和逻辑删除属性。固定属性的 `index` 必须使用 1~19 之间的序号，与业务属性（20~2000）区分开。

### 主键 `id`

```xml
<attr name="id" index="1" orm:primary="true"
      domain="uuid" displayName="ID"
      mandatory="true"
      queryable="true" sortable="false"
      insertable="false" updatable="false"
      orm:insertable="true" orm:updatable="false"
>
    <description>全局唯一标识，由系统自动生成</description>
</attr>
```

- **domain** 必须为 `uuid`，系统自动生成 32 位不带短横线的 UUID。
- **禁止使用复合主键**，所有实体均使用单列 UUID 主键。
- `orm:updatable="false"` 确保主键创建后不可修改。

### 审计属性（可选）

如需记录创建人、创建时间、最后修改人、最后修改时间，添加以下四个属性：

```xml
<attr name="createdBy" index="2"
      domain="userFlag" displayName="创建者"
      queryable="true" sortable="true"
      insertable="false" updatable="false"
      orm:insertable="true" orm:updatable="false"
>
    <description>记录创建该数据的用户标识</description>
</attr>
<attr name="updatedBy" index="3"
      domain="userFlag" displayName="更新者"
      queryable="true" sortable="true"
      insertable="false" updatable="false"
      orm:insertable="true" orm:updatable="true"
>
    <description>最后一次更新该数据的用户标识</description>
</attr>
<attr name="createdAt" index="4"
      domain="datetime" displayName="创建时间"
      queryable="true" sortable="true"
      insertable="false" updatable="false"
      datePattern="yyyy-MM-dd HH:mm:ss"
      orm:insertable="true" orm:updatable="false"
>
    <description>数据创建时间，自动填充</description>
</attr>
<attr name="updatedAt" index="5"
      domain="datetime" displayName="更新时间"
      queryable="true" sortable="true"
      insertable="false" updatable="false"
      datePattern="yyyy-MM-dd HH:mm:ss"
      orm:insertable="true" orm:updatable="true"
>
    <description>数据最后一次更新时间，自动更新</description>
</attr>
```

### 逻辑删除属性（可选）

启用逻辑删除时添加：

```xml
<attr name="deleted" index="6"
      domain="deleteFlag" displayName="是否已删除"
      queryable="true" sortable="true"
      insertable="false" updatable="false"
      defaultValue="false"
      orm:insertable="true" orm:updatable="true"
>
    <description>标记数据是否被逻辑删除，true 表示已删除，查询时默认过滤</description>
</attr>
```

## 唯一键 `<orm:unique-keys>`

用于定义除主键外需要在数据库层面保证唯一性的属性或属性组合。每个 `<key>` 的 `name` 属性必须以 `uk_` 为前缀，以便识别。

**规则**：

- 多个属性用逗号分隔。
- 每个组合定义一个独立的唯一键。
- 唯一键中的属性必须已存在于实体属性列表中。

**示例**：

```xml
<orm:unique-keys>
    <key name="uk_user_name">userName</key> <!-- 单属性唯一 -->
    <key name="uk_node_name">parent,name</key> <!-- 组合唯一 -->
</orm:unique-keys>
```

**注意**：唯一键仅对物理属性有效，虚拟属性、映射属性不能用于唯一键。

## 实体关联

### 通用规则

- 关联类型仅支持 **一对一（one-to-one）** 和 **一对多（one-to-many）**。
- **禁止**直接建立多对多关系，必须引入中间实体转换为一对多和一对一。
- 关联属性必须设置 `domain="entityRef"`, `virtual="false"`。
- 关联属性名应体现业务含义：一对一用单数（如 `user`），一对多用复数（如 `users`）。
- **禁止额外添加“关联属性名 + Id”的冗余字段**，如 `userId`，因为关联属性本身已包含目标实体的 ID。唯一键应直接引用关联属性（如 `user`）。

### 一对一 (`ref:type="one-to-one"`)

从源实体指向目标实体，表示“拥有一个”的关系。

**示例**：用户（UserEntity）拥有一个头像（AvatarEntity）

```xml
<attr name="avatar" domain="entityRef"
      ref:target="AvatarEntity" ref:type="one-to-one"
      description="用户头像"/>
```

**注意**：

- 禁止创建形如 `avatarId` 的额外属性，关联本身即代表外键。
- 唯一键可直接引用关联属性名：`<key name="uk_avatar">avatar</key>`。

### 一对多 (`ref:type="one-to-many"`)

从源实体（一方）关联到目标实体（多方）的集合。源实体中 **不存储外键**，而是由目标实体中的反向关联属性指向源实体。

**属性**：

- `ref:target`：目标实体名。
- `ref:targetAttr`：目标实体中指向当前实体的属性名（必须是 `one-to-one` 类型）。
- `ref:cascadeDelete`：是否在 **物理删除** 源实体的同时物理级联删除关联的目标实体，默认为 `false`。逻辑删除不受此属性影响。

**示例**：

```xml
<attr name="users" domain="entityRef" displayName="用户列表"
      ref:target="UserEntity" ref:targetAttr="group" ref:type="one-to-many"
      ref:cascadeDelete="true">
    <description>该用户组下的所有用户</description>
</attr>
```

对应的目标实体 `UserEntity` 中必须有一个一对一属性指向源实体：

```xml
<attr name="group" domain="entityRef" ref:target="GroupEntity" ref:type="one-to-one">
    <description>用户所属的组</description>
</attr>
```

**注意**：一对多关联本身不生成数据库列，仅用于对象关系映射和 API 查询。在查询时可通过 `users` 获取子实体列表。

## 数据字典 `<dict>`

用于定义状态、有限枚举（选项 ≤ 20）等。

**规则**：

- 字典 `name` 格式：`所属领域/相关模型-用途`，如 `auth/account-status`，全局唯一。
- 每个 `<option>` 的 `value` 为 3 位数字代码（如 `010`），`code` 为业务含义的常量名（大写字母+下划线），`displayName` 为界面显示文本。
- `boolean` 类型不定义为字典（直接使用布尔属性）。
- 字典项数量应 ≤ 20，超过则应考虑改为独立实体（如标签、分类）。

**示例**：

```xml
<dict name="auth/account-status" displayName="账号状态">
    <option value="010" code="ACTIVE" displayName="正常" />
    <option value="020" code="LOCKED" displayName="锁定" />
    <option value="030" code="INACTIVE" displayName="未激活" />
</dict>
```

在实体属性中引用：

```xml
<attr name="status" dict="auth/account-status" domain="string" displayName="账号状态"
      queryable="true" sortable="true" mandatory="true"
      allowFilterOp="eq,in">
    <description>用户当前状态，可选值：正常、锁定、未激活</description>
</attr>
```

**注意**：引用字典的属性，其值存储为 `value`（如 `"010"`），系统自动为该属性生成 `属性名_label` 形式的代表字典显示文本的属性，如 `status_label`。

## 业务操作 `<action>`

仅定义除标准增删改查、实体衍生、实体复制以外的实体数据操作。如果业务逻辑只是简单的属性更新，不涉及转换或外部服务，则无需定义操作，由通用 CRUD 接口处理。

**规则**：

- `name` 格式：`实体名__操作名`，如 `UserEntity__changePassword`，确保全局唯一。
- `type`：`query`（不修改数据）或 `mutation`（修改数据）。
- 操作内部可定义输入参数、校验规则、返回值类型等。

**示例**：

```xml
<action name="UserEntity__changePassword" type="mutation" displayName="修改密码">
    <arg name="oldPassword" mandatory="true" type="string" />
    <arg name="newPassword" mandatory="true" type="string" />
    <return type="boolean" />
    <description>验证旧密码并更新为新密码</description>
    <!-- 内部逻辑由平台实现，可通过 XPL 编写 -->
</action>
```
