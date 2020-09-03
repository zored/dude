package gl.ro.dude.domain.retriever.go

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.FilterPredicate
import gl.ro.dude.domain.retriever.Folder
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.MapPredicate

object TypeRetrieverImpl : IRetriever {
    private val RETRIEVERS = DeclarationRetriever.ALL.plus(IdentifierRetriever) // todo add alias support
    private var last: IRetriever? = null

    override fun suits(e: PsiElement): Boolean = findRetriever(e, true) !== null

    override fun getFolder(e: PsiElement): Folder = findRetriever(e)?.getFolder(e)

    private fun findRetriever(e: PsiElement, force: Boolean = false): IRetriever? {
        if (force || last == null) {
            last = RETRIEVERS.firstOrNull { it.suits(e) }
        }
        return last
    }
}