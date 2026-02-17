import os from 'node:os';
import {
  mkdir,
  writeFile,
  readFile,
  appendFile,
  access,
  open
} from 'node:fs/promises';
import { dirname } from 'node:path';

/** 读取 json 文件 */
export async function readJsonFile(file, defaultValue = {}) {
  if (!(await checkExists(file))) {
    return defaultValue;
  }

  const json = await readFile(file, 'utf8');

  return json ? JSON.parse(json) : defaultValue;
}

/** 初始化 json 文件：若文件不存在，则创建并初始化执行值，否则不做处理 */
export async function initJsonFile(file, json) {
  if (!(await checkExists(file))) {
    await writeJsonFile(file, json);
  }
}

/** 写入 json 文件 */
export async function writeJsonFile(file, json) {
  const dir = dirname(file);
  await mkdir(dir, { recursive: true });

  await writeFile(file, JSON.stringify(json || {}), 'utf8');
}

/** 读取多行 json 文件得到 json 列表 */
export async function readJsonLinesFile(file) {
  if (!(await checkExists(file))) {
    return [];
  }

  const lines = [];
  const fd = await open(file);
  for await (const line of fd.readLines()) {
    lines.push(line);
  }

  return lines;
}

/** 向多行 json 文件追加 json */
export async function appendToJsonLinesFile(file, json) {
  const dir = dirname(file);
  await mkdir(dir, { recursive: true });

  const line = JSON.stringify(json);

  await appendFile(file, os.EOL + line, { encoding: 'utf8' });
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
