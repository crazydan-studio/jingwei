import * as deepseek_web from './deepseek/web';
import * as deepseek_api from './deepseek/api';
import * as bailian_api from './bailian/api';

export const webDeepseek = deepseek_web;
export const deepseek = deepseek_api;
export const bailian = bailian_api;

export function routes(fastify, { prefix }) {
  // 列出所有可用的大模型列表
  fastify.get(`${prefix}/models`, async (request, reply) => {
    reply.send({
      data: [deepseek_api.model(), deepseek_web.model(), bailian_api.model()]
    });
  });
}
