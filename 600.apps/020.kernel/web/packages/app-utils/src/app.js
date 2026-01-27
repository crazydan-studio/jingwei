import { getAppConfig } from './config';
import { graphql } from './http';

const URL_PARAM_APP = 'app';

/** 加载应用页面 */
export async function loadAppPage({ appCode, el, loading }) {
  const appConfig = getAppConfig();
  if (!el) {
    el = document.getElementById(appConfig.containerId);
  }

  loading && startAppLoadingAnimation(el);

  try {
    await doLoadAppPage({ appCode, el });
  } finally {
    loading && endAppLoadingAnimation(el);
  }
}

export function getAppCodeFromLocation() {
  const urlParams = new URLSearchParams(window.location.search);

  return urlParams.get(URL_PARAM_APP);
}

export function updateAppCodeInLocation(appCode) {
  if (!appCode) {
    return;
  }

  const url = new URL(window.location.href);
  url.searchParams.set(URL_PARAM_APP, appCode);

  window.history.pushState({}, '', url);
}

export function startAppLoadingAnimation(el) {
  el.style.setProperty('--spinner-text', "'页面加载中，请稍等。。。'");

  el.classList.add('loading');
}

export function endAppLoadingAnimation(el) {
  el.addEventListener('transitionend', () => {
    el.classList.remove('loading', 'done');
  });

  el.classList.add('done');
}

async function doLoadAppPage({ appCode, el }) {
  const appConfig = getAppConfig();
  const ctx = appConfig.api.static;

  const { App__loadPage } = await graphql(
    `
      mutation ($app: String) {
        App__loadPage(app: $app)
      }
    `,
    { app: appCode }
  );

  const app = await import(ctx + App__loadPage.js);

  if (el.__app__) {
    el.__app__.umount();
  }

  App__loadPage.css.forEach((css) => addCssLink(ctx + css));

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
