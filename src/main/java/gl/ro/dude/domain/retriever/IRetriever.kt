package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias FilterPredicate = ((TypeValues) -> Boolean)?
typealias MapPredicate = ((TypeValues) -> Iterable<String>)?

/**
 * Filters TypeValues and maps them to completion strings for suitable PsiElements.
 */
interface IRetriever {
    /**
     * This MUST be called first.
     */
    fun suits(e: PsiElement): Boolean
    fun getFilter(e: PsiElement): FilterPredicate
    fun getMap(e: PsiElement): MapPredicate
}