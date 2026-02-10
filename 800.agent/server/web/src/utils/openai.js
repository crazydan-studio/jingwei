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
