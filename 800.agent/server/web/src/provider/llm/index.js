export * as webDeepseek from './deepseek/web';

export function routes(fastify, { prefix }) {
  // 列出所有可用的大模型列表
  fastify.get(`${prefix}/models`, async (request, reply) => {
    reply.send([webDeepseek.model()]);
  });
}
