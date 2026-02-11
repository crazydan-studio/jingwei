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
      showGraphQLError('GraphQL 请求出现异常', e.message);

      throw e;
    });

  if (data.data) {
    return data.data;
  }

  let msg = data.errors.map((e) => e.message).join('\n');
  const errorCode = data.extensions && data.extensions['nop-error-code'];

  if (errorCode == appConfig.vars.needMoreActionCode) {
    popupNeedMoreActionForm(JSON.parse(msg), { graphql });

    msg = errorCode;
  } else {
    showGraphQLError('GraphQL 数据处理出现异常', msg);
    // popupNeedMoreActionForm({"title":"登录 DeepSeek 网页版","body":{"type":"row","align":{"column":"center"},"body":[{"type":"column","body":[{"type":"input","label":"手机号","name":"phoneNumber","prefix":{"type":"text","value":"+86"}},{"type":"input","label":"验证码","name":"verifyCode","suffix":{"type":"button","label":"获取验证码","action":{"on":"click","name":"auth:send-code","data":"{\"phoneNumber\":\"${phoneNumber}\"}"}}},{"type":"button","label":"登录","color":"primary","action":{"on":"click","name":"auth:login","data":"{\"verifyCode\":\"${verifyCode}\"}"}}]},{"type":"column","align":{"row":"center"},"body":[{"type":"text","value":"微信扫码登录"},{"type":"image","src":"https://open.weixin.qq.com/connect/qrcode/xxxx","width":320},{"type":"button","label":"确认微信已登录","color":"info","action":{"on":"click","name":"auth:wechat-login"}}]}]},"graphql":"mutation($action:String,$data:String){LlmAgent__needMoreAction(action:$action,provider:\"deepseek-web\",data:$data)}"}, {graphql});
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

function showGraphQLError(title, msg) {
  notification.error({
    title,
    duration: MSG_TIMEOUT,
    keepAliveOnHover: true,
    content: msg
  });
}
