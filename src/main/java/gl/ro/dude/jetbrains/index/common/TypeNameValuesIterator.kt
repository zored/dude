package gl.ro.dude.jetbrains.index.common

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.TypeNameValues
import gl.ro.dude.domain.retriever.Values
import gl.ro.dude.jetbrains.index.ValuesIndexExtension

class TypeNameValuesIterator(private val project: Project) : Iterable<TypeNameValues> {
    private val id = ValuesIndexExtension.NAME

    override fun iterator(): Iterator<TypeNameValues> {
        val list = mutableListOf<TypeNameValues>()
        this.each { key, values -> list.add(Pair(key, values)) }
        return list.iterator()
    }

    private fun each(eachCallback: (key: Type, values: Values) -> Unit) =
        getAllKeys().forEach { key ->
            getIndex().processValues<Type, Values>(
                id,
                key,
                null,
                { _: VirtualFile, value: Values -> eachCallback(key, value); true },
                GlobalSearchScope.allScope(project)
            )
            Unit
        }

    private fun getAllKeys(): Set<Type> {
        val keys = mutableSetOf<Type>()
        getIndex().processAllKeys(id, { key -> keys.add(key); true }, project)
        return keys
    }

    private fun getIndex() = FileBasedIndex.getInstance()
}
