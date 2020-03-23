package gl.ro.guess_idea.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import gl.ro.guess_idea.domain.retriever.FilterPredicate
import gl.ro.guess_idea.domain.retriever.IRetriever
import gl.ro.guess_idea.domain.retriever.MapPredicate

class DeclarationRetriever(
    private val definitionType: IElementType,
    private val declarationType: IElementType
) : IRetriever {
    override fun getFilter(e: PsiElement): FilterPredicate {
        val parameterDeclaration = getDeclaration(e) ?: return null
        val type = parameterDeclaration.children.firstOrNull { it.elementType == GoTypes.TYPE }?.text

        return { it.first == type }
    }

    override fun suits(e: PsiElement): Boolean {
        return getDeclaration(e) != null
    }

    override fun getMap(e: PsiElement): MapPredicate {
        return { it.second }
    }

    private fun getDeclaration(position: PsiElement): PsiElement? {
        val definition = when (definitionType) {
            position.elementType -> position
            position.parent.elementType -> position.parent
            else -> return null
        }

        return when (declarationType) {
            definition.parent.elementType -> definition.parent
            else -> return null
        }
    }

    companion object {
        private val DATA = listOf(
            Pair(GoTypes.PARAM_DEFINITION, GoTypes.PARAMETER_DECLARATION),
            Pair(GoTypes.FIELD_DEFINITION, GoTypes.FIELD_DECLARATION),
            Pair(GoTypes.VAR_DEFINITION, GoTypes.VAR_SPEC),
            Pair(GoTypes.CONST_DEFINITION, GoTypes.CONST_DECLARATION)
        )
        val ALL = DATA.map { DeclarationRetriever(it.first, it.second) }
    }
}