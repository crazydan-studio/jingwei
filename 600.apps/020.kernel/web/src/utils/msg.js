import { createDiscreteApi } from 'naive-ui';

// https://www.naiveui.com/zh-CN/os-theme/components/discrete
export const { message, notification, dialog, loadingBar, modal } =
  createDiscreteApi(
    ['message', 'dialog', 'notification', 'loadingBar', 'modal'],
    {}
  );
