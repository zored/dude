package gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import main.java.gl.ro.guess_idea.completion.ValueCompletionProvider

class ValueCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), ValueCompletionProvider)
    }
}