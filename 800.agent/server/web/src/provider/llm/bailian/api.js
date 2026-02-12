import { createLlmModel } from '@/utils/openai';
import { bindChatAndActionRoutes } from '@/provider/llm/api-routes';

const AUTH_FILE = 'bailian.json';

const DISPLAY_NAME = '阿里云百炼';
const API_BASE_URL = 'https://dashscope.aliyuncs.com/compatible-mode/v1';
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
    name: 'bailian',
    displayName: DISPLAY_NAME,
    defaultModel: 'qwen-coder-plus',
    models: [
      {
        name: 'qwen-coder-plus',
        maxTokensLimit: 8192
      }
    ]
  });
}
