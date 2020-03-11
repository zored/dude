package main.java.gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import main.java.gl.ro.guess_idea.index.ValueByTypeRetriever

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        result.addAllElements(
            ValueByTypeRetriever(parameters.position.project)
                .map { key, value ->
                    LookupElementBuilder.create(key).appendTailText(value, true)
                } !!
        )
    }
}