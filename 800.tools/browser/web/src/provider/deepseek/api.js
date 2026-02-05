import { createContext } from '@/browser';

import { chat } from './chat';

const AUTH_FILE = 'deepseek.json';

export default function createRoutes(fastify, { prefix, authDir }) {
  //
  fastify.post(`${prefix}/chat`, async (request, reply) => {
    const result = await startChat({ authDir });

    reply.send(result);
  });

  // other apis ...
}

/** @return 返回结果类型为 GraphQLResponseBean */
async function startChat({ authDir }) {
  const authFile = `${authDir}/${AUTH_FILE}`;

  const context = await createContext({ authFile });
  const page = await context.newPage();

  try {
    return await chat(page, '你是谁？回答以 Markdown 格式输出');
  } catch (e) {
    try {
      await context.close();
    } catch (e) {
      console.error(e);
    }

    return { errors: [{ message: e.message }] };
  }
}
