import { readJsonFile } from '@/utils/fs';
import {
  createNeedAuthApiKeyResponse,
  ACTION_FORM_INPUT_API_KEY
} from '@/utils/need-more-action';

export const CHAT_URL = '/chat';

export async function sendChat({ url, data, authFile, unauthActionTitle }) {
  const auth = await readJsonFile(authFile);
  let apiKey = auth[ACTION_FORM_INPUT_API_KEY];

  if (apiKey) {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${apiKey}`
      },
      body: JSON.stringify(data)
    });

    if (response.ok) {
      return await response.json();
    } else if (response.status == 401) {
      apiKey = null;
    } else {
      throw new Error(`HTTP - ${response.status}: ${await response.text()}`);
    }
  }

  if (!apiKey) {
    return createNeedAuthApiKeyResponse(unauthActionTitle);
  }
}

export function createChatResponse(content) {
  return {
    object: 'chat.completion',
    choices: [
      {
        index: 0,
        finish_reason: 'stop',
        message: {
          role: 'assistant',
          content: content
        }
      }
    ]
  };
}

export function createLlmModel(opts) {
  // _vfs/nop/ai/llm/default.llm.xml
  return {
    chatUrl: CHAT_URL,
    ...opts,
    request: {
      seedPath: 'options.seed',
      topPPath: 'top_p',
      temperaturePath: 'temperature',
      stopPath: 'stop',
      maxTokensPath: 'max_tokens',
      ...(opts.request || {})
    },
    response: {
      contentPath: 'choices.0.message.content',
      rolePath: 'choices.0.message.role',
      reasoningContentPath: 'choices.0.message.reasoning_content',
      promptTokensPath: 'usage.prompt_tokens',
      completionTokensPath: 'usage.completion_tokens',
      totalTokensPath: 'usage.total_tokens',
      statusPath: 'done',
      errorPath: 'error',
      toolCallsPath: 'choices.0.message.tool_calls',
      ...(opts.response || {})
    }
  };
}
