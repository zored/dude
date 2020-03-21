package gl.ro.guess_idea.domain.type_retriever

import com.intellij.psi.PsiElement
import gl.ro.guess_idea.domain.type_retriever.go.DeclarationRetriever
import gl.ro.guess_idea.index.Type

object TypeRetrieverImpl: IRetriever {
    private val RETRIEVERS = DeclarationRetriever.ALL // todo add alias support

    override fun get(position: PsiElement): Type? {
        return findRetriever(position)?.get(position)
    }

    override fun suits(position: PsiElement): Boolean {
        return findRetriever(position) !== null
    }

    private fun findRetriever(position: PsiElement) = RETRIEVERS.firstOrNull { it.suits(position) }
}