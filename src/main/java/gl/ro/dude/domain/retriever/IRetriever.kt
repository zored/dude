package gl.ro.dude.domain.retriever

import com.intellij.psi.PsiElement

typealias FilterPredicate = ((TypeNameValues) -> Boolean)?
typealias MapPredicate = ((TypeNameValues) -> Iterable<String>)?
typealias Folder = ((List<String>, TypeNameValues) -> List<String>)?

/**
 * Filters TypeValues and maps them to completion strings for suitable PsiElements.
 */
interface IRetriever {
    /**
     * This MUST be called first.
     */
    fun suits(e: PsiElement): Boolean
    fun getFolder(e: PsiElement): Folder
}