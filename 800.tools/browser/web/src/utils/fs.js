import { mkdir, writeFile, readFile, access } from 'node:fs/promises';
import { dirname } from 'node:path';

/** 读取 json 文件 */
export async function readJsonFile(file) {
  const json = await readFile(file, 'utf8');

  return JSON.parse(json);
}

/** 初始化 json 文件：若文件不存在，则创建并初始化执行值，否则不做处理 */
export async function initJsonFile(file, json) {
  if (await checkExists(file)) {
    return;
  }

  const dir = dirname(file);

  await mkdir(dir, { recursive: true });

  await writeFile(file, JSON.stringify(json || {}), 'utf8');
}

/** 检查文件是否存在 */
export async function checkExists(path) {
  try {
    await access(path);
    return true;
  } catch {
    return false;
  }
}
