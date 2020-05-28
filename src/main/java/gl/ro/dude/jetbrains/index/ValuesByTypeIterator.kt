package gl.ro.dude.jetbrains.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Values

class ValuesByTypeIterator(private val project: Project) : Iterable<Pair<Type, Values>> {
    private val id = ValueByTypeIndexExtension.NAME

    private fun each(f: (key: Type, value: Values) -> Unit) {
        val keys = mutableSetOf<Type>()
        val index = FileBasedIndex.getInstance()

        index.processAllKeys(id, { key -> keys.add(key); true }, project)
        keys.forEach { key ->
            index.processValues(
                id,
                key,
                null,
                { _: VirtualFile, value: Values -> f(key, value); true },
                GlobalSearchScope.allScope(project)
            )
            Unit
        }
    }

    override fun iterator(): Iterator<Pair<Type, Values>> {
        val list = mutableListOf<Pair<Type, Values>>()
        each { key, values -> list.add(Pair(key, values)) }
        return list.listIterator()
    }
}