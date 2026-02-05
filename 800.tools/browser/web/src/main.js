import { parseArgs } from 'node:util';
import Fastify from 'fastify';

import { readConfig } from '@/server/config';
import { auth } from '@/server/plugin';

import deepseek from '@/provider/deepseek/api';

// https://nodejs.org/api/util.html#utilparseargsconfig
const { values } = parseArgs({
  options: {
    config: { type: 'string' },
    'auth-dir': { type: 'string' }
  },
  allowPositionals: false
});
if (!values.config || !values['auth-dir']) {
  console.error(
    `Usage: ${process.argv[0]} ${process.argv[1]} --config=/path/to/config.json --auth-dir=/path/to/auth/dir`
  );
  process.exit(1);
}

const authDir = values['auth-dir'];
const config = await readConfig(values.config);

//
const fastify = Fastify({
  logger: true
});

//
auth(fastify, { token: config.token });
//
deepseek(fastify, { prefix: '/deepseek', authDir });

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
