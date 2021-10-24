package gl.ro.dude.jetbrains.completion

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import gl.ro.dude.jetbrains.index.common.PsiElementCompletionsFactory

class ValueCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) = result.addAllElements(PsiElementCompletionsFactory.createLookupElements(parameters.position))
            }
        )
    }
}
