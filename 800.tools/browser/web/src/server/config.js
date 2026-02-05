import { readFile } from 'node:fs/promises';
import { parseArgs } from 'node:util';

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

  const data = await readFile(values.config, 'utf8');
  const config = JSON.parse(data);

  return { ...config, authDir };
}
