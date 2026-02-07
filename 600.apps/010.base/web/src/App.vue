<template>
  <div ref="appRef"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

import {
  getAppConfig, getAppCodeFromLocation, loadAppPage,
  startAppLoadingAnimation, endAppLoadingAnimation
} from '@app-utils';

const mainEl = document.getElementById('main');
startAppLoadingAnimation(mainEl);

const appConfig = getAppConfig();

const appRef = ref(null);
onMounted(async () => {
  // 按配置绑定 id
  appRef.value.id = appConfig.containerId;

  const appCode = getAppCodeFromLocation();

  await loadAppPage({ appCode, el: appRef.value, loading: false });

  endAppLoadingAnimation(mainEl);
});
</script>
