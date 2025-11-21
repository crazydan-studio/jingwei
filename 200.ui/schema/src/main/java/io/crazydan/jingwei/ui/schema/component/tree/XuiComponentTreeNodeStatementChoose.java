package io.crazydan.jingwei.ui.schema.component.tree;

import io.crazydan.jingwei.ui.schema.component.tree._gen._XuiComponentTreeNodeStatementChoose;
import io.nop.api.core.util.INeedInit;

public class XuiComponentTreeNodeStatementChoose extends _XuiComponentTreeNodeStatementChoose implements INeedInit {

    public XuiComponentTreeNodeStatementChoose() {

    }

    @Override
    public void init() {
        getWhens().forEach(XuiComponentTreeNodeNested::init);
        if (getOtherwise() != null) {
            getOtherwise().init();
        }
    }
}
