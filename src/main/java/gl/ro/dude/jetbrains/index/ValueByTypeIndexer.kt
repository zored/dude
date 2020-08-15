package gl.ro.dude.jetbrains.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.dude.domain.retriever.TypeName
import gl.ro.dude.domain.retriever.ValueNames
import gl.ro.dude.jetbrains.index.visitors.GoVisitor

object ValueByTypeIndexer : DataIndexer<TypeName, ValueNames, FileContent> {
    private val EMPTY by lazy { mapOf<TypeName, ValueNames>() }
    private val roots = hashMapOf<Project, String>()

    override fun map(inputData: FileContent): Map<TypeName, ValueNames> {
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

    // TODO: better way? settings?
    private fun isProjectFile(inputData: FileContent): Boolean {
        val project = inputData.psiFile.project
        val root =
            roots[project]
                ?: project.guessProjectDir()?.path
                ?: return true
        roots[project] = root
        return inputData.file.path.substring(0, root.length) == root
    }
}