package gl.ro.dude.domain.retriever.go

import com.goide.GoTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import gl.ro.dude.domain.retriever.FilterPredicate
import gl.ro.dude.domain.retriever.IRetriever
import gl.ro.dude.domain.retriever.MapPredicate

object IdentifierRetriever : IRetriever {
    override fun getFilter(e: PsiElement): FilterPredicate = { true }
    override fun suits(e: PsiElement): Boolean = e.elementType === GoTypes.IDENTIFIER
    override fun getMap(e: PsiElement): MapPredicate = { (type, values) -> values.map { "$it $type" } }
}