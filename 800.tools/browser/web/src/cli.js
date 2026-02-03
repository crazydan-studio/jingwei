import { cac } from 'cac';

import * as deepseek from './provider/deepseek/cli';

const cli = cac('browser');

deepseek.createCommand(cli);

//
cli.help();

try {
  cli.parse();
} catch (e) {
  if (e.message.includes('missing required args')) {
    cli.outputHelp();
    process.exit(1);
  } else {
    throw e;
  }
}
