import Fastify from 'fastify';

import { parseConfigFromArgs } from '@/server/config';
import { auth } from '@/server/plugin';

import deepseek from '@/provider/deepseek/api';

//
const args = process.argv.slice(2);
const config = await parseConfigFromArgs(args);

//
const fastify = Fastify({
  logger: true
});

//
auth(fastify, { token: config.token });
//
deepseek(fastify, { prefix: '/deepseek', authDir: config.authDir });

//
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
