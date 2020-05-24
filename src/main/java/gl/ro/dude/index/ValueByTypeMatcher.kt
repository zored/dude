package gl.ro.dude.index

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.TypeRetrieverImpl

object ValueByTypeMatcher {
    private val RETRIEVER: IRetriever = TypeRetrieverImpl

    @Suppress("RemoveExplicitTypeArguments")
    private val EMPTY: Iterable<Value> by lazy { listOf<Value>() }

    fun byElement(e: PsiElement): Iterable<String> {
        if (!RETRIEVER.suits(e)) {
            return EMPTY
        }

        val filter = RETRIEVER.getFilter(e) ?: return EMPTY
        val mapPredicate = RETRIEVER.getMap(e) ?: return EMPTY

        return ValuesByTypeIterator(e.project).filter(filter).flatMap(mapPredicate)
    }
}