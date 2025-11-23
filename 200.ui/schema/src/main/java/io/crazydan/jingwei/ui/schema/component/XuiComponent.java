package io.crazydan.jingwei.ui.schema.component;

import java.util.List;

import io.crazydan.jingwei.ui.schema.component._gen._XuiComponent;
import io.crazydan.jingwei.ui.schema.component.template.XuiComponentTemplate;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.INeedInit;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.xml.XNode;
import io.nop.xlang.api.IXLangCompileScope;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XLangCompileTool;
import io.nop.xlang.ast.Expression;
import io.nop.xlang.ast.XLangOutputMode;
import io.nop.xlang.xdsl.DslModelHelper;
import io.nop.xlang.xpl.IXplCompiler;
import io.nop.xlang.xpl.IXplTagCompiler;
import io.nop.xlang.xpl.tags.ChooseTagCompiler;
import io.nop.xlang.xpl.tags.ForTagCompiler;
import io.nop.xlang.xpl.tags.IfTagCompiler;

import static io.crazydan.jingwei.ui.XuiConstants.ATTR_NAME_XUI_ID;
import static io.crazydan.jingwei.ui.XuiConstants.ATTR_NAME_XUI_ID_RAW;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_CHOOSE;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_FOR;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_IF;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_OTHERWISE;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_TEMPLATE;
import static io.crazydan.jingwei.ui.XuiConstants.TAG_NAME_WHEN;
import static io.crazydan.jingwei.ui.XuiConstants.XDSL_SCHEMA_COMPONENT_TEMPLATE;
import static io.crazydan.jingwei.ui.XuiErrors.ERR_COMPONENT_DSL_NODE_NOT_BOUND;
import static io.nop.xlang.xpl.XplConstants.INDEX_NAME;

/**
 * 支持调用 {@link #evalTemplate} 以动态解析组件树
 * <br/><br/>
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

    public XuiComponentTemplate evalTemplate(IEvalScope scope) {
        return doEvalTemplate(scope);
    }

    protected void initTemplate() {
        if (getTemplate() != null) {
            getTemplate().init();
        }
    }

    protected XuiComponentTemplate doEvalTemplate(IEvalScope scope) {
        IEvalAction action = getTemplateEvalAction();
        if (action == null) {
            return null;
        }

        // TODO 传入配置的组件属性

        XNode node = (XNode) action.invoke(scope);

        XuiComponentTemplate template = //
                (XuiComponentTemplate) DslModelHelper.parseDslNode(XDSL_SCHEMA_COMPONENT_TEMPLATE, node);
        template.init();

        return template;
    }

    protected IEvalAction getTemplateEvalAction() {
        if (this._dslNode == null) {
            throw new NopException(ERR_COMPONENT_DSL_NODE_NOT_BOUND).source(this);
        }

        if (this.templateEvalAction == null) {
            XNode node = this._dslNode.childByTag(TAG_NAME_TEMPLATE);

            if (node != null) {
                // Note: 必须将 <template/> 单独挂载到仅有唯一子节点的根节点上，
                // 因为 Xpl 脚本的执行结果返回的是脚本构造节点的最后一个子节点
                XNode dummy = new XNode("_");
                node.cloneInstance().insertParent(dummy);

                this.templateEvalAction = newCompileTool().compileTagBody(dummy, XLangOutputMode.node);
            }
        }
        return this.templateEvalAction;
    }

    public static XLangCompileTool newCompileTool() {
        XLangCompileTool compileTool = XLang.newCompileTool();
        compileTool.allowUnregisteredScopeVar(true);

        IXLangCompileScope scope = compileTool.getScope();
        scope.addTagCompiler(TAG_NAME_IF, new XplTagCompiler(IfTagCompiler.INSTANCE));
        scope.addTagCompiler(TAG_NAME_CHOOSE, new XplTagCompiler(ChooseTagCompiler.INSTANCE));
        scope.addTagCompiler(TAG_NAME_FOR, new XplTagCompiler(ForTagCompiler.INSTANCE));

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

    private static class XplTagCompiler implements IXplTagCompiler {
        private static final List<String> TAGS = List.of(TAG_NAME_IF,
                                                         TAG_NAME_CHOOSE,
                                                         TAG_NAME_FOR,
                                                         TAG_NAME_WHEN,
                                                         TAG_NAME_OTHERWISE);

        private final IXplTagCompiler compiler;

        private XplTagCompiler(IXplTagCompiler compiler) {
            this.compiler = compiler;
        }

        @Override
        public Expression parseTag(XNode node, IXplCompiler cp, IXLangCompileScope scope) {
            cleanNode(node);
            if (this.compiler instanceof ChooseTagCompiler) {
                node.getChildren().forEach(this::cleanNode);
            } //
            else if (this.compiler instanceof ForTagCompiler) {
                String indexName = node.attrText(INDEX_NAME, "_forIndex_");
                if (!node.hasAttr(INDEX_NAME)) {
                    node.setAttr(INDEX_NAME, indexName);
                }

                patchXuiIdInFor(node, indexName, false);
            }

            return this.compiler.parseTag(node, cp, scope);
        }

        private void cleanNode(XNode node) {
            node.removeAttr(ATTR_NAME_XUI_ID);
        }

        /** 为确保 for 循环中的节点唯一标识的唯一性，需要在原始的标识中添加循环序号变量 */
        private void patchXuiIdInFor(XNode node, String indexName, boolean ignoreFor) {
            if (ignoreFor && TAG_NAME_FOR.equals(node.getTagName())) {
                return;
            }

            for (XNode child : node.getChildren()) {
                String childTagName = child.getTagName();

                if (TAGS.contains(childTagName)) {
                    patchXuiIdInFor(child, indexName, true);
                } else {
                    String xuiId = child.attrText(ATTR_NAME_XUI_ID);
                    child.setAttr(ATTR_NAME_XUI_ID_RAW, xuiId);
                    child.setAttr(ATTR_NAME_XUI_ID, xuiId + "_${" + indexName + "}");
                }
            }
        }
    }
}
