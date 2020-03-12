package main.java.gl.ro.guess_idea.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.guess_idea.index.ValueByTypeIndexExtension

class ValuesByTypeIterator(private val project: Project) : Iterable<Pair<String, String>> {
    private val id = ValueByTypeIndexExtension.NAME

    private fun each(f: (key: String, value: String) -> Unit) {
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

    override fun iterator(): Iterator<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        each { key, value -> list.add(Pair(key, value)) }
        return list.listIterator()
    }
}