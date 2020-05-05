package gl.ro.guess_idea.index

import com.intellij.psi.PsiElement
import gl.ro.guess_idea.domain.retriever.IRetriever
import gl.ro.guess_idea.domain.retriever.TypeRetrieverImpl

object ValueByTypeMatcher {
    private val RETRIEVER: IRetriever = TypeRetrieverImpl
    private val EMPTY: Iterable<Value> by lazy { listOf() }

    fun byElement(e: PsiElement): Iterable<String> {
        if (!RETRIEVER.suits(e)) {
            return EMPTY
        }

        val filter = RETRIEVER.getFilter(e) ?: return EMPTY
        val mapPredicate = RETRIEVER.getMap(e) ?: return EMPTY

        return ValuesByTypeIterator(e.project).filter(filter).flatMap(mapPredicate)
    }
}