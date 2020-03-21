package gl.ro.guess_idea.index

import com.intellij.psi.PsiElement
import gl.ro.guess_idea.domain.type_retriever.TypeRetrieverImpl

object ValueByTypeMatcher {
    fun byElement(element: PsiElement): Iterable<Value> {
        if (!TypeRetrieverImpl.suits(element)) {
            return listOf()
        }

        val expectedType = TypeRetrieverImpl.get(element) ?: return listOf()
        return ValuesByTypeIterator(element.project)
            .filter { (type) -> type == expectedType }
            .flatMap { (_, values) -> values }
    }
}