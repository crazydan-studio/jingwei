package io.crazydan.jingwei.ui.schema.component.template;

import io.crazydan.jingwei.ui.schema.component.template._gen._XuiComponentTemplateNodeText;

public class XuiComponentTemplateNodeText extends _XuiComponentTemplateNodeText {

    public XuiComponentTemplateNodeText() {

    }

    public String getInnerText() {
        // TODO 去除 Text 组件内文本的空白
        return getValue();
    }
}
