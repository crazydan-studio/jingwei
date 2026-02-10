// https://playwright.dev/docs/api/class-playwright
import { chromium } from 'playwright-core';

import { initJsonFile } from '@/utils/fs';

export const AUTH_TIMEOUT = 5 * 60 * 1000;

export async function createContext({ authFile }) {
  await initJsonFile(authFile);

  const browser = await lanuchBrowser();

  return await browser.newContext({
    storageState: authFile,
    // 确保页面上的文本为英文的
    locale: 'en-US'
  });
}

export async function startAuth(authUrl, successUrl, { authFile }) {
  const browser = await lanuchBrowser();

  const context = await browser.newContext({ storageState: undefined });
  const page = await context.newPage();

  await page.goto(authUrl);

  await page.waitForURL(successUrl, { timeout: AUTH_TIMEOUT });

  await context.storageState({ path: authFile });
  await browser.close();
}

export async function closeContext(context) {
  try {
    await context.close();
  } catch (e) {
    console.error(e);
  }
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
let browser = null;
async function lanuchBrowser() {
  if (!!browser && (await browser.isConnected())) {
    return browser;
  }

  // Note: 开启的浏览器示例会在主进程退出后自动关闭
  browser = await chromium.launch({
    // Using 'channel' to drive the user's local Chrome
    channel: 'chrome',
    headless: true,
    args: ['--disable-dev-shm-usage', '--no-sandbox']
  });

  return browser;
}
