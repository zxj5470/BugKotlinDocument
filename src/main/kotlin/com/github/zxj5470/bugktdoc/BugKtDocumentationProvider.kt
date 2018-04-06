package com.github.zxj5470.bugktdoc

import com.github.zxj5470.bugktdoc.constants.BugKtDocControl.LF
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration.PARAM
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration.PROPERTY
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration.RECEIVER
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration.RETURN
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration.THROWS
import com.intellij.codeInsight.editorActions.CodeDocumentationUtil
import com.intellij.ide.util.PackageUtil
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.lang.documentation.CodeDocumentationProvider
import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.lang.java.JavaDocumentationProvider.getPackageInfoComment
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.kdoc.psi.impl.KDocImpl
import org.jetbrains.kotlin.psi.*


/**
 * @author zxj5470
 * @date 2018/4/6
 */
class BugKtDocumentationProvider : DocumentationProviderEx(), CodeDocumentationProvider {
	override fun parseContext(startPoint: PsiElement): Pair<PsiElement, PsiComment>? {
		var current = startPoint
		while (current != null) {
			if (current is KDocImpl) {
				return Pair.create(if (current is PsiField) current.modifierList else current, current)
			} else if (PackageUtil.isPackageInfoFile(current)) {
				return Pair.create(current, getPackageInfoComment(current))
			}
			current = current.parent
		}
		return null
	}

	override fun generateDocumentationContentStub(contextComment: PsiComment?): String? {
		if (!pluginActive) return ""
		val owner = (contextComment as KDocImpl).getOwner()
		val commenter = LanguageCommenters.INSTANCE.forLanguage(contextComment.getLanguage()) as CodeDocumentationAwareCommenter
		fun StringBuilder.appendDecorate(str: String) = append(CodeDocumentationUtil.createDocCommentLine(str, contextComment.containingFile, commenter))
		return when (owner) {
			is KtNamedFunction -> buildString {
				// @receiver
				owner.receiverTypeReference?.let {
					appendDecorate(RECEIVER)
					append(it.text)
					append(LF)
				}
				// @param
				owner.valueParameters.forEach {
					val param = it.nameIdentifier?.text
					val type = it.typeReference?.typeElement?.text
					appendDecorate(PARAM)
					// add a space before `param` and after is no used
					append("$param $type")
					append(LF)
				}
				// @return
				if (owner.hasDeclaredReturnType()) {
					appendDecorate(RETURN)
					append(owner.typeReference?.typeElement?.text)
					append(LF)
				} else {
					val type = SpecifyTypeExplicitlyIntention.getTypeForDeclaration(owner).unwrap().toString()
					if (isAlwaysShowUnitReturnType || type != "Unit") {
						appendDecorate(RETURN)
						append(type)
						append(LF)
					}
				}
				// @throws
				PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java)
					.firstOrNull { it.calleeExpression?.text == "Throws" }
					?.valueArguments?.forEach {
					(it.getArgumentExpression() as? KtClassLiteralExpression)?.let {
						PsiTreeUtil.findChildOfType(it, KtNameReferenceExpression::class.java)?.text?.let {
							appendDecorate(THROWS)
							append(it)
							append(LF)
						}
					}
				}
			}
			is KtClass -> buildString {
				owner.typeParameters.forEach {
					appendDecorate(PARAM)
					append(it.text)
					append(LF)
				}
				owner.primaryConstructorParameters.forEach {
					val param = it.nameIdentifier?.text
					val type = it.typeReference?.typeElement?.text
					appendDecorate(PROPERTY)
					// add a space before `param` and after is no used
					append("$param $type")
					append(LF)
				}
				/*
				owner.getProperties().forEach {
					appendDecorate(PROPERTY)
					append(it.nameAsSafeName)
					append(LF)
				}
				*/
			}
			else -> ""
		}
	}

	override fun findExistingDocComment(contextElement: PsiComment?): PsiComment? =
		(contextElement as? KDocImpl)?.getOwner()?.docComment
}