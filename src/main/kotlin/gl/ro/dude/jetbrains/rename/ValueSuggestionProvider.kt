package gl.ro.dude.jetbrains.rename

import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.SuggestedNameInfo
import com.intellij.refactoring.rename.NameSuggestionProvider
import gl.ro.dude.jetbrains.index.common.PsiElementCompletionsFactory

class ValueSuggestionProvider : NameSuggestionProvider {
    override fun getSuggestedNames(
        element: PsiElement,
        nameSuggestionContext: PsiElement?,
        result: MutableSet<String>
    ): SuggestedNameInfo? {
        result.addAll(PsiElementCompletionsFactory.createStrings(element) { it.name })
        return null
    }
}
