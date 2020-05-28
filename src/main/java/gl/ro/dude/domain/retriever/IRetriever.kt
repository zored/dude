package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias FilterPredicate = ((Pair<Type, Values>) -> Boolean)?
typealias MapPredicate = ((Pair<Type, Values>) -> Iterable<String>)?

interface IRetriever {
    fun getFilter(e: PsiElement): FilterPredicate
    fun suits(e: PsiElement): Boolean
    fun getMap(e: PsiElement): MapPredicate
}