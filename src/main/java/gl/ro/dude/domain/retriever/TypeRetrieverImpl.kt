package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.go.DeclarationRetriever
import gl.ro.dude.domain.retriever.go.IdentifierRetriever

object TypeRetrieverImpl : IRetriever {
    private val RETRIEVERS = DeclarationRetriever.ALL.plus(IdentifierRetriever) // todo add alias support
    private var last: IRetriever? = null

    override fun getFilter(e: PsiElement): FilterPredicate = findRetriever(e)?.getFilter(e)

    override fun suits(e: PsiElement): Boolean = findRetriever(e, true) !== null

    override fun getMap(e: PsiElement): MapPredicate = findRetriever(e)?.getMap(e)

    private fun findRetriever(e: PsiElement, force: Boolean = false): IRetriever? {
        if (force || last == null) {
            last = RETRIEVERS.firstOrNull { it.suits(e) }
        }
        return last
    }
}