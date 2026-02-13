## 获取 AI 大模型列表：`AppCoder__findLlmModels`

```js
import { graphql } from '@app-utils';

const { AppCoder__findLlmModels } = await graphql(
  `
    query {
      AppCoder__findLlmModels {
        name, displayName
        models {
          name
        }
      }
    }
  `,
  { }
);

const { name, displayName, models } = AppCoder__findLlmModels;
```

## 根据需求生成模型设计代码：`AppCoder__genModelDesignCode(provider: !String, model: !String, requirements: !String)`

```js
import { graphql } from '@app-utils';

const { AppCoder__genModelDesignCode } = await graphql(
  `
    query ($provider: !String, $model: !String, $requirements: !String) {
      AppCoder__genModelDesignCode(provider: $provider, model: $model, requirements: $requirements) {
        content
      }
    }
  `,
  { provider: 'openai', model: 'gpt-4o', requirements: '模型设计需求'}
);

const { content } = AppCoder__genModelDesignCode;
```

## 根据需求生成 UI 设计代码：`AppCoder__genUiDesignCode(provider: !String, model: !String, requirements: !String)`

```js
import { graphql } from '@app-utils';

const { AppCoder__genUiDesignCode } = await graphql(
  `
    query ($provider: !String, $model: !String, $requirements: !String) {
      AppCoder__genUiDesignCode(provider: $provider, model: $model, requirements: $requirements) {
        content
      }
    }
  `,
  { provider: 'openai', model: 'gpt-4o', requirements: 'UI 设计需求'}
);

const { content } = AppCoder__genUiDesignCode;
```

## 生成模型设计提示词：`AppCoder__genModelDesignPrompt(requirements: !String)`

```js
import { graphql } from '@app-utils';

const { AppCoder__genModelDesignPrompt } = await graphql(
  `
    query ($requirements: !String) {
      AppCoder__genUiDesignCode(requirements: $requirements)
    }
  `,
  { requirements: '模型设计需求'}
);

const prompt = AppCoder__genModelDesignPrompt;
```

## 生成 UI 设计提示词：`AppCoder__genUiDesignPrompt(requirements: !String)`

```js
import { graphql } from '@app-utils';

const { AppCoder__genUiDesignPrompt } = await graphql(
  `
    query ($requirements: !String) {
      AppCoder__genUiDesignCode(requirements: $requirements)
    }
  `,
  { requirements: 'UI 设计需求'}
);

const prompt = AppCoder__genUiDesignPrompt;
```
