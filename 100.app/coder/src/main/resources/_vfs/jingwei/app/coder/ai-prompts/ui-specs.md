## 核心要求

- 所有功能均在单个页面内实现。
- 遵循移动优先、响应式布局原则。
- 界面风格应醒目、清晰、简洁、直接。

## 框架与库

- **开发语言**：ES6 (ECMAScript 2015)
- **UI 框架**：Vue 3 (响应式系统)
- **组件库**：Naive UI (`naive-ui`)
- **图标库**：Lucide Vue Next (`lucide-vue-next`)
- **样式方案**：Tailwind CSS，直接引用所需的样式名字，不做导入
- **数据可视化**：Apache ECharts (`echarts`) + Vue ECharts (`vue-echarts`)
- **代码高亮**：highlight.js (`highlight.js`)
- **禁止使用**
  - vue-router 等路由库，全部以组件显隐方式作视图切换
  - @vicons 等图标库，若 lucide-vue-next 中的图标不满足需求，则自行创建 svg 图标

## 强制性编码规范

- **组件使用**：优先使用 Naive UI 组件，若无合适组件，则自行编写。
- **图标使用**：优先使用 Lucide 提供的 SVG 图标，若无则自行绘制 SVG。
- **模块导入**：**仅导入**实际使用到的 ES 模块，**禁止**导入无关、未使用或核心框架以外的模块
  - 需以 `import CompA from './components/CompA.vue'` 形式导入自定义组件
- **作用域**：所有逻辑与自定义组件需在当前页面或 `<component>` 中完成，**禁止**引入外部模块或拆分为多个文件
- **编程风格**：采用声明式、响应式编程，保持逻辑层与 UI 层分离。
- **文本格式**：在中文与英文、数字之间**必须添加空格**，但中文标点与英文、数字之间**不能加空格**。
  > 示例：`这是一个示例 example 123。`

## 数据与组件映射规则

根据业务模型属性的以下特性，自动决定其在表单中使用的组件与状态：

- **虚属性**：始终只读。
- **不可新增**：不出现在新增表单中。
- **不可更新**：在编辑表单中为只读状态。
- **非开放**：在查看表单中需**标注为“私密”**；若可更新，则根据业务性质决定是否使用密码输入框。
- **关联字典**：使用下拉选择组件，数据源为对应字典。
- **建立关联 (一对一/一对多)**：使用弹出窗口以列表形式展示目标端数据，并根据需求支持单选或多选。
- **输入校验**：根据业务模型中对属性长度、类型等的约束要求进行输入校验。

## 默认功能要求

除非特别说明，否则为主要业务模型提供完整的**增删改查 (CRUD)** 功能，包括：

- 数据列表 (支持批量删除)。
- 查看表单。
- 新增/编辑表单 (新增表单支持“保存并继续添加”)。
- 优先采用通用的实体 CRUD 接口实现相关的数据操作，非必要不为实体的部分数据更新新增业务接口。

## 演示数据 (`<demo-data>`) 定义规则

- 页面中的动态数据全部调用对应实体的数据操作接口得到，**禁止**放置模拟数据。
- 根据数据操作接口创建演示数据，并以接口名作为 `<action>` 的 `name`，如 `UserEntity__save`、`UserEntity__findPage`。
- 在 `<source>` 中可根据接口参数动态确定所要返回的数据，从而实现对多种情况的演示效果

示例：

```xml
<action name="UserEntity__get">
  <source><![CDATA[
    if (id == 'c5fd5e8f5ec74d189b3d1023a79508ba') {
      return { id: id, name: 'Lily' };
    } else {
      return { id: 'unknown', name: 'Unknown' };
    }
  ]]></source>
</action>

<action name="UserEntity__save">
  <source><![CDATA[
    return { id: 'c5fd5e8f5ec74d189b3d1023a79508ba', name: 'Lily' };
  ]]></source>
</action>

<action name="UserEntity__findPage">
  <source><![CDATA[
    return { total: 12, items: [{ id: 'c5fd5e8f5ec74d189b3d1023a79508ba', name: 'Lily' }, ...] };
  ]]></source>
</action>

<action name="UserEntity__findList">
  <source><![CDATA[
    return [{ id: 'c5fd5e8f5ec74d189b3d1023a79508ba', name: 'Lily' }, ...];
  ]]></source>
</action>

<action name="UserEntity__delete">
  <source><![CDATA[
    if (id == 'c5fd5e8f5ec74d189b3d1023a79508ba') {
      return true;
    } else {
      return false;
    }
  ]]></source>
</action>
```
