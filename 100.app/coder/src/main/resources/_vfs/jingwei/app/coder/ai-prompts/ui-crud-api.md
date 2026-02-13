## 查询并返回分页数据：`findPage(query: QueryBeanInput)`

```js
import { graphql } from '@app-utils';

const { UserEntity__findPage } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findPage(query: $query) {
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
  `,
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

const { total, items } = UserEntity__findPage;
```

## 查询并返回所有数据：`findList(query: QueryBeanInput)`

```js
import { graphql } from '@app-utils';

const { UserEntity__findList } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findList(query: $query) {
        id, name
        roles {
          id
          roleName: name
        }
      }
    }
  `,
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

const [ user1, user2, ... ] = UserEntity__findList;
```

## 查询并得到第一条符合条件的数据：`findFirst(query: QueryBeanInput)`

```js
import { graphql } from '@app-utils';

const { UserEntity__findFirst } = await graphql(
  `
    query ($query: QueryBeanInput) {
      UserEntity__findFirst(query: $query) {
        id, name
        roles {
          id
          roleName: name
        }
      }
    }
  `,
  { query: { filter: {...} } }
);

const { id, name, roles } = UserEntity__findFirst;
```

## 新增数据：`save(data: Map)`

```js
import { graphql } from '@app-utils';

const { UserEntity__save } = await graphql(
  `
    mutation ($data: Map) {
      UserEntity__save(data: $data) {
        id, name, age
      }
    }
  `,
  { data: { name: 'Lily', age: 18, orgId: 'c5fd5e8f5ec74d189b3d1023a79508be', ... } }
);

const { id, name, age } = UserEntity__save;
```

注意,必须指定返回数据的选择字段。

## 更新数据：`update(data: Map)`

```js
import { graphql } from '@app-utils';

const { UserEntity__update } = await graphql(
  `
    mutation ($data: Map) {
      UserEntity__update(data: $data) {
        id, name
      }
    }
  `,
  {
    data: {
      id: 'c5fd5e8f5ec74d189b3d1023a79508ba',
      name: 'Tom',
      orgId: 'c5fd5e8f5ec74d189b3d1023a79508be'
    }
  }
);

const { id, name } = UserEntity__update;
```

注意,必须指定返回数据的选择字段,且在参数 `data` 中必须包含实体主键 `id`。

## 获取某条数据：`get(id: String)`

```js
import { graphql } from '@app-utils';

const { UserEntity__get } = await graphql(
  `
    mutation ($id: String) {
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

## 删除某条数据：`delete(id: String)`

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

## 批量删除多条数据：`batchDelete(ids: [String])`

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

## 批量更新多条数据：`batchUpdate(ids: [String], data: Map)`

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
    data: { price: 123, amount: 321 }
  }
);
```

注意,仅适用于需要同时修改某类数据的相同属性的情况。
