package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import gl.ro.dude.domain.retriever.ICompletionsRetriever
import gl.ro.dude.domain.retriever.OptionalFolder
import gl.ro.dude.domain.retriever.Type

object IdentifierRetriever : ICompletionsRetriever {
    override fun getFolder(e: PsiElement): OptionalFolder =
        if (e.elementType === GoTypes.IDENTIFIER)
            { completions, (t, values) ->
                if (t === Type.VARIABLE)
                    completions + values
                else
                    completions
            }
        else
            null
}