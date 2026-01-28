### 应用页面加载

直接修改 URL 地址，在其中添加或修改参数 `app` 为应用的 `code`，再刷新页面即可。

### 应用页面预览

预览页面在 iframe 中显示，其 URL 地址与系统访问地址相同，只是需附加参数
`app=app-code&version=app-version-name&preview=true`。

### 消息提醒

- 始终从依赖 `@app-utils` 中导入

```js
import { message, notification, dialog, loadingBar, modal } from '@app-utils';
```

- `message`, `notification`, `dialog`, `loadingBar`, `modal`
  分别由 Naive UI 的 `useMessage`, `useNotification`, `useDialog`, `useLoadingBar`, `useModal`
  函数所创建，直接调用其实例的 API 接口即可

### 数据操作接口调用

- 所有的数据操作接口均采用 GraphQL 规范，并按如下形式调用：

```js
import { graphql } from '@app-utils';

const { User__save } = await graphql(
  `
    mutation ($data: Map) {
      User__save(data: $data) {
        id, name, age
      }
    }
  `,
  { data: { name: 'Lily', age: 18, ... } }
);
```

- `graphql` 函数始终从依赖 `@app-utils` 中导入。
- **禁止**对 `graphql` 做异常拦截，仅需要在 finally 中做收尾工作。

#### 通用 CRUD 接口

- `findPage(query: QueryBeanInput)`：分页查询

```js
import { graphql } from '@app-utils';

const { User__findPage } = await graphql(
  `
    query ($query: QueryBeanInput) {
      User__findPage(query: $query) {
        total
        items {
          id, name
          roles: roleList(limit: 10) {
            items {
              id
              roleName: name
            }
          }
        }
      }
    }
  `，
  { query: {
    offset: 0, limit: 20,
    filter: {
      "$type": "eq",
      name: "name",
      value: "Tom",
    },
    orderBy: [{ name: 'name', desc: true }, { name: 'age', desc: false }]
  } }
);
```

- `findList(query: QueryBeanInput)`：查询并返回所有数据

```js
import { graphql } from '@app-utils';

const { User__findList } = await graphql(
  `
    query ($query: QueryBeanInput) {
      User__findList(query: $query) {
        id, name
        roles {
          id
          roleName: name
        }
      }
    }
  `，
  { query: {
    filter: {
      "$type": "and",
      "$body": [
        {
          "$type": "eq",
          name: "name",
          value: "Tom"
        },
        {
          "$type": "gt",
          name: "age",
          value: 21
        }
      ]
    }
  } }
);
```

- `findFirst(query: QueryBeanInput)`：条件查询首条

```js
import { graphql } from '@app-utils';

const { User__findFirst } = await graphql(
  `
    query ($query: QueryBeanInput) {
      User__findFirst(query: $query) {
        id, name
        roles {
          id
          roleName: name
        }
      }
    }
  `，
  { query: { filter: {...} } }
);
```

- `save(data: Map)`：新建数据

```js
import { graphql } from '@app-utils';

const { User__save } = await graphql(
  `
    mutation ($data: Map) {
      User__save(data: $data) {
        id, name, age
      }
    }
  `,
  { data: { name: 'Lily', age: 18, ... } }
);
```

- `update(id: String, data: Map)`：更新数据

```js
import { graphql } from '@app-utils';

const { User__update } = await graphql(
  `
    mutation ($id: String, $data: Map) {
      User__update(id: $id, data: $data) {
        id
        name
      }
    }
  `,
  { id: 'c5fd5e8f5ec74d189b3d1023a79508ba', data: { name: 'Tom' } }
);
```

- `get(id: String)`：获取数据

```js
import { graphql } from '@app-utils';

const { User__get } = await graphql(
  `
    mutation ($id: String) {
      User__get(id: $id) {
        id
        name
        roles {
          id
          name
        }
      }
    }
  `,
  { id: 'c5fd5e8f5ec74d189b3d1023a79508ba' }
);
```

- `delete(id: String)`：删除数据

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($id: String) {
      User__delete(id: $id)
    }
  `,
  { id: 'c5fd5e8f5ec74d189b3d1023a79508ba' }
);
```

- `batchDelete(ids: [String])` 批量删除

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($ids: [String]) {
      User__batchDelete(ids: $ids)
    }
  `,
  { ids: ['c5fd5e8f5ec74d189b3d1023a79508ba', 'c5fd5e8f5ec74d189b3d1023a79508bc'] }
);
```

- `batchUpdate(ids: [String], data: Map)` 批量更新

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($ids: [String], $data: Map) {
      Product__batchUpdate(ids: $ids, data: $data)
    }
  `,
  { ids: ['c5fd5e8f5ec74d189b3d1023a79508ba', 'c5fd5e8f5ec74d189b3d1023a79508bc'], data: { price: 123, amount: 321 } }
);
```
