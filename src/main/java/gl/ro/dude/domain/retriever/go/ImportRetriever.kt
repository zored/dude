package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings
import gl.ro.dude.domain.retriever.ICompletionsRetriever
import gl.ro.dude.domain.retriever.OptionalFolder
import gl.ro.dude.domain.retriever.Type

object ImportRetriever : ICompletionsRetriever {
    override fun getFolder(e: PsiElement): OptionalFolder =
        if (!ContextChecker.isInsideImport(e))
            null
        else
            { completions, (t, values) ->
                if (t !== Type.IMPORT)
                    completions
                else
                    completions + values
                        .map { "${it.value} \"${it.typeName}\"" }
                        .toList()
            }
}