import { getAppConfig } from './config';
import { notification } from './msg';
import { popupNeedMoreActionForm } from './need-more-action';

const MSG_TIMEOUT = 40 * 1000;

export async function graphql(query, variables) {
  const appConfig = getAppConfig();

  const url = appConfig.api.graphql;
  // Note: 响应结果类型为 GraphQLResponseBean
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
        duration: MSG_TIMEOUT,
        keepAliveOnHover: true,
        content: 'GraphQL 请求出现异常：\n' + e.message
      });

      throw e;
    });

  if (data.data) {
    return data.data;
  }

  let msg = data.errors.map((e) => e.message).join('\n');
  const errorCode = data.extensions && data.extensions['nop-error-code'];
  if (errorCode == 'nop.err.data.need-more-action') {
    popupNeedMoreActionForm(JSON.parse(msg));

    msg = errorCode;
  } else {
    notification.error({
      duration: MSG_TIMEOUT,
      keepAliveOnHover: true,
      content: 'GraphQL 数据处理存在错误：\n' + msg
    });
  }

  throw new Error(msg);
}

export function getFileUploadUrl() {
  const appConfig = getAppConfig();

  return appConfig.api.fileUpload;
}

export function getFileDownloadUrl(fileHash) {
  const appConfig = getAppConfig();

  return appConfig.api.fileDownload + '/' + fileHash;
}
