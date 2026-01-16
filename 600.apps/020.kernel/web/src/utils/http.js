import { getAppConfig } from './config';
import { notification } from './msg';

export async function graphql(query, variables) {
  const appConfig = getAppConfig();

  const url = appConfig.api.graphql;
  const data = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      query,
      variables
    })
  })
    .then((resp) => {
      if (!resp.ok) {
        throw new Error(resp.status + ' - ' + resp.statusText);
      }
      return resp.json();
    })
    .catch((e) => {
      notification.error({
        content: 'GraphQL 请求出现异常：\n' + e.message
      });

      throw e;
    });

  if (!data.data) {
    const msg = data.errors.map((e) => e.message).join('\n');
    notification.error({
      content: 'GraphQL 数据处理存在错误：\n' + msg
    });

    throw new Error(msg);
  }

  return data.data;
}
