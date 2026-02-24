## 获取字典项列表：`DictProvider__getDict`

### 描述

获取指定 `name` 的 `<dict>` 字典项。返回的列表可用于字典项下拉列表的数据源。

### 语法

```graphql
query($name: String!) {
  DictProvider__getDict(dictName: $name) {
    label
    name
    options {
      label
      code
      description
      value
    }
    description
  }
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `name` | `String` | 是 | `<dict>` 字典名，如 `"user/status"` |

### 返回值

<!-- prettier-ignore -->
| 字段 | 类型 | 描述 |
|------|------|------|
| `name` | `String` | 字典名（如 `"user/status"`） |
| `label` | `String` | 字典显示名（如 `"用户状态"`） |
| `description` | `String` | 字典描述 |
| `options` | `[Option]` | 字典项列表 |
| `options.code` | `String` | 字典项编码（如 `"DISABLED"`） |
| `options.value` | `String` | 字典项的值（如 `"020"`） |
| `options.label` | `String` | 字典项显示名（如 `"已禁用"`） |
| `options.description` | `String` | 字典项描述 |

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { DictProvider__getDict } = await graphql(
  `
    query($name: String!) {
      DictProvider__getDict(dictName: $name) {
        label
        name
        options {
          label
          code
          description
          value
        }
        description
      }
    }
  `,
  { name: 'user/status' }
);

// 使用示例
const { options } = DictProvider__getDict;
const { code, value, label } = options;
```

## 获取 AI 大模型列表：`AppCoder__findLlmModels`

### 描述

查询当前可用的 AI 模型提供商及其支持的模型名称。返回的列表可用于后续代码生成接口的参数选择。

### 语法

```graphql
query {
  AppCoder__findLlmModels {
    name
    displayName
    models {
      name
    }
  }
}
```

### 返回值

<!-- prettier-ignore -->
| 字段 | 类型 | 描述 |
|------|------|------|
| `name` | `String` | 提供商的唯一标识（如 `"openai"`） |
| `displayName` | `String` | 提供商的显示名称（如 `"OpenAI"`） |
| `models` | `[Model]` | 该提供商支持的模型列表 |
| `models.name` | `String` | 模型名称（如 `"gpt-4o"`） |

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__findLlmModels } = await graphql(
  `
    query {
      AppCoder__findLlmModels {
        name
        displayName
        models {
          name
        }
      }
    }
  `,
  {} // 无变量
);

// 使用示例
const [firstProvider] = AppCoder__findLlmModels;
console.log(firstProvider.displayName); // 例如 "OpenAI"
console.log(firstProvider.models.map((m) => m.name)); // 例如 ["gpt-4o", "gpt-3.5-turbo"]
```

### 注意事项

- 该查询不需要任何参数。
- 返回的模型列表可能随后端配置变化，建议在使用代码生成接口前实时获取。

## 根据需求生成模型设计代码：`AppCoder__genModelDesignCode`

### 描述

根据指定的 AI 提供商、模型和需求描述，生成模型设计相关的代码（如数据模型、实体类等）。

### 语法

```graphql
query ($provider: String!, $model: String!, $bizRequirements: String!, $modelRequirements: String!) {
  AppCoder__genModelDesignCode(
    provider: $provider
    model: $model
    bizRequirements: $bizRequirements
    modelRequirements: $modelRequirements
  ) {
    content
  }
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `provider` | `String` | 是 | 模型提供商标识，如 `"openai"` |
| `model` | `String` | 是 | 具体模型名称，如 `"gpt-4o"` |
| `bizRequirements` | `String` | 是 | 业务需求的自然语言描述 |
| `modelRequirements` | `String` | 是 | 模型设计需求的自然语言描述 |

### 返回值

<!-- prettier-ignore -->
| 字段 | 类型 | 描述 |
|------|------|------|
| `content` | `String` | 生成的模型设计代码（通常包含注释和代码块） |

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__genModelDesignCode } = await graphql(
  `
    query ($provider: String!, $model: String!, $bizRequirements: String!, $modelRequirements: String!) {
      AppCoder__genModelDesignCode(
        provider: $provider
        model: $model
        bizRequirements: $bizRequirements
        modelRequirements: $modelRequirements
      ) {
        content
      }
    }
  `,
  {
    provider: 'openai',
    model: 'gpt-4o',
    bizRequirements: '设计一个电商系统',
    modelRequirements: '用户模型包含姓名、邮箱、地址列表'
  }
);

const { content } = AppCoder__genModelDesignCode;
console.log(content);
```

### 注意事项

- 所有参数均为必填，空字符串可能导致生成结果不理想。
- 建议先通过 `AppCoder__findLlmModels` 获取可用的 `provider` 和 `model` 组合。

## 根据需求生成 UI 设计代码：`AppCoder__genUiDesignCode`

### 描述

根据指定的 AI 提供商、模型和需求描述，生成 UI 设计相关的代码（如 React 组件、CSS 等）。

### 语法

```graphql
query ($provider: String!, $model: String!, $bizRequirements: String!, $uiRequirements: String!, $bizModelDefs: String!) {
  AppCoder__genUiDesignCode(
    provider: $provider
    model: $model
    bizRequirements: $bizRequirements
    uiRequirements: $uiRequirements
    bizModelDefs: $bizModelDefs
  ) {
    content
  }
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `provider` | `String` | 是 | 模型提供商标识 |
| `model` | `String` | 是 | 具体模型名称 |
| `bizRequirements` | `String` | 是 | 业务需求的自然语言描述 |
| `uiRequirements` | `String` | 是 | UI 设计需求的自然语言描述 |
| `bizModelDefs` | `String` | 是 | 业务模型定义 |

### 返回值

<!-- prettier-ignore -->
| 字段 | 类型 | 描述 |
|------|------|------|
| `content` | `String` | 生成的 UI 设计代码 |

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__genUiDesignCode } = await graphql(
  `
    query ($provider: String!, $model: String!, $bizRequirements: String!, $uiRequirements: String!, $bizModelDefs: String!) {
      AppCoder__genUiDesignCode(
        provider: $provider
        model: $model
        bizRequirements: $bizRequirements
        uiRequirements: $uiRequirements
        bizModelDefs: $bizModelDefs
      ) {
        content
      }
    }
  `,
  {
    provider: 'openai',
    model: 'gpt-4o',
    bizRequirements: '用户管理系统',
    uiRequirements: '登录表单包含用户名、密码输入框和提交按钮',
    bizModelDefs: '<model-design><dicts>...</dicts><entities>...</entities></model-design>'
  }
);

const { content } = AppCoder__genUiDesignCode;
console.log(content);
```

### 注意事项

- 同模型设计代码接口，参数必填，建议先获取可用模型列表。

## 根据需求生成 Logo 设计代码：`AppCoder__genLogoDesignCode`

### 描述

根据指定的 AI 提供商、模型和需求描述，生成 svg 格式的 Logo 设计代码。

### 语法

```graphql
query ($provider: String!, $model: String!, $bizRequirements: String!, $logoRequirements: String!) {
  AppCoder__genLogoDesignCode(
    provider: $provider
    model: $model
    bizRequirements: $bizRequirements
    logoRequirements: $logoRequirements
  ) {
    content
  }
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `provider` | `String` | 是 | 模型提供商标识 |
| `model` | `String` | 是 | 具体模型名称 |
| `bizRequirements` | `String` | 是 | 业务需求的自然语言描述 |
| `logoRequirements` | `String` | 是 | Logo 设计需求的自然语言描述 |

### 返回值

<!-- prettier-ignore -->
| 字段 | 类型 | 描述 |
|------|------|------|
| `content` | `String` | 生成的 Logo 的 svg 代码 |

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__genLogoDesignCode } = await graphql(
  `
    query ($provider: String!, $model: String!, $bizRequirements: String!, $logoRequirements: String!) {
      AppCoder__genLogoDesignCode(
        provider: $provider
        model: $model
        bizRequirements: $bizRequirements
        logoRequirements: $logoRequirements
      ) {
        content
      }
    }
  `,
  {
    provider: 'openai',
    model: 'gpt-4o',
    bizRequirements: '记录个人/家庭收支的账本',
    logoRequirements: '中国传统账簿图标'
  }
);

const { content } = AppCoder__genLogoDesignCode;
console.log(content);
```

## 生成模型设计提示词：`AppCoder__genModelDesignPrompt`

### 描述

根据需求描述，生成一个专门用于引导 AI 进行模型设计的提示词。该提示词可以后续用于其他 AI 接口或人工审查。

### 语法

```graphql
query ($bizRequirements: String!, $modelRequirements: String!) {
  AppCoder__genModelDesignPrompt(bizRequirements: $bizRequirements, modelRequirements: $modelRequirements)
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `bizRequirements` | `String` | 是 | 业务需求的自然语言描述 |
| `modelRequirements` | `String` | 是 | 模型设计需求的自然语言描述 |

### 返回值

直接返回生成的提示词字符串，非对象包装。

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__genModelDesignPrompt } = await graphql(
  `
    query ($bizRequirements: String!, $modelRequirements: String!) {
      AppCoder__genModelDesignPrompt(bizRequirements: $bizRequirements, modelRequirements: $modelRequirements)
    }
  `,
  {
    bizRequirements: '设计一个电商系统',
    modelRequirements: '用户模型包含姓名、邮箱、地址列表'
  }
);

const prompt = AppCoder__genModelDesignPrompt;
console.log(prompt); // 输出优化后的提示词文本
```

### 注意事项

- 该接口只生成提示词，不调用 AI 模型生成代码。

## 生成 UI 设计提示词：`AppCoder__genUiDesignPrompt`

### 描述

根据需求描述，生成一个专门用于引导 AI 进行 UI 设计的提示词。

### 语法

```graphql
query ($bizRequirements: String!, $uiRequirements: String!, $bizModelDefs: String!) {
  AppCoder__genUiDesignPrompt(
    bizRequirements: $bizRequirements
    uiRequirements: $uiRequirements
    bizModelDefs: $bizModelDefs
  )
}
```

### 参数

<!-- prettier-ignore -->
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| `bizRequirements` | `String` | 是 | 业务需求的自然语言描述 |
| `uiRequirements` | `String` | 是 | UI 设计需求的自然语言描述 |
| `bizModelDefs` | `String` | 是 | 业务模型定义 |

### 返回值

直接返回生成的提示词字符串。

### 示例代码

```javascript
import { graphql } from '@app-utils';

const { AppCoder__genUiDesignPrompt } = await graphql(
  `
    query ($bizRequirements: String!, $uiRequirements: String!, $bizModelDefs: String!) {
      AppCoder__genUiDesignPrompt(
        bizRequirements: $bizRequirements
        uiRequirements: $uiRequirements
        bizModelDefs: $bizModelDefs
      )
    }
  `,
  {
    bizRequirements: '用户管理系统',
    uiRequirements: '登录表单包含用户名、密码输入框和提交按钮',
    bizModelDefs: '<model-design><dicts>...</dicts><entities>...</entities></model-design>'
  }
);

const prompt = AppCoder__genUiDesignPrompt;
console.log(prompt);
```

### 注意事项

- 该接口只生成提示词，不调用 AI 模型生成代码。
