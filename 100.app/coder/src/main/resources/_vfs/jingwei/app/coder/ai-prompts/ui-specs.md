## 核心要求

- **单页应用**：所有功能（列表、表单、详情等）均集中在同一个页面内实现，通过组件显隐控制视图切换，禁止使用路由。
- **移动优先与响应式**：优先考虑移动端布局，使用 Tailwind CSS 的响应式工具类（如 `sm:`、`md:`）适配不同屏幕尺寸，确保界面在手机、平板、桌面均清晰易用。
- **风格要求**：界面设计应醒目、清晰、简洁、直接，避免冗余装饰，保持操作路径最短。

## 框架与库

- **开发语言**：ES6+，采用现代 JavaScript 语法。
- **UI 框架**：Vue 3（使用 Composition API 与 `<script setup>` 语法），利用其响应式系统管理数据和状态。
- **组件库**：Naive UI（`naive-ui`），作为主要的基础组件来源，例如按钮、输入框、弹窗、表格等。若无合适组件，则自行编写 Vue 组件。
- **图标库**：Lucide Vue Next（`lucide-vue-next`），提供丰富的 SVG 图标。若 Lucide 图标不满足需求，需自行绘制 SVG 图标并封装为 Vue 组件，禁止使用其他图标库（如 `@vicons`）。
- **样式方案**：Tailwind CSS，直接在模板中使用原子类名，如 `class="flex items-center p-4"`。**禁止**在 `<style>` 中编写额外的 CSS，除非需要覆盖第三方组件样式或实现 Tailwind 无法完成的复杂样式。
- **数据可视化**：Apache ECharts（`echarts`）配合 Vue ECharts（`vue-echarts`），用于图表展示。图表应响应式适配容器尺寸变化。
- **代码高亮**：`highlight.js`，用于在代码展示区域高亮代码块。

## 强制性编码规范

### 自定义组件 (`<component>`)

**优先使用 Naive UI 组件**：所有基础 UI 元素（如按钮、输入框、下拉框、表格等）应首先从 Naive UI 导入并使用。例如：

```vue
<n-button type="primary">提交</n-button>
```

> **禁止使用 Naive UI 的 useMessage/useDialog/useNotification/useModal 函数**。

当 Naive UI 无法满足需求时（如特殊布局、业务复合组件），在 `<component>` 中编写 Vue 组件：

```xml
<component name="CustomCmp"><![CDATA[
  <template>...</template>
  <script setup>...</script>
]]></component>
```

再在 `<page>` 中以 `import CustomCmp from './components/CustomCmp';` 形式导入。

### 图标使用

- **优先使用 Lucide 图标**：例如 `<lucide-user />`，需从 `lucide-vue-next` 导入具体图标组件。
- **自定义 SVG 图标**：若所需图标不在 Lucide 集中，自行绘制 SVG，并封装为单文件组件或内联模板。例如：
  ```html
  <svg width="24" height="24" viewBox="0 0 24 24">...</svg>
  ```

### 模块导入

- **精确导入**：仅导入实际使用的模块，禁止导入未使用的模块。例如：
  ```javascript
  import { NButton, NInput } from 'naive-ui'; // 正确
  import * as naive from 'naive-ui'; // 禁止，会引入所有组件
  ```

### 编程风格

- **声明式与响应式**：利用 Vue 3 的 `ref`、`reactive`、`computed` 等 API 管理状态，模板中直接绑定数据和事件。避免直接操作 DOM。
- **逻辑与 UI 分离**：业务逻辑（数据处理、接口调用）应放在 `<script setup>` 中，模板仅负责渲染和事件绑定。可提取可复用的逻辑为 Composition 函数（仍定义在页面内）。

### 文本格式

- **中英文混排规范**：中文与英文、数字之间必须添加空格，但中文标点符号与英文、数字之间**不加**空格。
  - 正确示例：`这是一个示例 example 123。`
  - 错误示例：`这是一个示例example123。`（缺少空格）
  - 错误示例：`这是一个示例 example 123 。`（中文句号前多空格）

## 数据与组件映射规则

根据业务模型属性的特性，自动决定表单中使用的组件类型及校验规则。

### 关联字典

- **定义方式**：字典数据由 `@app-utils` 提供的 `getDictOptions` 函数根据字典名动态获取，禁止在页面中硬编码字典选项。调用示例：

  ```javascript
  import { getDictOptions } from '@app-utils';

  const options = await getDictOptions('user/status');
  ```

- **组件映射**：对应属性在表单中应使用下拉选择组件（如 `<n-select>`），选项数据从接口返回的字典项动态生成。
- **静态字典**：若业务模型中未定义字典（如一些固定选项），则可以在代码中定义为常量数组。

### 建立关联（一对一/一对多）

- **一对一关联**：通过“属性名 + Id”的字段（如 `userId`）绑定关联目标。在表单中，通常需要一个弹出窗口，以列表形式展示目标端数据，支持单选。用户选择后，将选中的目标 ID 赋值给对应的 `xxxId` 字段。
- **一对多关联**：同样使用弹出窗口，但应支持多选。选择结果通常以数组形式存储（如 `roleIds`）。
- **实现方式**：弹窗内使用表格展示目标端数据，通过复选框（多选）或单选按钮（单选）进行选择。表格数据通过对应的实体查询接口获取（如 `RoleEntity__findList`）。

### 输入校验

- **校验来源**：校验规则依据业务模型中的实体属性的定义及其数据约束 `<constraint>`，包括：
  - 必填（`mandatory="true"`）
  - 长度限制（如 `maxLength="20"`）
  - 许可的文件类型（如 `allowedFileTypes="image/jpeg,image/png"`）
  - 限制的文件大小（如 `maxFileSize="1M"`）
  - 正则表达式（如 `pattern="\d+{3,8}"`）
- **实现方式**：在表单提交前执行校验。可使用 Naive UI 表单组件的校验功能，或自定义校验逻辑。示例：
  ```html
  <n-form-item label="姓名" :rule="{ required: true, message: '请输入姓名' }">
    <n-input v-model:value="form.name" />
  </n-form-item>
  ```

### 文本编辑

- 非特殊说明，包括 svg、xml、html、markdown 等在内的所有类型的长文本，均使用文本输入组件。
- 对于 svg、xml、html、markdown 等各种代码，在只读状态时均通过 `highlight.js` 高亮其内容。

### 隐式属性

- `xxx_label`？只读，更新通过对应的字典属性实现

## 交互体验

- **操作路径最短**：任何功能（增、删、改、查）应在 3 步以内完成。例如，新增按钮应直接展示表单弹窗，而非跳转页面。
- **保持视图中心**：关键操作按钮、提示信息、弹窗应位于屏幕视觉中心区域，避免偏离用户视野。
- **即时反馈**：所有用户操作（如提交、删除）均需给出明确反馈（加载状态、成功/错误提示）。

## 默认功能要求

除非特别说明，为主要业务模型提供完整的**增删改查 (CRUD)** 功能，具体实现如下：

- **数据列表**
  - 使用 `<n-table>` 或 `<n-data-table>` 展示数据，支持分页。
  - 列表顶部提供“新增”按钮，每行提供“编辑”、“删除”操作，列表左侧可勾选多行以支持批量删除。
  - 批量删除：点击“批量删除”按钮后，弹出确认对话框，确认后调用批量删除接口。

- **查看表单**
  - 点击列表中的“查看”或直接点击某行数据，应弹窗展示只读表单，展示该记录的详细信息。
  - 弹窗底部提供“编辑”、“关闭”按钮。

- **新增/编辑表单**
  - 均通过弹窗呈现表单，包含所有需要填写的字段。
  - 新增表单底部提供“重置”、“保存”、“保存并继续添加”按钮。“保存并继续添加”提交成功后不清空表单，允许连续录入。
  - 编辑表单底部提供“保存”、“复制新增”按钮。“复制新增”将当前编辑的数据复制一份作为新增表单打开（ID 清空，其他字段保留），方便快速创建类似记录。

- **数据操作接口**
  - 优先使用通用实体 CRUD 接口实现数据操作，如 `UserEntity__save`、`UserEntity__delete`、`UserEntity__findPage` 等。
  - 对于单个或多个属性的更新（例如修改状态），若涉及额外处理逻辑（如校验、级联更新），则应使用专用的业务操作接口，而非直接调用 `UserEntity__update`。

## 演示数据 (`<demo-data>`) 定义规则

在开发或演示环境中，页面所需的动态数据均通过模拟接口（Mock）返回，禁止在 `<page>` 和 `<component>` 中的代码中硬编码模拟数据。模拟接口的定义遵循以下规则：

- **使用 `<action>` 标签**：每个模拟接口对应一个 `<action>` 标签，`name` 属性为接口名（如 `UserEntity__save`）。
- **动态数据逻辑**：在 `<source>` 中使用 JavaScript（ES6 规范）编写数据返回逻辑。可以根据传入的参数（如 `data.id`、`query.offset`）以动态返回不同的数据，以模拟真实后端行为。
- **接口命名**：接口名应遵循实际项目中的命名规范，如 `UserEntity__findPage` 表示分页查询用户实体。

**示例**：

```xml
<!-- 根据 ID 查询单个实体 -->
<action name="UserEntity__get">
  <source><![CDATA[
    if (data.id === 'c5fd5e8f5ec74d189b3d1023a79508ba') {
      return { id: data.id, name: 'Lily', age: 28 };
    } else {
      return { id: 'unknown', name: 'Unknown', age: 0 };
    }
  ]]></source>
</action>

<!-- 保存实体（新增或更新） -->
<action name="UserEntity__save">
  <source><![CDATA[
    // 模拟保存操作，返回保存后的实体（可能包含新生成的 ID）
    return { id: 'c5fd5e8f5ec74d189b3d1023a79508ba', name: data.name, age: data.age };
  ]]></source>
</action>

<!-- 分页查询 -->
<action name="UserEntity__findPage">
  <source><![CDATA[
    // 根据 query.offset 和 query.limit 返回分页数据
    const items = [
      { id: '1', name: 'Lily', age: 28 },
      { id: '2', name: 'Lucy', age: 25 },
    ];
    return {
      total: items.length,
      items: items,
    };
  ]]></source>
</action>

<!-- 查询列表（无分页） -->
<action name="UserEntity__findList">
  <source><![CDATA[
    return [
      { id: '1', name: 'Lily' },
      { id: '2', name: 'Lucy' },
    ];
  ]]></source>
</action>

<!-- 删除实体 -->
<action name="UserEntity__delete">
  <source><![CDATA[
    // 根据 ID 删除，返回成功或失败
    if (data.id === '1') {
      return true;   // 删除成功
    } else {
      return false;  // 删除失败
    }
  ]]></source>
</action>
```

**注意事项**：

- 所有 `<action>` 应集中定义在文档的模拟数据区域，便于维护。
- 模拟数据应尽量接近真实数据结构，字段类型、命名需与实际接口一致。
- 对于需要模拟分页、排序、过滤的场景，可在 `<source>` 中编写相应逻辑。
