package gl.ro.guess_idea.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import main.java.gl.ro.guess_idea.completion.ValueCompletionProvider
import main.java.gl.ro.guess_idea.index.ValueByTypeRetriever

class ValueCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), ValueCompletionProvider)
    }
}