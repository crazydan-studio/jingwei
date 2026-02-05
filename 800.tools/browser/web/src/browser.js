import { chromium } from 'playwright-core';

const AUTH_TIMEOUT = 5 * 60 * 1000;

export async function lanuchBrowser({ headless }) {
  return await chromium.launch({
    // Using 'channel' to drive the user's local Chrome
    channel: 'chrome',
    headless
  });
}

export async function createPage(browser, { authFile }) {
  const context = await browser.newContext({
    storageState: authFile,
    // 确保页面上的文本为英文的
    locale: 'en-US'
  });

  return await context.newPage();
}

export async function startAuth(authUrl, successUrl, { authFile }) {
  const browser = await lanuchBrowser({ headless: false });

  const context = await browser.newContext({ storageState: undefined });
  const page = await context.newPage();

  await page.goto(authUrl);

  await page.waitForURL(successUrl, { timeout: AUTH_TIMEOUT });

  await context.storageState({ path: authFile });
  await browser.close();
}
