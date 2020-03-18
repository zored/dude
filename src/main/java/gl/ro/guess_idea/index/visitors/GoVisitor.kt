package gl.ro.guess_idea.index.visitors

import com.goide.psi.*
import gl.ro.guess_idea.index.ValuesByType

class GoVisitor(private val valuesByType: ValuesByType) : GoRecursiveVisitor() {
    override fun visitConstDeclaration(o: GoConstDeclaration) {
        super.visitConstDeclaration(o) // todo
    }

    override fun visitImportDeclaration(o: GoImportDeclaration) {
        super.visitImportDeclaration(o) // todo
    }

    override fun visitParameterDeclaration(o: GoParameterDeclaration) {
        val type = o.type?.text ?: return
        o.paramDefinitionList.forEach { valuesByType[it.text] = type }
    }

    override fun visitVarDeclaration(o: GoVarDeclaration) {
        super.visitVarDeclaration(o) // todo
    }

    // todo a lot of errors here
//    override fun visitShortVarDeclaration(o: GoShortVarDeclaration) {
//        o.varDefinitionList.forEachIndexed { i, d ->
//            val type = d.text
//            val value = o.rightExpressionsList[i].text
//            valuesByType[type] = value
//        }
//    }

    override fun visitFieldDeclaration(o: GoFieldDeclaration) {
        val type = o.type?.text ?: return
        o.fieldDefinitionList.map{valuesByType[type] = it.text}
    }

    override fun visitVarOrConstDeclaration(o: GoVarOrConstDeclaration<out GoVarOrConstSpec<*>>) {
        super.visitVarOrConstDeclaration(o) // todo
    }

    override fun visitMethodDeclaration(o: GoMethodDeclaration) {
        super.visitMethodDeclaration(o) // todo
    }
}