package gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns

class ValueCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), ValueCompletionProvider)
    }
}