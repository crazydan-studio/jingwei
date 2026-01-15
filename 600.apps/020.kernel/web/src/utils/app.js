const appConfig = __global_app_config__;

/** 加载应用 */
export async function loadApp(appCode) {
  return await import(appConfig.api.appStatic + '/' + appCode + '/index.js');
}
