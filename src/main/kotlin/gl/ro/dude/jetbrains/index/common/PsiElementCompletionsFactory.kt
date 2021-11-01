package gl.ro.dude.jetbrains.index.common

import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.Folder
import gl.ro.dude.domain.retriever.ICompletionsRetriever
import gl.ro.dude.domain.retriever.Value
import gl.ro.dude.domain.retriever.go.TypeRetrieverImpl

object PsiElementCompletionsFactory {
    private val retriever: ICompletionsRetriever = TypeRetrieverImpl

    fun createStrings(e: PsiElement, nameRetriever: (Value) -> String): Iterable<String> {
        val operation: Folder = retriever.getFolder(e) ?: return listOf()

        @Suppress("RedundantAsSequence")
        return TypeNameValuesIterator(e.project)
            .asSequence()
            .fold(listOf(), operation)
            .groupBy(nameRetriever)
            .map {
                Pair(
                    it.key,
                    it.value.fold(0) { total, v -> total + v.occurrences }
                )
            }
            .sortedWith { (_, a), (_, b) -> b - a }
            .map { (s) -> s }
    }

    fun createLookupElements(e: PsiElement): Iterable<LookupElement> =
        createStrings(e) { it.getVisibleName() }
            .map { LookupElementBuilder.create(it) }
            .mapIndexed { i, v -> PrioritizedLookupElement.withPriority(v, -i.toDouble()) }
}
