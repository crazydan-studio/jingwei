import { parseArgs } from 'node:util';

import { readJsonFile } from '@/utils/fs';

export async function parseConfigFromArgs(args) {
  // https://nodejs.org/api/util.html#utilparseargsconfig
  const { values } = parseArgs({
    args,
    options: {
      config: { type: 'string' },
      'auth-dir': { type: 'string' }
    },
    allowPositionals: false
  });

  if (!values.config || !values['auth-dir']) {
    console.error(
      `Usage: pnpm start --config=/path/to/config.json --auth-dir=/path/to/auth/dir`
    );
    process.exit(1);
  }

  const authDir = values['auth-dir'];

  const config = await readJsonFile(values.config);

  return { ...config, authDir };
}
