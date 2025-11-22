package io.crazydan.jingwei.ui.schema.component;

import io.crazydan.jingwei.ui.schema.component._gen._XuiComponent;
import io.crazydan.jingwei.ui.schema.component.tree.XuiComponentTreeNodeRoot;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.INeedInit;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.xlang.api.IXLangCompileScope;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XLangCompileTool;
import io.nop.xlang.ast.XLangOutputMode;
import io.nop.xlang.xdsl.DslModelHelper;
import io.nop.xlang.xpl.tags.ChooseTagCompiler;
import io.nop.xlang.xpl.tags.ForTagCompiler;
import io.nop.xlang.xpl.tags.IfTagCompiler;

import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_CHOOSE;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_FOR;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_IF;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_TEMPLATE;
import static io.crazydan.jingwei.ui.XuiConstants.XDSL_SCHEMA_COMPONENT_TREE;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_DSL_NODE_NOT_BOUND;

/**
 * 支持调用 {@link #evalTreeRootNode} 以动态解析组件树
 * <p/>
 * 对组件树的动态解析是通过将 &lt;if/>、&lt;for/> 等标签映射为
 * &lt;c:if/>、&lt;c:for/> 等 Xpl 标签，再将 {@link #getTemplate()}
 * 对应的 XNode 节点解析为 Xpl 脚本的方式实现。
 * 虽然该方式存在重复解析 XNode 节点的问题，但考虑到对组件完整性和有效性的检查等业务问题，
 * 其性能损耗是必要且可接受的（对 Xpl 脚本的解析结果将被缓存起来，避免重复解析）
 */
public class XuiComponent extends _XuiComponent implements INeedInit {
    /** 组件的 {@link XNode} 节点 */
    private XNode _dslNode;
    /** 用于支持 Xpl 动态解析组件树 */
    private IEvalAction templateEvalAction;

    public XuiComponent() {

    }

    @Override
    public void init() {
        initTemplate();
        // TODO 检查未导入组件
    }

    public XuiComponentTreeNodeRoot evalTreeRootNode(IEvalScope scope) {
        return doEvalTreeRootNode(scope);
    }

    protected void initTemplate() {
        if (getTemplate() != null) {
            getTemplate().init();
        }
    }

    protected XuiComponentTreeNodeRoot doEvalTreeRootNode(IEvalScope scope) {
        // TODO 传入配置的组件属性，深度拆解嵌套组件直到 native

        IEvalAction action = getTemplateEvalAction();
        XNode node = (XNode) action.invoke(scope);

        return (XuiComponentTreeNodeRoot) DslModelHelper.parseDslNode(XDSL_SCHEMA_COMPONENT_TREE, node);
    }

    protected IEvalAction getTemplateEvalAction() {
        if (_dslNode == null) {
            throw new NopException(ERR_COMPONENT_DSL_NODE_NOT_BOUND).source(this);
        }

        if (templateEvalAction == null) {
            XNode node = _dslNode.childByTag(TAG_NAME_TEMPLATE);

            if (node != null) {
                templateEvalAction = newCompileTool().compileTagBody(node, XLangOutputMode.node);
            }
        }
        return templateEvalAction;
    }

    public static XLangCompileTool newCompileTool() {
        XLangCompileTool compileTool = XLang.newCompileTool();
        compileTool.allowUnregisteredScopeVar(true);

        IXLangCompileScope scope = compileTool.getScope();
        scope.addTagCompiler(TAG_NAME_IF, IfTagCompiler.INSTANCE);
        scope.addTagCompiler(TAG_NAME_CHOOSE, ChooseTagCompiler.INSTANCE);
        scope.addTagCompiler(TAG_NAME_FOR, ForTagCompiler.INSTANCE);

        return compileTool;
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
