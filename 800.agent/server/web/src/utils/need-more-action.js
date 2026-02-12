import { writeJsonFile } from '@/utils/fs';

export const ACTION_URL = '/action';
export const ACTION_AUTH_PREFIX = 'auth:';
export const ACTION_AUTH_UPDATE_API_KEY = ACTION_AUTH_PREFIX + 'update-api-key';

export const ACTION_REASON_UNAUTHORIZED = 'unauthorized';

export const ACTION_FORM_INPUT_API_KEY = 'apiKey';

/** 创建「需要更多操作」的响应结构 */
export function createNeedMoreActionResponse(reason, form) {
  return {
    need_more_action: true,
    reason,
    form
  };
}

/** 处理「需要 API Key」的响应动作 */
export async function handleNeedAuthApiKeyAction({ name, data, authFile }) {
  if (name != ACTION_AUTH_UPDATE_API_KEY) {
    return;
  }

  const apiKey = data[ACTION_FORM_INPUT_API_KEY];
  if (!apiKey || apiKey.length < 8) {
    throw new Error(`提供的 API Key 无效`);
  }

  await writeJsonFile(authFile, { [ACTION_FORM_INPUT_API_KEY]: apiKey });
}

/** 创建「需要 API Key」的响应结构 */
export function createNeedAuthApiKeyResponse(title) {
  return createNeedMoreActionResponse(ACTION_REASON_UNAUTHORIZED, {
    title,
    body: {
      type: 'column',
      align: { row: 'center' },
      body: [
        {
          type: 'alert',
          color: 'warning',
          body: '还未添加 API Key 或当前的已无效，请更新 API Key'
        },
        {
          type: 'input',
          label: 'API Key',
          name: ACTION_FORM_INPUT_API_KEY
        },
        {
          type: 'button',
          label: '更新',
          color: 'primary',
          action: {
            on: 'click',
            name: ACTION_AUTH_UPDATE_API_KEY,
            data: JSON.stringify({
              [ACTION_FORM_INPUT_API_KEY]:
                '${' + ACTION_FORM_INPUT_API_KEY + '}'
            })
          }
        }
      ]
    }
  });
}
