【任务目标】

你作为兼具软件技术与业务领域专业知识的资深软件架构师，需要依据给定需求描述完成业务模型的结构设计。
只用返回 XML，不需要额外解释。

【返回格式】

```xml
<model-design>
    <dicts>
        <dict name="kebab-case" displayName="string">
            <description>string</description>
            <options>
                <option value="digits" code="macro-case" displayName="string">
                    <description>string</description>
                </option>
            </options>
        </dict>
    </dicts>
    <entities>
        <entity name="pascal-case" displayName="string">
            <description>string</description>
            <orm:unique-keys>
                <key name="snake-case">entity-attr-csv-string</key>
            </orm:unique-keys>
            <attrs>
                <attr name="camel-case" displayName="string" mandatory="boolean" virtual="boolean" published="boolean"
                      insertable="boolean" updatable="boolean" queryable="boolean" sortable="boolean"
                      maskPattern="string" datePattern="string" db:estimatedRowCount="int" dict="dict-name"
                      stdDomain="std-domain" precision="int" scale="int" defaultValue="any" minValue="double"
                      maxValue="double" minLength="int" maxLength="int" minFileSize="file-size" maxFileSize="file-size"
                      allowedFileTypes="mime-type-csv-string">
                    <description>string</description>
                    <computed>xpl-fn</computed>
                </attr>
            </attrs>
        </entity>
    </entities>
    <relations>
        <relation name="snake-case" source="child-entity-name" sourceProp="child-entity-attr"
                  target="parent-entity-name" targetProp="parent-to-children-prop" targetPropDisplayName="string"/>
    </relations>
</model-design>
```

【设计规范】

## **一、核心规范**

1. **设计范围**：排除 User、Role、Permission、页面资源等通用模型
2. **主键**：不做显式定义，由系统自动补充
3. **唯一键**（orm:unique-keys）：仅需要在数据库表中保持唯一性的单一属性或复合属性，
  可配置多个唯一键 <key> 并以逗号分隔复合属性，其 name 以 uk_ 为前缀

4. **属性定义 <attr>**

- name、stdDomain、displayName（属性名称）、mandatory（是否必须）必填
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

5. **数据字典 <dict>**

- 状态类属性，如 state、status 等，需要定义为字典
- 有限枚举值属性（≤20 个固定选项），比如支付方式等需要定义为字典
- 此类属性统一采用 string 类型
- 字典必须以**业务模型-用途**形式命名，如 app-version-status，
  若字典为通用的，则其名字以**业务域-用途**形式命名，如 system-data-type
- 字典项的 value 采用 3 位数字，比如 010
- boolean 类型不需要设置字典

6. **属性类型**

- std-domain 可选值：
  - file: 文件类型
  - fileList: 文件列表类型
  - string: 字符串
  - long
  - int
  - double
  - float
  - boolean
  - url
  - text: 纯文本
  - json
  - xml
  - html
  - markdown
  - uuidv7
- file-size: 文件大小，支持纯数字或带单位的数字，如 1024、2K、1.2G 等，不带单位的数字表示字节数

7. **内置标签函数**

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

1. **多对一关联**

- 从**子模型**出发关联**父模型**，即前者为关系的**源端**，后者为关系的**目标端**，
  比如，用户与联系方式的关联是从联系方式出发与用户绑定关系，
  即，在联系方式对象的某个属性（如 userId）上记录用户对象的主键：
  ```xml
  <relation source="UserContact" sourceProp="userId"
            target="User" targetProp="contacts"
            targetPropDisplayName="联系方式"
  />
  ```

- targetProp、targetPropDisplayName 仅在多对一的关系中，用于配置在父模型中反向关联子模型
- 设置 targetProp **表示关联子模型的数据规模较小**允许（并建议）直接通过主模型主键查询关联子模型的数据，并**一次性全量加载到内存**中进行操作
- **禁止**在 <attr> 元素内定义集合属性，集合属性只能在 <relation> 上通过 targetProp 指定，且在子模型上的关联属性必须为标量（非集合）属性
- 一般主模型并不会反向指向关联它的子模型，比如 ProductCategory 并不会有一个集合属性 orderDetails 指向父模型
  OrderDetail。在业务层面上没有这种需求，在技术层面这种集合也过大，不适合直接在内存中操作

2. **一对一关联**

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


【完整性检查】

- 返回的 XML 包含所有业务模型定义，没有缺失
- 移除值为空的 XML 标签属性
- 移除 XML 标签属性值中的首尾空白

【需求描述 - 系统设计】

# 个人数字资产应用开发系统的设计

该系统主要用于开发和运行**轻量级**的**本地优先**应用。
适用于管理个人及家庭的数字资产，用户可根据自身需求自行创建不同的本地应用。

## 功能模块

### 1. 应用管理

1. 应用新增

- 用户点击新增按钮后弹出应用新增窗口，在该窗口中填写应用信息并保存后，
  系统将为其自动创建一个初始版本，并切换到该版本的开发页面中，进行版本开发

2. 版本激活

- 只有已发布版本才能被激活。激活不是版本的状态，而是应用对某个已发布版本的唯一引用
- 对于应用的第一个发布版本，系统将自动将其作为应用的已激活版本，无需手动激活
- 每个应用只能有唯一的激活版本。若其没有激活版本，则应用始终处于开发中的状态，
  也只有在这种情况下，应用才会处于开发中的状态
- 除了首次激活前允许应用存在无激活版本以外，后续只能在发布版本中切换选择激活版本，
  而不能取消版本激活
- 所激活的版本的主页面将作为应用的入口 UI，并以该版本的业务模型进行数据管理

3. 应用启用

- 在首次激活应用版本时，应用将被自动切换到已启用状态
- 只有已启用的应用才能被用户使用，用户才能操作和管理该应用的数据

4. 应用禁用

- 只有已启用的应用才能被禁用
- 被禁用的应用将不能使用，用户只能编辑应用基本信息，而不能在查看、操作和管理该应用的数据

5. 应用编辑

- 应用在任何状态下均可修改其基本信息

6. 应用删除

- 只有已禁用或开发中的应用才可以被删除，且删除为物理删除

### 2. 应用版本管理

1. 版本开发

- 用户在版本开发页面中可以在表单中描述应用的功能设计、模型设计和 UI 设计等需求，
  再提交给 AI 大模型，由其生成业务模型结构以及配套的 UI 代码
- 在开发页面中可以对 AI 生成的业务模型结构进行查看和编辑，也可对 UI 代码进行编辑和效果预览
- 如果用户对 AI 生成的结果不满意，则可以对其需求描述进行修改或细化调整，再由 AI 重新生成
- 若当前版本已开发完成，则需要对其进行发布

2. 版本发布

- 在发布版本时，需要填写版本说明，阐明版本所实现的功能、特色，或者对衍生版本所做的变更情况等
- 只有已发布的版本才可以作为应用的已激活版本
- 已发布版本不可被修改、不可被删除

3. 版本修改

- 仅开发中的版本才能被修改，若要对已发布版本做修改，则需要做版本衍生
- 版本修改也就是在版本发布前的开发操作，其逻辑与要求与版本开发一致

4. 版本新建/衍生

- 可以为应用全新重建一个版本，也可以对其某个已发布版本做衍生，
  也即，基于某个发布版本进行变更性调整
- 无论新建还是衍生，其开发过程均与版本开发一致

5. 版本删除

- 只有开发中的版本才允许被删除，且删除为物理删除
- 若应用只有唯一的一个版本，则不管该版本是何状态都不能被删除


【需求描述 - 模型设计】

App（应用）的结构为（**不要扩展其结构**）：
- 唯一标识（code）：必须、UUIDv7、非主键，但全表唯一、新增时由系统自动生成，可新增但不可更新
- 名字：必须、长度 200、不唯一，可重复
- 图标：file 类型、长度 64、不允许 image/gif 类型图片
- 状态：必须、可选值为 开发中/已启用/已禁用。初始状态为 开发中
- 说明：文本，Markdown 格式

AppVersion（应用版本信息）的结构为（**不要扩展其结构**）：
- 所属应用的主键（appId）：必须
- 版本号（name）：非必须、x.y.z 三段形式，每段最多 3 个数字
- 开发代码（devCode）：必须、新增时由系统自动生成，可新增但不可更新、与 appId 组成复合唯一键
- 状态：必须、可选值为 开发中/已发布。初始状态为 开发中
- 说明：描述版本所实现的功能或者对衍生版本所做的变更情况等
- 应用功能设计需求（appRequirements）：text 类型、描述应用整体的功能设计
- 业务模型设计需求（modelRequirements）：text 类型、描述应用的业务模型结构和约束
- UI 设计需求（uiRequirements）：text 类型、描述应用的 UI 设计风格和要求
- 业务模型定义（modelDefs）：xml 类型
- UI 代码（uiDefs）：html 类型
- 使用的 AI 模型供应商：LLM 供应商标识
- 使用的 AI 模型：LLM 模型标识

模型关联说明：
- App 与 AppVersion 通过属性 activeVersion（已激活版本）建立一对一关联
- AppVersion 与 AppVersion 通过属性 derivedFrom（衍生自）建立一对一的版本衍生关系

