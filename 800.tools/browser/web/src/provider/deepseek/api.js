import { createContext, closeContext } from '@/browser';

import {
  chat,
  waitAuth,
  clickAuthSendCodeButton,
  clickAuthLoginButton
} from './chat';

const AUTH_FILE = 'deepseek.json';

export default function createRoutes(fastify, { prefix, authDir }) {
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
  if (globalAuthPage) {
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
  } catch (e) {
    return createErrorResponse(e);
  } finally {
    if (!globalAuthPage) {
      await closeContext(context);
    }
  }
}

async function startAuth(action, { phoneNumber, verifyCode }) {
  const page = globalAuthPage;

  try {
    if (!!page) {
      switch (action) {
        case 'send-code': {
          await clickAuthSendCodeButton(page, phoneNumber);
          break;
        }
        case 'login': {
          await clickAuthLoginButton(page, verifyCode);
          await globalAuthPromise;
          break;
        }
      }
    }

    return { data: true };
  } catch (e) {
    return createErrorResponse(e);
  }
}

function createErrorResponse(e) {
  console.error(e);
  return { errors: [{ message: e.message }] };
}
