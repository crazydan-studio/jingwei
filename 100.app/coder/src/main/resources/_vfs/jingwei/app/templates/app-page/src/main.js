import { createApp } from 'vue';
import naive from 'naive-ui';

import App from './App.vue';

const app = createApp(App);
app.use(naive);

export function mount(selector) {
  app.mount(selector);
}

export function umount() {
  app.umount();
}
