import * as deepseek_web from './deepseek/web';

export const webDeepseek = deepseek_web;

export function routes(fastify, { prefix }) {
  // 列出所有可用的大模型列表
  fastify.get(`${prefix}/models`, async (request, reply) => {
    reply.send({
      data: [deepseek_web.model()]
    });
  });
}
