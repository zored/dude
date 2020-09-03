package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias OptionalFolder = ((List<String>, TypeNameValues) -> List<String>)?

interface ICompletionsRetriever {
    fun getFolder(e: PsiElement): OptionalFolder
}