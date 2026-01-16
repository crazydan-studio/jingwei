import { getAppConfig } from './config';
import { graphql } from './http';

const URL_PARAM_APP = 'app';

/** 加载应用页面 */
export async function loadAppPage({ appCode, el, forPreview }) {
  // TODO 开始等待
  try {
    await doLoadAppPage({ appCode, el, forPreview });
  } finally {
    // TODO 结束等待
  }
}

export function getAppCodeFromLocation() {
  const urlParams = new URLSearchParams(window.location.search);

  return urlParams.get(URL_PARAM_APP);
}

export function updateAppCodeInLocation(appCode) {
  const url = new URL(window.location.href);
  url.searchParams.set(URL_PARAM_APP, appCode);

  window.history.pushState({}, '', url);
}

async function doLoadAppPage({ appCode, el, forPreview }) {
  const appConfig = getAppConfig();
  const ctx = appConfig.api.static;

  const { App__loadAppPage } = await graphql(
    `
      mutation ($app: String!, $preview: Boolean) {
        App__loadAppPage(app: $app, preview: $preview)
      }
    `,
    { app: appCode, preview: !!forPreview }
  );

  const app = await import(ctx + App__loadAppPage.js);

  if (!el) {
    el = document.getElementById(appConfig.containerId);
  }
  if (el.__app__) {
    el.__app__.umount();
  }

  App__loadAppPage.css.forEach((css) => addCssLink(ctx + css));

  app.mount(el);
  el.__app__ = app;

  // 修改地址栏，从而支持后退
  updateAppCodeInLocation(appCode);
}

function addCssLink(url) {
  const head = document.head;
  if (head.querySelector('link[href="' + url + '"]')) {
    return;
  }

  const link = document.createElement('link');
  link.rel = 'stylesheet';
  link.type = 'text/css';
  link.href = url;

  head.appendChild(link);
}
