import { lanuchBrowser, createPage, startAuth } from '@/browser';

import { chat } from './chat';

const AUTH_FILE = 'auth-deepseek.json';

export default function createRoutes(fastify, options) {
  //
  routeChat(fastify, options);

  // other apis ...
}

function routeChat(fastify, { prefix, authDir }) {
  fastify.post(`${prefix}/chat`, async (request, reply) => {
    await startChat(authDir);

    reply.send({ hello: 'world' });
  });
}

async function startChat(authDir) {
  const authFile = `${authDir}/${AUTH_FILE}`;
  const browser = await lanuchBrowser({ headless: true });

  await chat('你是谁？回答以 Markdown 格式输出', {
    // 打开
    open: async (url) => {
      const page = await createPage(browser, { authFile });

      await page.goto(url);

      return page;
    },
    // 认证
    auth: {
      // 关闭已开启浏览器
      before: async () => await browser.close(),
      // 开始认证
      start: async (authUrl, successUrl) =>
        await startAuth(authUrl, successUrl, { authFile }),
      // 重新开始 chat
      after: async () => await startChat(authDir)
    },
    // 退出
    exit: async () => await browser.close()
  });
}
