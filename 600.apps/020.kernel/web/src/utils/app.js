/** 获取应用配置 */
export function getAppConfig() {
  return window.__global_app_config__;
}

/** 加载应用 */
export async function loadApp(appCode) {
  const appConfig = getAppConfig();

  return await import(appConfig.api.appStatic + '/' + appCode + '/index.js');
}
