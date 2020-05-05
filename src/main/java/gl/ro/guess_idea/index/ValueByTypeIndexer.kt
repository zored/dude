package gl.ro.guess_idea.index

import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.guess_idea.index.visitors.GoVisitor

object ValueByTypeIndexer : DataIndexer<Type, Values, FileContent> {
    private val EMPTY by lazy { mapOf<Type, Values>() }
    override fun map(inputData: FileContent): Map<Type, Values> {
        if (!isProjectFile(inputData)) {
            return EMPTY
        }

        val valuesByType = MutableValuesByType()
        val visitor = GoVisitor { type, value -> valuesByType[type] = value }
        if (visitor.suitsFile(inputData.fileType)) {
            inputData.psiFile.accept(visitor)
        }
        return valuesByType.toMap()
    }

    // todo any better filter?
    private fun isProjectFile(inputData: FileContent): Boolean {
        val projectPath = inputData.psiFile.project.basePath ?: return true
        return inputData.file.path.substring(0, projectPath.length) == projectPath
    }
}