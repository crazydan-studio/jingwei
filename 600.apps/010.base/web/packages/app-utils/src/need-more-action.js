import { h } from 'vue';
import {
  NButton,
  NImage,
  NInput,
  NInputGroup,
  NInputGroupLabel,
  NFlex
} from 'naive-ui';

import { modal } from './msg';

export function popupNeedMoreActionForm(form) {
  modal.create({
    title: form.title || '',
    preset: 'dialog',
    draggable: true,
    closeOnEsc: false,
    maskClosable: false,
    style: { width: 'unset' },
    content: () => render(form.body)
  });
}

function render(component, { inInputGroup } = {}) {
  if (!component) {
    return [];
  } else if (Array.isArray(component)) {
    return component.map(render);
  }

  let { type } = component;
  let props = {};
  let children = [];
  switch (type) {
    case 'row':
    case 'column': {
      const vertical = type == 'column';
      let { align } = component;
      align ||= {};

      type = NFlex;
      props = {
        vertical,
        align: vertical ? align.row : align.column,
        justify: vertical ? align.column : align.row
      };
      children = render(component.body);
      break;
    }
    case 'input': {
      const { label, name, prefix, suffix } = component;

      if (!prefix && !suffix) {
        type = NInput;
        props = { placeholder: label };
        children = [];
      } else {
        type = NInputGroup;

        if (prefix) {
          children.push(render(prefix, { inInputGroup: true }));
        }

        const onlyInput = { ...component, prefix: null, suffix: null };
        children.push(render(onlyInput));

        if (suffix) {
          children.push(render(suffix, { inInputGroup: true }));
        }
      }
      break;
    }
    case 'button': {
      const { label, color, action } = component;
      type = NButton;
      props = { type: color };
      children = [label];
      break;
    }
    case 'text': {
      const { value } = component;

      if (inInputGroup) {
        type = NInputGroupLabel;
      } else {
        type = 'span';
      }
      children = [value];
      break;
    }
    case 'image': {
      const { src, width, height, action } = component;
      type = NImage;
      props = { src, width, height };
      break;
    }
  }

  return h(type, props, children);
}
