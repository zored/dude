package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias Folder = ((List<Value>, TypeNameValues) -> List<Value>)
typealias OptionalFolder = Folder?

interface ICompletionsRetriever {
    fun getFolder(e: PsiElement): OptionalFolder
}