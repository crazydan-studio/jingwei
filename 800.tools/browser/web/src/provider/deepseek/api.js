import { lanuchBrowser, createPage, startAuth } from '../../utils/browser';

import { chat } from './chat';

const AUTH_FILE = 'auth-deepseek.json';

export default function createRoutes(fastify, prefix) {
  //
  routeChat(fastify, prefix);

  // other apis ...
}

function routeChat(fastify, prefix) {
  fastify.post(`${prefix}/chat`, async (request, reply) => {
    await startChat();

    reply.send({ hello: 'world' });
  });
}

async function startChat() {
  const browser = await lanuchBrowser({ headless: true });

  await chat('你是谁？回答以 Markdown 格式输出', {
    // 打开
    open: async (url) => {
      const page = await createPage(browser, { authFile: AUTH_FILE });

      await page.goto(url);

      return page;
    },
    // 认证
    auth: {
      // 关闭已开启浏览器
      before: async () => await browser.close(),
      // 开始认证
      start: async (authUrl, successUrl) =>
        await startAuth(authUrl, successUrl, { authFile: AUTH_FILE }),
      // 重新开始 chat
      after: startChat
    },
    // 退出
    exit: async () => await browser.close()
  });
}
