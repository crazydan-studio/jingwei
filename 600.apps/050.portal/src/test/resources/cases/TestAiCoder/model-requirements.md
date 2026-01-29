实现一个围绕个人数字资产进行应用开发和管理的平台。

### 核心业务概念

- **应用 (AppEntity)**：用户创建的一个可运行的轻量级本地应用，用于管理特定类型的个人数字资产。
- **应用版本 (AppVersionEntity)**：一个应用在不同时间点或不同功能集的快照。版本经过开发、发布后，可被激活作为应用的运行版本。

### 业务状态与流程规则

- **应用状态流转**：`开发中` -> (`版本发布并激活`) -> `已启用` -> (`手动禁用`) -> `已禁用`。
- **版本状态流转**：`开发中` -> (`发布`) -> `已发布` (终态)。
- **激活规则**：只有`已发布`版本可被激活。应用首次发布版本时自动激活。
- **启用/禁用**：只有`已启用`的应用可供用户操作数据。`已禁用`应用仅能编辑基本信息。
- **版本衍生**：新建版本可以衍生自某个已发布版本，从而支持版本改进。

### 详细业务规则与属性定义

#### 实体：AppEntity (应用)

- `code` (**唯一标识**)：
  - **必填**。类型为 UUID（不含短横线），`domain="uuid"`。
  - **全表唯一**，但不是主键。
  - **系统行为**：新增时由系统自动生成。`insertable="true"`, `updatable="false"`。
- `name` (**名字**)：
  - **必填**。`domain="string"`, `maxLength="200"`。
  - 不要求唯一，可重复。
- `bizDomain` (**所属业务域**)：
  - **必填**。`domain="string"`。限定最大长度 20。作为应用内部业务模型的命名空间标识，如 `user`、`org` 等。
- `icon` (**图标**)：
  - `domain="file"`。
  - **文件限制**：`allowedFileTypes` 为 `image/jpeg,image/png,image/svg+xml,image/webp`。
- `status` (**状态**)：
  - **必填**。`domain="string"`, `dict="app-status"`。
  - **可选值**：`开发中`, `已启用`, `已禁用`。
  - **初始状态**：`开发中`。
- `description` (**说明**)：
  - `domain="markdown"`。用于详细描述应用用途。

#### 实体：AppVersionEntity (应用版本)

- `name` (**版本号**)：
  - **必填**。`domain="string"`。限定最大长度 12。
  - **格式**：`x.y.z`，每段最多 3 位数字。需在 `description` 中说明格式。
  - **唯一性**：与 `app` 组成**复合唯一键** (`uk_app_version`)。
- `status` (**状态**)：
  - **必填**。`domain="string"`, `dict="app-version-status"`。限定长度 3。
  - **可选值**：`开发中`, `已发布`。
  - **初始状态**：`开发中`。
- `description` (**版本说明**)：
  - `domain="text"`。描述版本功能或衍生版本的变更。
- `modelRequirements` (**业务需求说明**)：
  - `domain="markdown"`。
- `modelDefs` (**业务模型定义**)：
  - `domain="xml"`。
- `uiRequirements` (**UI 需求说明**)：
  - `domain="markdown"`
- `uiDefs` (**页面代码**)：
  - `domain="xml"`

#### 关键关联关系

- **AppVersionEntity -> AppEntity (一对多)**：**必填**
  - AppVersionEntity 有一个 `app`（所属应用），指向一个 `AppEntity`。
  - 相应地在 AppEntity 中有一个 `versions`（版本列表），以一对多类型反向关联 AppEntity 所包含的全部 AppVersionEntity，并且启用级联删除。
- **AppEntity -> AppVersionEntity (一对一)**：
  - AppEntity 有一个 `activeVersion`（已激活版本），指向一个 `AppVersionEntity`。
- **AppVersionEntity -> AppVersionEntity (一对一)**：
  - 一个版本可以通过 `derivedFrom` 属性指向其衍生来源的另一个 `AppVersionEntity`（父版本）。

### 需实现业务接口

- `AppVersionEntity__getAiPrompt(id, type)`：查询。获取应用版本的 AI 提示词
  - `id`：字符串。应用版本 id
  - `type`：字符串。可选值：`model`（模型设计）、`ui`（UI 设计）
  - 返回提示词文本
- `AppVersionEntity__genAiCode(id)`：变更。调用 AI 模型生成业务模型定义 `modelDefs` 和页面代码 `uiDefs`
  - `id`：字符串。应用版本 id
  - 无返回值。前端在该接口正常调用后，重新获取 `AppVersionEntity` 的 `modelDefs` 和 `uiDefs` 的值即可
