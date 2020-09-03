package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import gl.ro.dude.domain.retriever.*

class DeclarationRetriever(
    private val definitionType: IElementType,
    private val declarationType: IElementType
) : IRetriever {

    override fun getFolder(e: PsiElement): Folder {
        val declaration = getDeclaration(e) ?: return null
        val nodeType = declaration.children.firstOrNull { it.elementType == GoTypes.TYPE }?.text

        return { completions, (t, values) ->
            if (t === Type.VARIABLE)
                completions + values
                    .filter { it.typeName == nodeType }
                    .map { it.value }
                    .toList()
            else
                completions
        }
    }

    override fun suits(e: PsiElement): Boolean = getDeclaration(e) != null

    private fun getDeclaration(e: PsiElement): PsiElement? {
        val definition = when (definitionType) {
            e.elementType -> e
            e.parent.elementType -> e.parent
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
        val ALL = DATA.map { (def, decl) -> DeclarationRetriever(def, decl) }
    }
}