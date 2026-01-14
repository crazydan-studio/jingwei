<template>
  <div ref="appRef"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { createDiscreteApi } from 'naive-ui';

import { loadApp } from './utils/app';

// https://www.naiveui.com/zh-CN/os-theme/components/discrete
const { message, notification, dialog, loadingBar, modal } = //
  createDiscreteApi(
    ['message', 'dialog', 'notification', 'loadingBar', 'modal'],
    {}
  );

const appRef = ref(null);
onMounted(async () => {
  const appConfig = __global_app_config__;

  const urlParams = new URLSearchParams(window.location.search);
  const appCode = urlParams.get('app') || appConfig.protalCode;

  // 修改地址栏，从而支持后退
  if (appCode != appConfig.protalCode) {
    const url = new URL(window.location.href);
    url.searchParams.set('app', appCode);
    window.history.pushState({}, '', url);
  }

  try {
    const app = await loadApp(appCode);
    app?.mount(appRef.value, appConfig);
  } catch (e) {
    dialog.error({
      title: '异常提醒',
      content: 'App ' + appCode + ' loading failed: ' + e,
      positiveText: '确认',
      maskClosable: false,
    });
  }
});
</script>