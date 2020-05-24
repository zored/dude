package gl.ro.dude.index

import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.dude.index.visitors.GoVisitor

object ValueByTypeIndexer : DataIndexer<Type, Values, FileContent> {
    override fun map(inputData: FileContent): Map<Type, Values> {
        // TODO: check if project file.
        val valuesByType = MutableValuesByType()
        val visitor = GoVisitor { type, value -> valuesByType[type] = value }
        if (visitor.suitsFile(inputData.fileType)) {
            inputData.psiFile.accept(visitor)
        }
        return valuesByType.toMap()
    }
}