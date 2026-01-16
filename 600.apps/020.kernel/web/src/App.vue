<template>
  <div ref="appRef"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

import { getAppConfig, getAppCodeFromLocation, loadAppPage } from '@app-utils';

const appConfig = getAppConfig();

const appRef = ref(null);
onMounted(async () => {
  // 按配置绑定 id
  appRef.value.id = appConfig.containerId;

  const appCode = getAppCodeFromLocation() || appConfig.protalCode;

  await loadAppPage({ appCode, el: appRef.value });
});
</script>
