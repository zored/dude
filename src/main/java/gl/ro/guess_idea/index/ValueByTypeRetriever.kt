package main.java.gl.ro.guess_idea.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import com.sun.tools.javac.util.List
import com.sun.tools.javac.util.ListBuffer
import gl.ro.guess_idea.index.ValueByTypeIndexExtension

class ValueByTypeRetriever(private val project: Project) {
    private val id = ValueByTypeIndexExtension.NAME
    fun <T> map(apply: (key: String, value: String) -> T): List<T>? {
        val result = ListBuffer<T>()
        forEach { key, value -> result.append(apply(key, value)) }
        return result.toList()
    }

    fun forEach(f: (key: String, value: String) -> Unit) {
        val keys = HashSet<String>()
        val index = FileBasedIndex.getInstance()

        index.processAllKeys(id, { key -> keys.add(key); true }, project)
        keys.forEach { key ->
            index.processValues(
                id,
                key,
                null,
                { _: VirtualFile, value: String -> f(key, value); true },
                GlobalSearchScope.allScope(project)
            )
        }
    }
}