package io.crazydan.jingwei.ui.schema.component;

import io.crazydan.jingwei.ui.schema.component._gen._XuiComponent;
import io.nop.api.core.util.INeedInit;

public class XuiComponent extends _XuiComponent implements INeedInit {

    public XuiComponent() {

    }

    @Override
    public void init() {
        initTemplate();
    }

    protected void initTemplate() {
        if (getTemplate() != null) {
            getTemplate().init();
        }
    }
}
