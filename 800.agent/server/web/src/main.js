// https://fastify.dev/docs/latest/Reference/
import Fastify from 'fastify';

import { parseConfigFromArgs } from '@/server/config';
import { auth } from '@/server/plugin';

import * as llm from '@/provider/llm';

//
const args = process.argv.slice(2);
const config = await parseConfigFromArgs(args);

//
const fastify = Fastify({
  logger: true
});

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

fastify.setErrorHandler((e, request, reply) => {
  fastify.log.error(e);

  reply.send({ error: e.message });
});

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

auth(fastify, { token: config.token });

//
const llmRoutePrefix = '/llm';
llm.routes(fastify, { prefix: llmRoutePrefix });
llm.webDeepseek.routes(fastify, {
  prefix: `${llmRoutePrefix}/deepseek-web`,
  authDir: config.authDir
});

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

fastify.listen(
  { port: config.port, host: config.host },
  function (err, address) {
    if (err) {
      fastify.log.error(err);
      process.exit(1);
    }

    fastify.log.info(`Server listening on ${address}`);
  }
);
