package io.crazydan.jingwei.ui.schema.component.template;

import io.crazydan.duzhou.framework.ui.domain.GenericStdDomainHandlers;
import io.crazydan.jingwei.ui.schema.component.template._gen._XuiComponentTemplateNodeNested;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.INeedInit;

import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_INVALID_TAG_NAME;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_MULTIPLE_LAYOUT_NOT_ALLOWED;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_SLOT_IN_DEPTH_NOT_ALLOWED;
import static io.nop.xlang.XLangErrors.ARG_TAG_NAME;

public class XuiComponentTemplateNodeNested extends _XuiComponentTemplateNodeNested implements INeedInit {

    public XuiComponentTemplateNodeNested() {

    }

    @Override
    public void init() {
        checkTagName();
        checkMultiLayouts();
        checkSlotInSlot();
        // TODO 同名消息不能重复派发

        getChildren().forEach((child) -> {
            if (child instanceof INeedInit) {
                ((INeedInit) child).init();
            }
        });
    }

    protected void checkTagName() {
        String tagName = get$tag();

        if (this instanceof XuiComponentTemplateNodeAny) {
            if (!GenericStdDomainHandlers.isValidComponentName(tagName)) {
                throw new NopException(ERR_COMPONENT_INVALID_TAG_NAME).source(this).param(ARG_TAG_NAME, tagName);
            }
        }
    }

    protected void checkMultiLayouts() {
        boolean exists = false;
        for (XuiComponentTemplateNodeKeyed child : getChildren()) {
            if (child instanceof XuiComponentTemplateNodeLayout) {
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
        return this instanceof XuiComponentTemplateNodeSlot;
    }

    private boolean hasSlotInDepth() {
        for (XuiComponentTemplateNodeKeyed child : getChildren()) {
            if (child instanceof XuiComponentTemplateNodeNested) {
                if (((XuiComponentTemplateNodeNested) child).isSlot() //
                    || ((XuiComponentTemplateNodeNested) child).hasSlotInDepth() //
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
