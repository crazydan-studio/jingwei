## 通用查询参数 `QueryBeanInput`

多数查询接口接受一个 `QueryBeanInput` 类型的参数，其结构如下（以 JSON 形式表示）：

```json
{
  "offset": 0, // 可选，分页起始位置
  "limit": 20, // 可选，每页条数
  "filter": { // 可选，过滤条件
    "$type": "eq", // 操作符：eq, ne, gt, ge, lt, le, like, in, not, and, or 等
    "name": "字段名", // 字段名
    "value": "字段值" // 比较值（根据操作符可能不同）
    // 对于 and/or，使用 "$body" 数组包含多个条件
  },
  "orderBy": [
    // 可选，排序
    { "name": "字段名", "desc": true } // desc 为 true 表示降序
  ]
}
```

- **filter 说明**：
  - 原子条件：`{ "$type": "eq", "name": "age", "value": 18 }`
  - 组合条件：`{ "$type": "and", "$body": [条件1, 条件2, ...] }`
  - 支持的操作符：`eq`（等于）、`ne`（不等于）、`gt`（大于）、`ge`（大于等于）、`lt`（小于）、`le`（小于等于）、`like`（模糊匹配）、`in`（在列表中）、`not`（非）、`and`（与）、`or`（或）等。

## 分页查询 `findPage`

**功能**：按条件查询实体列表，并返回符合条件的数据总数及当前页数据项。

**GraphQL 操作类型**：`query`

**参数**：`query: QueryBeanInput`

**返回**：对象包含 `total`（总数）和 `items`（当前页数据数组），`items` 中可指定需要返回的字段，并支持嵌套查询关联实体。

**示例**：查询名字为 "Tom" 的用户，按姓名降序、年龄升序排序，每页 20 条，同时加载每个用户的前 10 个角色。

```js
import { graphql } from '@app-utils';

const { UserEntity__findPage } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findPage(query: $query) {
        total
        items {
          id
          name
          roles: roleList(limit: 10) {
            # 嵌套查询关联实体，可指定子查询参数
            items {
              id
              roleName: name # 字段别名
            }
          }
        }
      }
    }
  `,
  {
    query: {
      offset: 0,
      limit: 20,
      filter: {
        $type: 'eq',
        name: 'name',
        value: 'Tom'
      },
      orderBy: [
        { name: 'name', desc: true },
        { name: 'age', desc: false }
      ]
    }
  }
);

const { total, items } = UserEntity__findPage;
```

**注意**：必须显式指定返回字段（包括嵌套字段），否则 GraphQL 会返回错误。

## 列表查询 `findList`

**功能**：按条件查询所有符合的数据（不分页），返回数据项数组。

**GraphQL 操作类型**：`query`

**参数**：`query: QueryBeanInput`

**返回**：符合条件的实体数组（需指定返回字段）。

**示例**：查询年龄大于 21 且名字为 "Tom" 的所有用户。

```js
import { graphql } from '@app-utils';

const { UserEntity__findList } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findList(query: $query) {
        id
        name
        roles {
          # 默认加载所有关联角色（除非服务端限制）
          id
          roleName: name
        }
      }
    }
  `,
  {
    query: {
      filter: {
        $type: 'and',
        $body: [
          {
            $type: 'eq',
            name: 'name',
            value: 'Tom'
          },
          {
            $type: 'gt',
            name: 'age',
            value: 21
          }
        ]
      }
    }
  }
);

const users = UserEntity__findList; // 数组
```

## 查询第一条 `findFirst`

**功能**：按条件查询，返回符合条件的第一条数据（通常配合排序使用）。

**GraphQL 操作类型**：`query`

**参数**：`query: QueryBeanInput`

**返回**：单个实体对象，若无数据则返回 `null`。

**示例**：

```js
import { graphql } from '@app-utils';

const { UserEntity__findFirst } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findFirst(query: $query) {
        id
        name
        roles {
          id
          roleName: name
        }
      }
    }
  `,
  {
    query: {
      filter: {
        $type: 'eq',
        name: 'name',
        value: 'Tom'
      },
      orderBy: [{ name: 'age', desc: true }] // 取年龄最大的
    }
  }
);

const user = UserEntity__findFirst;
```

## 新增数据 `save`

**功能**：创建一条新实体记录。

**GraphQL 操作类型**：`mutation`

**参数**：`data: Map` – 一个键值对对象，包含要设置的字段。主键通常由数据库自动生成，无需传入。

**返回**：创建后的实体对象（需指定返回字段）。

**示例**：新增一个用户，并返回其 `id`、`name`、`age`。

```js
import { graphql } from '@app-utils';

const { UserEntity__save } = await graphql(
  `
    mutation ($data: Map) {
      UserEntity__save(data: $data) {
        id
        name
        age
      }
    }
  `,
  {
    data: {
      name: 'Lily',
      age: 18,
      orgId: 'c5fd5e8f5ec74d189b3d1023a79508be'
      // 其他字段...
    }
  }
);

const { id, name, age } = UserEntity__save;
```

**注意**：必须指定返回字段，否则操作成功但无法获取新数据。

## 更新数据 `update`

**功能**：更新一条已有实体记录。

**GraphQL 操作类型**：`mutation`

**参数**：`data: Map` – 必须包含实体主键 `id`，其他字段为待更新的属性。

**返回**：更新后的实体对象（需指定返回字段）。

**示例**：更新指定用户的姓名。

```js
import { graphql } from '@app-utils';

const { UserEntity__update } = await graphql(
  `
    mutation ($data: Map) {
      UserEntity__update(data: $data) {
        id
        name
      }
    }
  `,
  {
    data: {
      id: 'c5fd5e8f5ec74d189b3d1023a79508ba',
      name: 'Tom',
      orgId: 'c5fd5e8f5ec74d189b3d1023a79508be' // 也可更新其他字段
    }
  }
);

const { id, name } = UserEntity__update;
```

**注意**：`data` 中必须包含 `id`，否则服务端无法定位要更新的记录。

## 复制新增 `copyForNew`

**功能**：基于现有数据创建一个新实体，并可覆盖部分字段。适用于“复制”或“衍生”场景。

**GraphQL 操作类型**：`mutation`

**参数**：`data: Map` – 必须包含源实体的主键 `id`，以及需要覆盖的字段（可选）。

**返回**：新创建的实体对象（需指定返回字段）。

**示例**：以 ID 为 `...ba` 的用户为模板，创建一个新用户，并将姓名改为 "Lily"。

```js
import { graphql } from '@app-utils';

const { UserEntity__copyForNew } = await graphql(
  `
    mutation ($data: Map) {
      UserEntity__copyForNew(data: $data) {
        id
        name
      }
    }
  `,
  {
    data: {
      id: 'c5fd5e8f5ec74d189b3d1023a79508ba', // 源实体 ID
      name: 'Lily' // 覆盖字段
    }
  }
);

const { id, name } = UserEntity__copyForNew;
```

**注意**：`data` 中必须包含 `id`，新记录的主键由系统自动生成。

## 获取单条数据 `get`

**功能**：根据主键 ID 获取实体详情。

**GraphQL 操作类型**：`query`

**参数**：`id: String`

**返回**：实体对象，若不存在返回 `null`。

**示例**：

```js
import { graphql } from '@app-utils';

const { UserEntity__get } = await graphql(
  `
    query ($id: String) {
      UserEntity__get(id: $id) {
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

const { id, name, roles } = UserEntity__get;
```

## 删除单条数据 `delete`

**功能**：根据主键 ID 删除一条记录。

**GraphQL 操作类型**：`mutation`

**参数**：`id: String`

**返回**：布尔值（`true` 表示成功）。

**示例**：

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($id: String) {
      UserEntity__delete(id: $id)
    }
  `,
  { id: 'c5fd5e8f5ec74d189b3d1023a79508ba' }
);
```

## 批量删除 `batchDelete`

**功能**：根据多个主键 ID 批量删除记录。

**GraphQL 操作类型**：`mutation`

**参数**：`ids: [String]`

**返回**：无。

**示例**：

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($ids: [String]) {
      UserEntity__batchDelete(ids: $ids)
    }
  `,
  {
    ids: [
      'c5fd5e8f5ec74d189b3d1023a79508ba',
      'c5fd5e8f5ec74d189b3d1023a79508bc'
    ]
  }
);
```

## 批量更新 `batchUpdate`

**功能**：对满足 ID 列表的所有记录，统一更新相同的字段值。

**GraphQL 操作类型**：`mutation`

**参数**：

- `ids: [String]` – 要更新的记录 ID 列表
- `data: Map` – 要设置的公共字段及其值

**返回**：无。

**示例**：将两个产品的价格和数量统一更新。

```js
import { graphql } from '@app-utils';

await graphql(
  `
    mutation ($ids: [String], $data: Map) {
      ProductEntity__batchUpdate(ids: $ids, data: $data)
    }
  `,
  {
    ids: [
      'c5fd5e8f5ec74d189b3d1023a79508ba',
      'c5fd5e8f5ec74d189b3d1023a79508bc'
    ],
    data: {
      price: 123,
      amount: 321
    }
  }
);
```

**注意**：此操作适用于需要将某批数据的某些字段设置为相同值的场景（如批量上架、批量调价）。不支持针对每条数据设置不同的值。

## 通用注意事项

1. **选择字段**：所有返回对象的查询和变更操作都必须显式列出需要的字段，否则 GraphQL 会报错。
2. **主键字段**：更新、复制、获取、删除等操作必须提供正确的实体主键（通常为 `id`）。
3. **过滤条件语法**：`filter` 中的 `$type` 指定操作符，具体字段名和值由实体模型决定。组合条件使用 `$body` 数组。
5. **接口命名规则**：`[实体名]__[操作名]`，如 `UserEntity__findPage`，注意大小写敏感。
