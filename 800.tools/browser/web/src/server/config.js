import { readFile } from 'node:fs/promises';

export async function readConfig(file) {
  const data = await readFile(file, 'utf8');

  return JSON.parse(data);
}
