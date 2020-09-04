package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias OptionalFolder = ((List<Value>, TypeNameValues) -> List<Value>)?

interface ICompletionsRetriever {
    fun getFolder(e: PsiElement): OptionalFolder
}