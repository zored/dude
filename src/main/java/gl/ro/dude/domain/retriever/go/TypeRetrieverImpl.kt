package gl.ro.dude.domain.retriever.go

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.ICompletionsRetriever
import gl.ro.dude.domain.retriever.OptionalFolder

object TypeRetrieverImpl : ICompletionsRetriever {
    private val RETRIEVERS = DeclarationRetriever.ALL +
        ImportRetriever +
        IdentifierRetriever

    override fun getFolder(e: PsiElement): OptionalFolder {
        var folder: OptionalFolder = null
        RETRIEVERS.firstOrNull {
            folder = it.getFolder(e)
            folder !== null
        }
        return folder
    }
}
