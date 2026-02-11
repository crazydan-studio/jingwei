/* 通过操作浏览器实现与 DeepSeek 网页端交互的接口 */

import { createContext, closeContext } from '@/utils/browser';
import { createLlmModel } from '@/utils/openai';

import {
  chat,
  waitAuth,
  clickAuthSendCodeButton,
  clickAuthLoginButton,
  clickAuthCaptchaImage,
  NEED_MORE_ACTION_REASON,
  ACTION_AUTH_PREFIX
} from './web-chat';

const AUTH_FILE = 'deepseek-web.json';
const CHAT_URL = '/chat';

export function routes(fastify, { prefix, authDir }) {
  //
  fastify.post(`${prefix}${CHAT_URL}`, async (request, reply) => {
    const { messages } = request.body;
    const content = messages.map((msg) => msg.content).join('\n\n---\n\n');

    const result = await startChat(content, { authDir });
    reply.send(result);
  });

  //
  fastify.post(`${prefix}/action`, async (request, reply) => {
    const { name } = request.query;
    const body = request.body;

    let result;
    if (name.startsWith(ACTION_AUTH_PREFIX)) {
      result = await startAuth(name, body);
    }

    reply.send(result || { success: true });
  });
}

/** @return 模型定义 AgentLlmModel (_vfs/nop/schema/ai/llm.xdef) */
export function model() {
  return createLlmModel({
    name: 'deepseek-web',
    displayName: 'DeepSeek 网页版',
    defaultModel: 'deepseek-chat',
    chatUrl: CHAT_URL,
    models: [
      {
        name: 'deepseek-chat',
        maxTokensLimit: 8192
      }
    ]
  });
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

let globalAuthPage;
let globalAuthPromise;
async function resetGlobalAuth() {
  if (globalAuthPage && !(await globalAuthPage.isClosed())) {
    const context = await globalAuthPage.context();
    await closeContext(context);
  }

  globalAuthPage = null;
  globalAuthPromise = null;
}

async function startChat(content, { authDir }) {
  await resetGlobalAuth();

  const authFile = `${authDir}/${AUTH_FILE}`;

  const context = await createContext({ authFile });
  const page = await context.newPage();

  try {
    const result = await chat(page, content);

    if (result.reason == NEED_MORE_ACTION_REASON) {
      globalAuthPage = page;
      // 异步等待认证完成
      globalAuthPromise = waitAuth(page, {
        authFile,
        complete: resetGlobalAuth
      });
    }

    return result;
  } finally {
    if (!globalAuthPage) {
      await closeContext(context);
    }
  }
}

async function startAuth(action, { phoneNumber, verifyCode, captchaPos }) {
  const page = globalAuthPage;

  if (!page) {
    if (action == ACTION_AUTH_PREFIX + 'wechat-login') {
      return;
    }
    throw new Error('还未触发登录认证，请重试');
  }

  switch (action) {
    case ACTION_AUTH_PREFIX + 'send-code': {
      return await clickAuthSendCodeButton(page, phoneNumber);
    }
    case ACTION_AUTH_PREFIX + 'login': {
      await clickAuthLoginButton(page, verifyCode);
      await globalAuthPromise;
      break;
    }
    case ACTION_AUTH_PREFIX + 'captcha': {
      await clickAuthCaptchaImage(page, captchaPos);
      break;
    }
    case ACTION_AUTH_PREFIX + 'wechat-login': {
      throw new Error('微信登录还未完成或者登录失败，请稍后重试');
    }
  }
}
