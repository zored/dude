package gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import gl.ro.guess_idea.index.Type
import gl.ro.guess_idea.index.Value
import gl.ro.guess_idea.index.ValuesByTypeIterator

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val items = valuesByType(parameters).flatMap { (key, values) ->
            values.set.map { value ->
                createLookupElement(
                    key,
                    value
                )
            }
        }
        result.addAllElements(items)
    }

    private fun createLookupElement(key: Type, value: Value) =
        LookupElementBuilder.create(key).appendTailText(value, true)

    private fun valuesByType(parameters: CompletionParameters) = ValuesByTypeIterator(parameters.position.project)


}