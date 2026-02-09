/* 通过操作浏览器实现与 DeepSeek 网页端交互的接口 */

import { createContext, closeContext } from '@/utils/browser';

import {
  chat,
  waitAuth,
  clickAuthSendCodeButton,
  clickAuthLoginButton,
  clickAuthCaptchaImage
} from './web-chat';

const AUTH_FILE = 'deepseek-web.json';

export function routes(fastify, { prefix, authDir }) {
  //
  fastify.post(`${prefix}/chat`, async (request, reply) => {
    const { content } = request.body;

    const result = await startChat(content, { authDir });
    reply.send(result);
  });

  //
  fastify.post(`${prefix}/auth/action/:action`, async (request, reply) => {
    const { action } = request.params;
    const body = request.body;

    const result = await startAuth(action, body);
    reply.send(result);
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

/** @return 返回结果类型为 GraphQLResponseBean */
async function startChat(content, { authDir }) {
  await resetGlobalAuth();

  const authFile = `${authDir}/${AUTH_FILE}`;

  const context = await createContext({ authFile });
  const page = await context.newPage();

  try {
    const result = await chat(page, content);

    if (!result.data) {
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

  if (!!page) {
    switch (action) {
      case 'send-code': {
        return await clickAuthSendCodeButton(page, phoneNumber);
      }
      case 'login': {
        await clickAuthLoginButton(page, verifyCode);
        await globalAuthPromise;
        break;
      }
      case 'captcha': {
        await clickAuthCaptchaImage(page, captchaPos);
        break;
      }
    }
  }

  return { data: true };
}
