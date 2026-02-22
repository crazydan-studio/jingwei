## 获取字典项

### `getDictOptions(dictName: string): [DictOption]`

通过 `@app-utils` 提供的 `getDictOptions` 函数可以获取指定名字的字典项列表。

```js
import { getDictOptions } from '@app-utils';

const options = await getDictOptions('user/status');
const { code, value, label, description } = options[0];
```

**参数说明**

- `dictName: string` – 字典名，如 `user/status`。

## 打开应用

### `openApp(code: string): void`

要打开一个指定应用，可使用 `@app-utils` 提供的 `openApp` 函数。该函数接收一个应用编码（`code`）作为参数，并负责导航至对应应用。

```js
import { openApp } from '@app-utils';

openApp('user-management'); // 示例：打开用户管理应用
```

**参数说明**

- `code: string` – 应用编码。

**注意事项**

- `openApp` 通常用于跨应用跳转，若当前已在目标应用则可能刷新或重新加载。

## 开发应用

### GraphQL IDE

#### `openGraphiQL(): void`

在开发环境中，可调用 `openGraphiQL` 快速打开 GraphQL 交互式调试工具（GraphiQL），方便测试接口和查看 Schema。

```js
import { openGraphiQL } from '@app-utils';

openGraphiQL(); // 在新标签页或内嵌视图中打开 GraphiQL
```

### 页面预览

暂不支持。

## 页面消息

从 `@app-utils` 中可导入并使用 **dialog**、**message**、**modal** 和 **notification** 消息组件：

```js
import { message, notification, dialog, modal } from '@app-utils';
```

### 对话框（dialog）

提供四种预设类型：`error`、`info`、`success`、`warning`。

**方法签名**

```ts
dialog.error(options: DialogOptions): DialogReactive;
dialog.info(options: DialogOptions): DialogReactive;
dialog.success(options: DialogOptions): DialogReactive;
dialog.warning(options: DialogOptions): DialogReactive;
```

**示例**

```js
dialog.success({
  title: '成功',
  content: '操作已完成',
  positiveText: '确定',
  onPositiveClick: () => {
    console.log('确定');
  }
});
```

**返回值**

每个方法返回一个对话框实例，包含 `destroy` 等方法。

### 信息（message）

`message` 提供五种类型：`error`、`info`、`loading`、`success`、`warning`。

**方法签名**

```ts
message.error(content: string | (() => VNodeChild), option?: MessageOption): void;
message.info(content: string | (() => VNodeChild), option?: MessageOption): void;
message.loading(content: string | (() => VNodeChild), option?: MessageOption): void;
message.success(content: string | (() => VNodeChild), option?: MessageOption): void;
message.warning(content: string | (() => VNodeChild), option?: MessageOption): void;
```

**示例**

```js
message.success('操作成功');
message.error('操作失败', { duration: 3000, closable: true });
message.loading('处理中...', { duration: 0 }); // duration=0 表示不自动关闭
```

**参数说明**

- `content: string | (() => VNodeChild)` – 消息文本或返回 VNode 的函数。
- `option?: MessageOption` – 可选配置，如 `duration`、`closable` 等。

### 模态框（modal）

通过 `modal.create(options)` 创建模态框，返回一个控制器对象，可主动关闭模态框。

**方法签名**

```ts
const win = modal.create(options: ModalOptions): { destroy: () => void };
win.destroy(); // 手动关闭模态框
```

**示例**

```js
const modalWin = modal.create({
  title: '编辑用户',
  content: () => h('div', '自定义内容'),
  preset: 'card',
  onPositiveClick: () => {
    /* 确认逻辑 */
  }
});

// 主动关闭模态框
setTimeout(() => modalWin.destroy(), 5000);
```

**注意事项**

- 与 `dialog` 不同，`modal` 更灵活，可嵌入复杂内容。
- `destroy()` 调用后会立即移除模态框，且不会触发任何确认回调。

### 通知（notification）

`notification` 提供四种类型：`error`、`info`、`success`、`warning`。

**方法签名**

```ts
notification.error(options: NotificationOption): void;
notification.info(options: NotificationOption): void;
notification.success(options: NotificationOption): void;
notification.warning(options: NotificationOption): void;
```

**示例**

```js
notification.success({
  title: '成功',
  content: '数据更新完成',
  duration: 3000,
  meta: '刚刚'
});

notification.error({
  title: '错误',
  content: '网络连接失败',
  keepAliveOnHover: true
});
```

**注意事项**

- 通知默认会在 `duration` 后自动关闭，设置 `duration: 0` 则需手动关闭。

## 文件上传与下载

暂不支持。

## 数据操作接口

所有与业务模型相关的增删改查操作均通过 **GraphQL** 接口完成，并使用统一的 `graphql` 函数进行调用。

### `graphql(query: string, variables?: Record<string, any>): Promise<any>`

执行 GraphQL 查询或变更。

**参数**

- `query` (string) – GraphQL 请求体（支持多行字符串）。
- `variables` (object, 可选) – 查询变量，会以 `Map` 类型传递给后端。

**返回值**

- Promise，解析为服务端返回的数据对象，字段名与查询中定义的别名一致。

**使用示例**

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
  { data: { name: 'Lily', age: 18 } }
);

console.log(UserEntity__save.id); // 输出保存后返回的 id
```

### 使用要求与注意事项

- **禁止拦截 graphql 的调用异常**
  - 不允许使用 `.catch()` 或 `try/catch` 包裹 `graphql` 调用来处理错误。
  - 错误将由框架统一处理（如全局错误提示）。
  - 若需在操作结束后执行清理工作，应使用 `finally` 块。

  ```js
  // ❌ 错误示例
  try {
    await graphql(...);
  } catch (err) {
    // 禁止自行处理错误
  }

  // ✅ 正确示例
  let loading = true;
  try {
    await graphql(...);
  } finally {
    loading = false; // 关闭加载遮罩或其他清理
  }
  ```

- **变更操作必须配合加载遮罩**
  - 对于增加、修改、删除等变更操作（通常是 mutation），需要在操作开始前显示加载遮罩，操作结束后关闭。
  - 加载遮罩推荐使用 Naive UI 的 `NSpin` 组件，将其包裹在需要屏蔽的区域外层，并通过一个响应式变量控制其 `show` 属性。

  ```vue
  <template>
    <n-spin :show="loading">
      <!-- 变更操作涉及的区域 -->
      <div>...</div>
    </n-spin>
  </template>

  <script setup>
  import { ref } from 'vue';
  import { graphql } from '@app-utils';

  const loading = ref(false);

  async function handleSave() {
    loading.value = true;
    try {
      await graphql(
        `
          mutation ($data: Map) {
            UserEntity__save(data: $data) {
              id
            }
          }
        `,
        { data: { name: 'Tom' } }
      );
    } finally {
      loading.value = false;
    }
  }
  </script>
  ```

  - 若操作涉及多个独立区域，可考虑使用全局加载指示器（如顶部进度条），但推荐按区域细粒度控制。

- **GraphQL 查询变量类型**
  - 变量对象中可使用 `Map` 类型（对应任意 JSON 对象），服务端会进行解析。
  - 复杂筛选条件通常也通过 `Map` 传递，具体格式需参考业务文档。
- **返回值结构**
  - `graphql` 返回的 Promise 解析为服务端返回的数据对象，其顶层字段名与 query 中定义的操作名一致（如 `UserEntity__save`）。
  - 若需要多个操作的结果，可通过别名区分。
