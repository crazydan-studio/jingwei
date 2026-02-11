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

export function createNeedMoreActionResponse(reason, form) {
  return {
    need_more_action: true,
    reason,
    form
  };
}

export function createLlmModel(opts) {
  // _vfs/nop/ai/llm/default.llm.xml
  return {
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
