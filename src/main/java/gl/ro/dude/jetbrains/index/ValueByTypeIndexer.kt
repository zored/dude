package gl.ro.dude.jetbrains.index

import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Values
import gl.ro.dude.jetbrains.index.visitors.GoVisitor

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