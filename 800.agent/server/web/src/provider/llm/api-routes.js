import { CHAT_URL, sendChat } from '@/utils/openai';
import {
  ACTION_URL,
  handleNeedAuthApiKeyAction
} from '@/utils/need-more-action';

export function bindChatAndActionRoutes(
  fastify,
  { prefix, chatUrl, authFile, unauthActionTitle }
) {
  //
  fastify.post(`${prefix}${CHAT_URL}`, async (request, reply) => {
    const data = request.body;

    const result = await sendChat({
      url: chatUrl,
      data,
      authFile,
      unauthActionTitle
    });

    reply.send(result);
  });

  //
  fastify.post(`${prefix}${ACTION_URL}`, async (request, reply) => {
    const { name } = request.query;
    const data = request.body;

    let result = await handleNeedAuthApiKeyAction({ name, data, authFile });

    reply.send(result || { success: true });
  });
}
