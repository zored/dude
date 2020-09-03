package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import gl.ro.dude.domain.retriever.Folder
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.Type

object IdentifierRetriever : IRetriever {
    override fun getFolder(e: PsiElement): Folder =
        { completions, (t, values) ->
            if (t === Type.VARIABLE)
                completions + values
                    .map { "${it.value} ${it.typeName}" }
                    .toList()
            else
                completions
        }

    override fun suits(e: PsiElement): Boolean = e.elementType === GoTypes.IDENTIFIER
}