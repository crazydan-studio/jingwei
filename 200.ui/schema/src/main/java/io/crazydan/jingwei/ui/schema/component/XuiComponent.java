package io.crazydan.jingwei.ui.schema.component;

import io.crazydan.jingwei.ui.schema.component._gen._XuiComponent;
import io.nop.api.core.util.INeedInit;
import io.nop.core.lang.xml.XNode;

public class XuiComponent extends _XuiComponent implements INeedInit {
    /** 组件的 {@link XNode} 节点 */
    private XNode _dslNode;

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

    // <<<<<<<<<<<<<<< getter/setter

    public XNode getDslNode() {
        return this._dslNode;
    }

    public void setDslNode(XNode dslNode) {
        this._dslNode = dslNode;
    }

    // >>>>>>>>>>>>>>>
}
