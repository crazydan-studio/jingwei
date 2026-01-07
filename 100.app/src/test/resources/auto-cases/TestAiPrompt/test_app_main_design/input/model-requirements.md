App（应用）的结构为（**不要扩展其结构**）：
- 唯一标识（code）：必须、UUID、非主键，但全表唯一、新增时由系统自动生成，可新增但不可更新
- 名字：必须、长度 200、不唯一，可重复
- 图标：file 类型、长度 64、不允许 image/gif 类型图片
- 状态（status）：必须、可选值为 开发中/已启用/已禁用。初始状态为 开发中
- 说明：文本，Markdown 格式
- 数据库表前缀（dbTablePrefix）：必须、新增时由系统自动生成，可新增但不可更新

AppVersion（应用版本信息）的结构为（**不要扩展其结构**）：
- 所属应用的主键（appId）：必须
- 版本号（name）：非必须、x.y.z 三段形式，每段最多 3 个数字
- 开发代码（devCode）：必须、新增时由系统自动生成，可新增但不可更新、与 appId 组成复合唯一键
- 状态（status）：必须、可选值为 开发中/已发布。初始状态为 开发中
- 说明：描述版本所实现的功能或者对衍生版本所做的变更情况等
- 应用功能设计需求（appRequirements）：text 类型、描述应用整体的功能设计
- 业务模型设计需求（modelRequirements）：text 类型、描述应用的业务模型结构和约束
- UI 设计需求（uiRequirements）：text 类型、描述应用的 UI 设计风格和要求
- 业务模型定义（modelDefs）：xml 类型
- UI 代码（uiDefs）：xml 类型，包含多个页面的代码
- 使用的 AI 模型提供商：LLM 提供商标识
- 使用的 AI 模型：LLM 模型标识

模型关联说明：
- AppVersion 通过属性 appId 与 App 建立多对一关联
- App 与 AppVersion 通过属性 activeVersion（已激活版本）建立一对一关联
- AppVersion 与 AppVersion 通过属性 derivedFrom（衍生自）建立一对一的版本衍生关系
