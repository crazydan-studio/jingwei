package io.crazydan.jingwei.ui.schema.component.tree;

import io.crazydan.duzhou.framework.ui.domain.GenericStdDomainHandlers;
import io.crazydan.jingwei.ui.schema.component.tree._gen._XuiComponentTreeNodeNested;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.INeedInit;

import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_INVALID_TAG_NAME;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED;
import static io.nop.xlang.XLangErrors.ARG_TAG_NAME;

public class XuiComponentTreeNodeNested extends _XuiComponentTreeNodeNested implements INeedInit {

    public XuiComponentTreeNodeNested() {

    }

    @Override
    public void init() {
        checkTagName();
        checkMultiLayouts();
        checkSlotInSlot();

        getChildren().forEach((child) -> {
            if (child instanceof INeedInit) {
                ((INeedInit) child).init();
            }
        });
    }

    protected void checkTagName() {
        String tagName = get$tag();

        if (this instanceof XuiComponentTreeNodeAny) {
            if (!GenericStdDomainHandlers.isValidComponentName(tagName)) {
                throw new NopException(ERR_COMPONENT_INVALID_TAG_NAME).source(this).param(ARG_TAG_NAME, tagName);
            }
        }
    }

    protected void checkMultiLayouts() {
        boolean exists = false;
        for (XuiComponentTreeNodeKeyed child : getChildren()) {
            if (child instanceof XuiComponentTreeNodeLayout) {
                if (exists) {
                    throw new NopException(ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED).source(this)
                                                                                     .param(ARG_TAG_NAME, get$tag());
                } else {
                    exists = true;
                }
            }
        }
    }

    protected void checkSlotInSlot() {
        if (isSlot() && hasSlotInDepth()) {
            throw new NopException(ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED).source(this);
        }
    }

    protected boolean isSlot() {
        return this instanceof XuiComponentTreeNodeSlot;
    }

    private boolean hasSlotInDepth() {
        for (XuiComponentTreeNodeKeyed child : getChildren()) {
            if (child instanceof XuiComponentTreeNodeNested) {
                if (((XuiComponentTreeNodeNested) child).isSlot() //
                    || ((XuiComponentTreeNodeNested) child).hasSlotInDepth() //
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
