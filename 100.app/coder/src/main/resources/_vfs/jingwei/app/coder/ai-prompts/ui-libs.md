## 打开应用

直接调用依赖包 `@app-utils` 中的函数 `openApp`，并以应用的 `code` 作为该函数的参数即可：

```js
import { openApp } from '@app-utils';

openApp('<app-code>');
```

## 开发应用

### GraphQL IDE

调用依赖包 `@app-utils` 中的函数 `openGraphiQL` 即可：

```js
import { openGraphiQL } from '@app-utils';

openGraphiQL();
```

### 页面预览

暂不支持。

## 页面消息

直接从依赖包 `@app-utils` 中导入各种形式的消息：

```js
import { message, notification, dialog, modal } from '@app-utils';
```

- `dialog`（对话框）的各种类型（错误、信息、成功、告警）消息的配置与 Naive UI Dialog 组件的 `DialogOptions` 相同：

```js
dialog.error/info/success/warning(options: DialogOptions);
```

- `message`（信息）的各种类型（错误、信息、加载、成功、告警）消息的配置与 Naive UI Message 组件的 `MessageOption` 相同：

```js
message.error/info/loading/success/warning(content: string | (() => VNodeChild), option?: MessageOption);
```

- `modal`（模态框）的配置与 Naive UI Modal 组件的 `ModalOptions` 相同：

```js
const win = modal.create(options: ModalOptions);
win.destroy(); // 主动关闭模态框
```

- `notification`（通知）的各种类型（错误、信息、成功、告警）消息的配置与 Naive UI Notification 组件的 `NotificationOption` 相同：

```js
notification.error/info/success/warning(options: NotificationOption);
```

## 文件上传与下载

暂不支持。

## 数据操作接口

与业务模型相关的增删改查等各类数据操作均为 GraphQL 接口，并始终由依赖包 `@app-utils`
中的函数 `graphql` 做统一的调用处理：

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
  { data: { name: 'Lily', age: 18, ... } }
);

const { id, name, age } = UserEntity__save;
```

使用 `graphql` 函数必须符合以下要求：

- **禁止拦截 graphql 的调用异常**，只允许在 `finally` 中做收尾工作，不能添加 `catch` 处理。
- 对于增加、修改、删除等变更操作，需要在操作开始前对涉及变更的区域的视图显示加载遮罩，并在操作结束后关闭遮罩
  - 加载遮罩由 Naive UI 的 `NSpin` 组件实现
