package gl.ro.dude.jetbrains.index.visitors

import com.goide.GoFileType
import com.goide.psi.*
import com.goide.psi.impl.GoConstSpecImpl
import com.goide.psi.impl.GoVarSpecImpl
import com.intellij.openapi.fileTypes.FileType
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Value

class GoVisitor(private val typeValue: (type: Type, value: Value) -> Unit) : GoRecursiveVisitor() {
    fun suitsFile(t: FileType) = t == GoFileType.INSTANCE

    override fun visitParameterDeclaration(o: GoParameterDeclaration) {
        val type = o.type?.text ?: return
        o.paramDefinitionList.forEach { typeValue(type, it.text) }
    }

    override fun visitFieldDeclaration(o: GoFieldDeclaration) {
        val type = o.type?.text ?: return
        o.fieldDefinitionList.map { typeValue(type, it.text) }
    }

    override fun visitVarOrConstDeclaration(o: GoVarOrConstDeclaration<out GoVarOrConstSpec<*>>) {
        o.children.forEach { declaration ->
            when (declaration) {
                is GoConstSpecImpl -> typeValue(
                    declaration.type?.text ?: return,
                    declaration.name ?: return
                )
                is GoVarSpecImpl -> declaration.varDefinitionList.forEach { definition ->
                    typeValue(
                        definition.getGoType(null)?.typeReferenceExpression?.text ?: return,
                        definition.name ?: return
                    )
                }
            }
        }
    }

    override fun visitMethodDeclaration(o: GoMethodDeclaration) {
        typeValue(
            o.receiverType?.text ?: return,
            o.receiver?.text ?: return
        )
    }

    override fun visitImportSpec(o: GoImportSpec) {
        typeValue(o.stringLiteral.value.toString(), o.alias ?: return)
    }
}