package cn.wjdghd

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * @author: zxj5470
 * @date: 2017/12/4
 */
class FolderUtils : FoldingBuilderEx(), DumbAware {
    override fun getPlaceholderText(node: ASTNode): String="..."

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {

        val lst=ArrayList<FoldingDescriptor>()
        val content=root.text
        val begin=content.countIndices()
        val end=begin+2
        val s=content.subSequence(begin,end).toString().toUpperCase()
        return arrayOf(getFold(root, TextRange.from(begin, 3),s))
    }

    override fun isCollapsedByDefault(node: ASTNode)=true

    fun getFold(elem: PsiElement, range: TextRange, placeholder: String) =
            object : FoldingDescriptor(elem, range) {
                override fun getPlaceholderText() = placeholder
            }
}

fun String.countIndices():Int = this.indexOf("main")