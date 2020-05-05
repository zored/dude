package gl.ro.guess_idea.domain.retriever

import com.intellij.psi.PsiElement
import gl.ro.guess_idea.index.Type
import gl.ro.guess_idea.index.Values

typealias FilterPredicate = ((Pair<Type, Values>) -> Boolean)?
typealias MapPredicate = ((Pair<Type, Values>) -> Iterable<String>)?

interface IRetriever {
    fun getFilter(e: PsiElement): FilterPredicate
    fun suits(e: PsiElement): Boolean
    fun getMap(e: PsiElement): MapPredicate
}