package gl.ro.guess_idea.completion.type_retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import gl.ro.guess_idea.completion.type_retriever.IRetriever
import gl.ro.guess_idea.index.Type

class DeclarationRetriever(private val definitionType: IElementType, private val declarationType: IElementType) :
    IRetriever {
    override fun get(position: PsiElement): Type? {
        val parameterDeclaration = getDeclaration(position) ?: return null
        return parameterDeclaration.children.firstOrNull { it.elementType == GoTypes.TYPE }?.text
    }

    override fun suits(position: PsiElement): Boolean {
        return getDeclaration(position) != null
    }

    private fun getDeclaration(position: PsiElement): PsiElement? {
        val definition = position.parent
        if (definition.elementType != definitionType) {
            return null
        }

        val declaration = definition.parent
        if (declaration.elementType != declarationType) {
            return null
        }

        return declaration
    }

    companion object {
        val ALL = listOf(
            DeclarationRetriever(GoTypes.PARAM_DEFINITION, GoTypes.PARAMETER_DECLARATION),
            DeclarationRetriever(GoTypes.FIELD_DEFINITION, GoTypes.FIELD_DECLARATION),
            DeclarationRetriever(GoTypes.VAR_DEFINITION, GoTypes.VAR_DECLARATION),
            DeclarationRetriever(GoTypes.CONST_DEFINITION, GoTypes.CONST_DECLARATION)
        )
    }
}