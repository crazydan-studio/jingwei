import { createApp } from 'vue';
import naive from 'naive-ui';

import App from './App.vue';

const app = createApp(App);
app.use(naive);

export function mount(el) {
  app.mount(el);
}

export function umount() {
  app.umount();
}
