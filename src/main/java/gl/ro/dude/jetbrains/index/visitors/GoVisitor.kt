package gl.ro.dude.jetbrains.index.visitors

import com.goide.GoFileType
import com.goide.psi.*
import com.goide.psi.impl.GoConstSpecImpl
import com.goide.psi.impl.GoVarSpecImpl
import com.intellij.openapi.fileTypes.FileType
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.TypeName
import gl.ro.dude.domain.retriever.ValueName

class GoVisitor(private val visit: (typeName: TypeName, value: ValueName, type: Type) -> Unit) : GoRecursiveVisitor() {
    fun suitsFile(t: FileType) = t == GoFileType.INSTANCE

    override fun visitParameterDeclaration(o: GoParameterDeclaration) {
        val type = o.type?.text ?: return
        o.paramDefinitionList.forEach { visit(type, it.text, Type.VARIABLE) }
    }

    override fun visitFieldDeclaration(o: GoFieldDeclaration) {
        val type = o.type?.text ?: return
        o.fieldDefinitionList.map { visit(type, it.text, Type.VARIABLE) }
    }

    override fun visitVarOrConstDeclaration(o: GoVarOrConstDeclaration<out GoVarOrConstSpec<*>>) {
        o.children.forEach { declaration ->
            when (declaration) {
                is GoConstSpecImpl -> visit(
                    declaration.type?.text ?: return,
                    declaration.name ?: return,
                    Type.VARIABLE
                )
                is GoVarSpecImpl -> declaration.varDefinitionList.forEach { definition ->
                    visit(
                        definition.getGoType(null)?.typeReferenceExpression?.text ?: return,
                        definition.name ?: return,
                        Type.VARIABLE
                    )
                }
            }
        }
    }

    override fun visitMethodDeclaration(o: GoMethodDeclaration) {
        visit(
            o.receiverType?.text ?: return,
            o.receiver?.text ?: return,
            Type.VARIABLE
        )
    }

    override fun visitImportSpec(o: GoImportSpec) {
        visit(
            o.stringLiteral.value.toString(),
            o.alias ?: return,
            Type.IMPORT
        )
    }
}
