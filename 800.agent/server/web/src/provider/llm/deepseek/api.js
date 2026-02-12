import { createLlmModel } from '@/utils/openai';
import { bindChatAndActionRoutes } from '@/provider/llm/api-routes';

const AUTH_FILE = 'deepseek.json';

const DISPLAY_NAME = 'DeepSeek';
const API_BASE_URL = 'https://api.deepseek.com';
const CHAT_API_COMPLETION = '/chat/completions';

export function routes(fastify, { prefix, authDir }) {
  const authFile = `${authDir}/${AUTH_FILE}`;
  const chatUrl = API_BASE_URL + CHAT_API_COMPLETION;

  bindChatAndActionRoutes(fastify, {
    prefix,
    chatUrl,
    authFile,
    unauthActionTitle: DISPLAY_NAME + ' API 接口认证'
  });
}

/** @return 模型定义 AgentLlmModel (_vfs/nop/schema/ai/llm.xdef) */
export function model() {
  return createLlmModel({
    name: 'deepseek',
    displayName: DISPLAY_NAME,
    defaultModel: 'deepseek-chat',
    models: [
      {
        name: 'deepseek-chat',
        maxTokensLimit: 8192
      }
    ]
  });
}
