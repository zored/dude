package gl.ro.dude.domain.retriever.go

import com.intellij.psi.PsiElement
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
                        .map { "${it.name} \"${it.typeName}\"" }
                        .toList()
            }
}