import { h, ref } from 'vue';
import {
  NButton,
  NImage,
  NInput,
  NInputGroup,
  NInputGroupLabel,
  NFlex,
  NSpin
} from 'naive-ui';

import { modal } from './msg';

export function popupNeedMoreActionForm({ title, body, graphql }, options) {
  modal.create({
    title: title || '',
    preset: 'dialog',
    draggable: true,
    closeOnEsc: false,
    maskClosable: false,
    // 设置自动宽度
    style: { width: 'unset' },
    content: () => {
      const show = ref(false);
      return h(
        NSpin,
        { style: 'padding-top:1em', show: show.value },
        render(body, {
          vars: {},
          graphql: async (vars) => {
            try {
              show.value = true;
              await options.graphql(graphql, vars);
            } finally {
              show.value = false;
            }
          }
        })
      );
    }
  });
}

function render(component, opts = {}) {
  if (!component) {
    return [];
  } else if (Array.isArray(component)) {
    return component.map((cmp) => render(cmp, opts));
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
      children = render(component.body, opts);
      break;
    }
    case 'input': {
      const { label, name, prefix, suffix } = component;

      if (!prefix && !suffix) {
        type = NInput;
        props = {
          placeholder: label,
          onInput: (val) => {
            opts.vars[name] = val;
          }
        };
        children = [];
      } else {
        type = NInputGroup;

        if (prefix) {
          children.push(render(prefix, { ...opts, inInputGroup: true }));
        }

        const onlyInput = { ...component, prefix: null, suffix: null };
        children.push(render(onlyInput, opts));

        if (suffix) {
          children.push(render(suffix, { ...opts, inInputGroup: true }));
        }
      }
      break;
    }
    case 'button': {
      const { label, color } = component;
      type = NButton;
      props = { type: color };
      children = [label];
      break;
    }
    case 'text': {
      const { value } = component;

      if (opts.inInputGroup) {
        type = NInputGroupLabel;
      } else {
        type = 'span';
      }
      children = [value];
      break;
    }
    case 'image': {
      const { src, width, height, preview } = component;
      type = NImage;
      props = { src, width, height, previewDisabled: preview == false };
      break;
    }
  }

  bindAction(props, component.action, opts);

  return h(type, props, children);
}

function bindAction(props, action, { graphql, vars }) {
  if (!action || !action.name) {
    return;
  }

  let on = action.on || 'click';
  on = on.charAt(0).toUpperCase() + on.slice(1);

  props['on' + on] = async (event) => {
    const data = replaceVars(action.data, { ...vars, event });

    await graphql({ action: action.name, data });
  };
}

function replaceVars(s, vars) {
  return (
    s?.replace(/\${(\s*[-_\w\d.]+\s*)}/g, (_, path) => {
      return getByPath(vars, path.trim());
    }) ?? ''
  );
}

function getByPath(data, path) {
  return path.split('.').reduce((obj, key) => obj?.[key], data) ?? '';
}
