package gl.ro.dude.jetbrains.index

import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import gl.ro.dude.domain.retriever.Folder
import gl.ro.dude.domain.retriever.ICompletionsRetriever
import gl.ro.dude.domain.retriever.Value
import gl.ro.dude.domain.retriever.go.TypeRetrieverImpl

object PsiElementCompletionsFactory {
    private val RETRIEVER: ICompletionsRetriever = TypeRetrieverImpl

    fun createStrings(e: PsiElement, nameRetriever: (Value) -> String): Iterable<String> {
        val operation: Folder = RETRIEVER.getFolder(e) ?: return listOf()
        return TypeNameValuesIterator(e.project)
            .fold(
                listOf(), operation
            )
            .asSequence()
            .groupBy(nameRetriever)
            .map {
                Pair(
                    it.key,
                    it.value.fold(0, { total, v -> total + v.occurrences })
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
