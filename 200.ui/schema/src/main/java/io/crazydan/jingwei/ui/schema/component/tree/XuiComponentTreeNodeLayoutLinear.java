package io.crazydan.jingwei.ui.schema.component.tree;

import io.crazydan.duzhou.framework.ui.layout.XuiLayoutNode;
import io.crazydan.duzhou.framework.ui.layout.parser.XuiLayoutLinearParser;
import io.crazydan.jingwei.ui.schema.component.tree._gen._XuiComponentTreeNodeLayoutLinear;
import io.nop.api.core.util.SourceLocation;
import io.nop.core.lang.json.IJsonHandler;

public class XuiComponentTreeNodeLayoutLinear extends _XuiComponentTreeNodeLayoutLinear {
    private XuiLayoutNode root;

    public XuiComponentTreeNodeLayoutLinear() {

    }

    @Override
    public String getType() {
        return get$tag();
    }

    @Override
    public XuiLayoutNode getRoot() {
        if (this.root == null) {
            SourceLocation loc = getLocation();
            XuiLayoutLinearParser parser = new XuiLayoutLinearParser(getMode());

            this.root = parser.parseFromText(loc, getValue());
        }
        return this.root;
    }

    @Override
    protected void outputJson(IJsonHandler out) {
        super.outputJson(out);

        out.putNotNull("root", getRoot());
    }
}
