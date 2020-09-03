package gl.ro.dude.jetbrains.index

import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.ValueName
import gl.ro.dude.domain.retriever.go.TypeRetrieverImpl

object PsiElementCompletionsFactory {
    private val RETRIEVER: IRetriever = TypeRetrieverImpl

    @Suppress("RemoveExplicitTypeArguments")
    private val EMPTY: Iterable<ValueName> by lazy { listOf<ValueName>() }

    fun create(e: PsiElement): Iterable<String> {
        return if (RETRIEVER.suits(e))
            ValuesByTypeIterator(e.project).fold(
                listOf(),
                RETRIEVER.getFolder(e) ?: return EMPTY
            )
        else
            EMPTY
    }
}