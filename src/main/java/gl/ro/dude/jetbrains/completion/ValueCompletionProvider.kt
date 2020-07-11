package gl.ro.dude.jetbrains.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import gl.ro.dude.jetbrains.index.ValueByTypeMatcher

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) = result.addAllElements(
        ValueByTypeMatcher.byElement(parameters.position).map {
            LookupElementBuilder.create(it)
        }
    )
}