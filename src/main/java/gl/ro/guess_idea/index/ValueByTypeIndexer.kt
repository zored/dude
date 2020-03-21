package gl.ro.guess_idea.index

import com.goide.psi.GoFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.guess_idea.index.visitors.GoVisitor

object ValueByTypeIndexer : DataIndexer<Type, Values, FileContent> {
    override fun map(inputData: FileContent): Map<Type, Values> {
        val valuesByType = MutableValuesByType()
        val file = inputData.psiFile
        if (file is GoFile) {
            fillGo(valuesByType, file)
        }
        return valuesByType.toMap()
    }

    private fun fillGo(map: MutableValuesByType, file: GoFile) {
        file.accept(GoVisitor { type, value -> map[type] = value })
    }
}