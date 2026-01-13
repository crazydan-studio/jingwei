<template>
  <n-loading-bar-provider>
    <n-message-provider>
      <n-notification-provider>
        <n-modal-provider>
          <n-dialog-provider>
            <n-config-provider :hljs="hljs">
              <Page />
            </n-config-provider>
          </n-dialog-provider>
        </n-modal-provider>
      </n-notification-provider>
    </n-message-provider>
  </n-loading-bar-provider>
</template>

<script setup>
import Page from './Page.vue';

import hljs from 'highlight.js';
</script>