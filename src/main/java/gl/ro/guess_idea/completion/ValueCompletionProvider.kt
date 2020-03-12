package main.java.gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import main.java.gl.ro.guess_idea.index.ValuesByTypeIterator

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val items = valuesByType(parameters).map { (key, value) -> createLookupElement(key, value) }
        result.addAllElements(items)
    }

    private fun createLookupElement(key: String, value: String) =
        LookupElementBuilder.create(key).appendTailText(value, true)

    private fun valuesByType(parameters: CompletionParameters) = ValuesByTypeIterator(parameters.position.project)


}