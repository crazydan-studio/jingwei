import { AUTH_TIMEOUT } from '@/utils/browser';
import {
  createChatResponse,
  createNeedMoreActionResponse
} from '@/utils/openai';

const BASE_URL = 'https://chat.deepseek.com';

const CHAT_URL = BASE_URL;
const AUTH_URL = `${BASE_URL}/sign_in`;
const CHAT_API_COMPLETION = '/api/v0/chat/completion';
const CHAT_API_DELETE_SESSION = '/api/v0/chat_session/delete';
const CHAT_TIMEOUT = 20 * 60 * 1000;

export const ACTION_AUTH_PREFIX = 'auth:';
export const NEED_MORE_ACTION_REASON = 'unauthorized';

export async function chat(page, content) {
  //console.log(`[DeepSeek] Opening ${CHAT_URL} ...`);
  await page.goto(CHAT_URL);

  if ((await page.url()) == AUTH_URL) {
    const form = await createLoginForm(page);

    return createNeedMoreActionResponse(NEED_MORE_ACTION_REASON, form);
  }

  //
  await enableDeepSeek(page, true);

  //console.log('[DeepSeek] Starting chat session ...');
  const session = await startChatSession(page, content);

  try {
    //console.log('[DeepSeek] Deleting chat session ...');
    await deleteChatSession(page, session.id);
  } catch (e) {
    console.error(e);
  }

  return createChatResponse(session.answer);
}

/** 等待认证结束 */
export async function waitAuth(page, { authFile, complete }) {
  const context = await page.context();

  try {
    await page.waitForURL(CHAT_URL, { timeout: AUTH_TIMEOUT });

    await context.storageState({ path: authFile });
  } catch (e) {
    console.error(e);
  } finally {
    await complete();
  }
}

/** 点击认证页面中的验证码发送按钮 */
export async function clickAuthSendCodeButton(page, phoneNumber) {
  if (!/^1[3-9]\d{9}$/.test(phoneNumber || '')) {
    throw new Error('输入的手机号无效');
  }

  const locator = await page.locator('.ds-sign-in-form__main');

  await locator.getByPlaceholder('Phone number').fill(phoneNumber);

  await locator.getByText('Send code').click();

  // 二次验证
  const captchaLocator = page.locator('#sm-captcha');
  const captchaTips = await captchaLocator
    .locator('.shumei_captcha_slide_tips')
    .innerText();

  const captcha = await captchaLocator.locator('img');
  await captcha.waitFor({ state: 'visible' });

  const captchaSize = await captcha.boundingBox();
  const captchaUrl = await captcha.getAttribute('src');

  return createNeedMoreActionResponse(NEED_MORE_ACTION_REASON, {
    title: '请按提示选择正确的图形',
    body: {
      type: 'column',
      align: { row: 'center' },
      body: [
        {
          type: 'text',
          value: captchaTips
        },
        {
          type: 'image',
          src: captchaUrl,
          preview: false,
          width: captchaSize.width,
          height: captchaSize.height,
          action: {
            on: 'click',
            name: ACTION_AUTH_PREFIX + 'captcha',
            data: JSON.stringify({
              captchaPos: { x: '${event.offsetX}', y: '${event.offsetY}' }
            })
          }
        }
      ]
    }
  });
}

/** 点击认证页面中的登录按钮 */
export async function clickAuthLoginButton(page, verifyCode) {
  if (!/^\d{3,9}$/.test(verifyCode || '')) {
    throw new Error('输入的验证码无效');
  }

  const locator = await page.locator('.ds-sign-in-form__main');

  await locator.getByPlaceholder('Code').fill(verifyCode);

  await locator.getByText('Log in').click();
}

/** 点击认证页面中的验证码图片 */
export async function clickAuthCaptchaImage(page, position) {
  let { x, y } = position || {};
  try {
    x = parseFloat(x);
    y = parseFloat(y);
  } catch (e) {
    throw new Error(`无效的坐标值 [${x}, ${y}]：${e.message}`);
  }

  const captcha = await page.locator('#sm-captcha').locator('img');

  await captcha.click({ x, y });
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

async function startChatSession(page, content) {
  const responsePromise = page.waitForResponse(
    (response) =>
      response.url().includes(CHAT_API_COMPLETION) && response.status() === 200,
    { timeout: CHAT_TIMEOUT }
  );

  //console.log('[DeepSeek] Filling chat content ...');
  await fillChatContent(page, content);

  //console.log('[DeepSeek] Clicking chat submit button ...');
  await clickChatSubmitButton(page);

  // https://chat.deepseek.com/a/chat/s/xx-xx-xx-xx
  await page.waitForURL(`${CHAT_URL}/a/chat/s/*`);
  const url = await page.url();

  //console.log('[DeepSeek] Fetching chat answer ...');
  const response = await responsePromise;
  const responseText = await response.text();

  return {
    id: url.replace(/^.+\/a\/chat\/s\/(.+)/, '$1'),
    answer: parseChatAnswer(responseText)
  };
}

async function deleteChatSession(page, sessionId) {
  const responsePromise = page.waitForResponse(
    (response) =>
      response.url().includes(CHAT_API_DELETE_SESSION) &&
      (response.ok() || response.status() > 500),
    { timeout: 10 * 1000 }
  );

  // 更多按钮
  await page
    .locator(`a[href="/a/chat/s/${sessionId}"]`)
    .locator('.ds-icon-button')
    .click();

  // 下拉操作菜单
  let locator = page
    .locator('.ds-dropdown-menu-option.ds-dropdown-menu-option--error')
    .getByText('Delete');
  await clickIfVisible(locator);

  // 弹窗确认
  locator = page.locator(
    '.ds-atom-button.ds-basic-button.ds-basic-button--danger'
  );
  await clickIfVisible(locator);

  //
  await page.waitForURL(CHAT_URL);

  await responsePromise;
}

async function fillChatContent(page, content) {
  const locator = page.getByPlaceholder('Message DeepSeek');

  await locator.fill(content);
}

async function clickChatSubmitButton(page) {
  const locator = page.locator(
    'svg path[d="M8.3125 0.981587C8.66767 1.0545 8.97902 1.20558 9.2627 1.43374C9.48724 1.61438 9.73029 1.85933 9.97949 2.10854L14.707 6.83608L13.293 8.25014L9 3.95717V15.0431H7V3.95717L2.70703 8.25014L1.29297 6.83608L6.02051 2.10854C6.26971 1.85933 6.51277 1.61438 6.7373 1.43374C6.97662 1.24126 7.28445 1.04542 7.6875 0.981587C7.8973 0.94841 8.1031 0.956564 8.3125 0.981587Z"]'
  );

  await clickIfVisible(locator);
}

async function enableDeepSeek(page, enabled) {
  const locator = page
    .locator('.ds-atom-button.ds-toggle-button')
    .filter(
      enabled
        ? {
            hasNot: page.locator('.ds-toggle-button--selected')
          }
        : {
            has: page.locator('.ds-toggle-button--selected')
          }
    )
    .getByText('DeepThink');

  await clickIfVisible(locator);
}

async function clickIfVisible(locator) {
  if (await locator.isVisible()) {
    await locator.click();
  }
}

function parseChatAnswer(text) {
  const messages = [];

  const lines = text.split('\n');
  for (let line of lines) {
    // event: title
    // data: {"content":"AI助手DeepSeek自我介绍"}
    if (!line.startsWith('data: ')) {
      continue;
    }

    const data = JSON.parse(line.replace(/^data: /, ''));

    // data: {"p":"response/fragments","o":"APPEND","v":[{"id":2,"type":"RESPONSE","content":"你好","references":[],"stage_id":1}]}
    // data: {"p":"response/fragments/-1/content","v":"！"}
    // data: {"v":"我是"}
    // data: {"p":"response","o":"BATCH","v":[{"p":"accumulated_token_usage","v":250},{"p":"quasi_status","v":"FINISHED"}]}
    // data: {"p":"response/status","o":"SET","v":"FINISHED"}
    if (Array.isArray(data.v) && data.v[0].type == 'RESPONSE') {
      const msg = data.v[0].content || '';
      messages.push(msg);
    } //
    else if (data.v == 'FINISHED') {
      break;
    } //
    else if (typeof data.v == 'string' && messages.length > 0) {
      messages.push(data.v);
    }
  }

  return messages.join('');
}

async function createLoginForm(page) {
  const iframe = page.locator('#wxLogin > iframe');
  const wxBaseUrl = (await iframe.getAttribute('src')).replace(
    /^(https:\/\/[^\/]+).+/,
    '$1'
  );

  const qrcode = iframe.contentFrame().locator('img').first();
  const qrcodeUrl = wxBaseUrl + (await qrcode.getAttribute('src'));

  return {
    title: '登录 DeepSeek 网页版',
    body: {
      type: 'row',
      align: { column: 'center' },
      body: [
        {
          type: 'column',
          body: [
            {
              type: 'input',
              label: '手机号',
              name: 'phoneNumber',
              prefix: {
                type: 'text',
                value: '+86'
              }
            },
            {
              type: 'input',
              label: '验证码',
              name: 'verifyCode',
              suffix: {
                type: 'button',
                label: '获取验证码',
                action: {
                  on: 'click',
                  name: ACTION_AUTH_PREFIX + 'send-code',
                  data: JSON.stringify({ phoneNumber: '${phoneNumber}' })
                }
              }
            },
            {
              type: 'button',
              label: '登录',
              color: 'primary',
              action: {
                on: 'click',
                name: ACTION_AUTH_PREFIX + 'login',
                data: JSON.stringify({ verifyCode: '${verifyCode}' })
              }
            }
          ]
        },
        {
          type: 'column',
          align: { row: 'center' },
          body: [
            {
              type: 'text',
              value: '微信扫码登录'
            },
            {
              type: 'image',
              src: qrcodeUrl,
              width: 320
            },
            {
              type: 'button',
              label: '确认微信已登录',
              color: 'info',
              action: {
                on: 'click',
                name: ACTION_AUTH_PREFIX + 'wechat-login'
              }
            }
          ]
        }
      ]
    }
  };
}
