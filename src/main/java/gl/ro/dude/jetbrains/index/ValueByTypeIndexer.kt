package gl.ro.dude.jetbrains.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Value
import gl.ro.dude.domain.retriever.Values
import gl.ro.dude.jetbrains.index.visitors.GoVisitor

object ValueByTypeIndexer : DataIndexer<Type, Values, FileContent> {
    private val EMPTY by lazy { mapOf<Type, Values>() }
    private val roots = hashMapOf<Project, String>()

    override fun map(content: FileContent): Map<Type, Values> {
        if (!isProjectFile(content)) {
            return EMPTY
        }

        // Retrieve values from Go file:
        val result = MutableValuesByType()
        val visitor = GoVisitor { type, value, source -> result[source] = Value(value, type, source) }
        if (visitor.suitsFile(content.fileType)) {
            content.psiFile.accept(visitor)
        }
        return result.toMap()
    }

    // TODO: better way? settings?
    private fun isProjectFile(content: FileContent): Boolean {
        val project = content.psiFile.project
        val root =
            roots[project]
                ?: project.guessProjectDir()?.path
                ?: return true
        roots[project] = root
        return content.file.path.substring(0, root.length) == root
    }
}
