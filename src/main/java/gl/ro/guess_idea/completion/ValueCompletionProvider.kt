package gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import gl.ro.guess_idea.completion.type_retriever.IRetriever
import gl.ro.guess_idea.completion.type_retriever.go.DeclarationRetriever
import gl.ro.guess_idea.index.Type
import gl.ro.guess_idea.index.Value
import gl.ro.guess_idea.index.ValuesByTypeIterator

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    private val RETRIEVERS = DeclarationRetriever.ALL // todo add alias support

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val position = parameters.position
        val type = RETRIEVERS.firstOrNull { it.suits(position) }?.get(position) ?: return

        result.addAllElements(createElements(position, type))
    }

    private fun createElements(
        position: PsiElement,
        expectedType: Type
    ): Iterable<LookupElementBuilder> =
        ValuesByTypeIterator(position.project)
            .filter { (type, _) -> type == expectedType }
            .flatMap { (_, values) -> values.set.map { value -> createElement(value) } }

    private fun createElement(value: Value) = LookupElementBuilder.create(value)
}