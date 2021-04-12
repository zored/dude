package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.lang.Language
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType

object ContextChecker {
    private val importTypes = setOf<IElementType>(
        GoTypes.IMPORT,
        GoTypes.IMPORT_DECLARATION,
        GoTypes.IMPORT_LIST,
        GoTypes.IMPORT_SPEC
    )

    fun isInsideImport(e: PsiElement): Boolean {
        val prev = e.prevSibling
        val prevType = prev.elementType
        if (prevType?.language == Language.ANY) {
            return isInsideImport(prev)
        }

        return importTypes.contains(prevType)
    }
}
