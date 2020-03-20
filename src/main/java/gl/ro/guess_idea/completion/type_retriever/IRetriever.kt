package gl.ro.guess_idea.completion.type_retriever

import com.intellij.psi.PsiElement
import gl.ro.guess_idea.index.Type

interface IRetriever {
    fun get(position: PsiElement): Type?
    fun suits(position: PsiElement): Boolean
}