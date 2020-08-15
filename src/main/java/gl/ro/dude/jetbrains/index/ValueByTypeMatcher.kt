package gl.ro.dude.jetbrains.index

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.go.TypeRetrieverImpl
import gl.ro.dude.domain.retriever.ValueName

object ValueByTypeMatcher {
    private val RETRIEVER: IRetriever = TypeRetrieverImpl

    @Suppress("RemoveExplicitTypeArguments")
    private val EMPTY: Iterable<ValueName> by lazy { listOf<ValueName>() }

    fun byElement(e: PsiElement): Iterable<String> {
        return if (RETRIEVER.suits(e))
            ValuesByTypeIterator(e.project)
                .filter(RETRIEVER.getFilter(e) ?: return EMPTY)
                .flatMap(RETRIEVER.getMap(e) ?: return EMPTY)
        else
            EMPTY
    }
}