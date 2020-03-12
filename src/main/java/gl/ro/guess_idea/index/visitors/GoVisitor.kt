package gl.ro.guess_idea.index.visitors

import com.goide.psi.*
import com.goide.psi.GoVisitor
import gl.ro.guess_idea.index.ValuesByType

class GoVisitor(val map: ValuesByType) : GoRecursiveVisitor() {

    override fun visitConstDeclaration(o: GoConstDeclaration) {
        super.visitConstDeclaration(o)
    }

    override fun visitImportDeclaration(o: GoImportDeclaration) {
        super.visitImportDeclaration(o)
    }

    override fun visitParameterDeclaration(o: GoParameterDeclaration) {
        super.visitParameterDeclaration(o)
    }

    override fun visitTopLevelDeclaration(o: GoTopLevelDeclaration) {
        super.visitTopLevelDeclaration(o)
    }

    override fun visitVarDeclaration(o: GoVarDeclaration) {
        super.visitVarDeclaration(o)
    }

    override fun visitShortVarDeclaration(o: GoShortVarDeclaration) {
        super.visitShortVarDeclaration(o)
    }

    override fun visitFieldDeclaration(o: GoFieldDeclaration) {
        super.visitFieldDeclaration(o)
    }

    override fun visitTypeDeclaration(o: GoTypeDeclaration) {
        super.visitTypeDeclaration(o)
    }

    override fun visitFunctionOrMethodDeclaration(o: GoFunctionOrMethodDeclaration) {
        super.visitFunctionOrMethodDeclaration(o)
    }

    override fun visitFunctionDeclaration(o: GoFunctionDeclaration) {
        super.visitFunctionDeclaration(o)
    }

    override fun visitVarOrConstDeclaration(o: GoVarOrConstDeclaration<out GoVarOrConstSpec<*>>) {
        super.visitVarOrConstDeclaration(o)
    }

    override fun visitMethodDeclaration(o: GoMethodDeclaration) {
        super.visitMethodDeclaration(o)
    }
}