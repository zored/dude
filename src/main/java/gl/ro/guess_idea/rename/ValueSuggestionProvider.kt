package gl.ro.guess_idea.rename

import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.SuggestedNameInfo
import com.intellij.refactoring.rename.NameSuggestionProvider
import gl.ro.guess_idea.index.ValueByTypeMatcher

class ValueSuggestionProvider : NameSuggestionProvider {
    override fun getSuggestedNames(
        element: PsiElement,
        nameSuggestionContext: PsiElement?,
        result: MutableSet<String>
    ): SuggestedNameInfo? {
        ValueByTypeMatcher.byElement(element).forEach { result.add(it) }
        return null
    }
}