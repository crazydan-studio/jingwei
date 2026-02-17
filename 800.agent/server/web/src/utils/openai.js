import {
  readJsonFile,
  readJsonLinesFile,
  appendToJsonLinesFile
} from '@/utils/fs';
import {
  createNeedAuthApiKeyResponse,
  ACTION_FORM_INPUT_API_KEY
} from '@/utils/need-more-action';

export const CHAT_URL = '/chat';

export const KEY_KEEP_SESSION = 'keep_session';
export const KEY_SESSION_ID = 'session_id';

export async function sendChat({
  url,
  data,
  sessionDir,
  authFile,
  unauthActionTitle
}) {
  const auth = await readJsonFile(authFile);
  let apiKey = auth[ACTION_FORM_INPUT_API_KEY];

  if (apiKey) {
    const sessionId = data[KEY_KEEP_SESSION]
      ? data[KEY_SESSION_ID] || generateSessionId()
      : null;
    const sessionFile = sessionId ? `${sessionDir}/${sessionId}` : null;

    // { "content": "You are a helpful assistant", "role": "system" }
    const messages = [];
    if (sessionFile) {
      messages = await readJsonLinesFile(sessionFile);
    }
    data.messages.forEach((msg) => {
      messages.push(JSON.stringify(msg));
    });

    delete data.messages;

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${apiKey}`
      },
      body:
        `{"messages":[${messages.join(',')}],` +
        JSON.stringify(data).substring(1)
    });

    if (response.ok) {
      const result = await response.json();
      if (sessionFile) {
        const msg = result.choices[0].message;
        delete msg.reasoning_content;

        await appendToJsonLinesFile(sessionFile, msg);
      }

      result[KEY_SESSION_ID] = sessionId;

      return result;
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

export function createChatResponse(content, { sessionId }) {
  return {
    object: 'chat.completion',
    [KEY_SESSION_ID]: sessionId,
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

export function generateSessionId() {
  return '';
}
