package gl.ro.dude.jetbrains.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.dude.domain.retriever.TypeName
import gl.ro.dude.domain.retriever.TypeValues
import gl.ro.dude.domain.retriever.ValueNames

class ValuesByTypeIterator(private val project: Project) : Iterable<TypeValues> {
    private val id = ValueByTypeIndexExtension.NAME

    override fun iterator(): Iterator<TypeValues> {
        val list = mutableListOf<TypeValues>()
        this.each { key, values -> list.add(Pair(key, values)) }
        return list.iterator()
    }

    private fun each(eachCallback: (key: TypeName, value: ValueNames) -> Unit) {
        getAllKeys().forEach { key ->
            getIndex().processValues(
                id,
                key,
                null,
                { _: VirtualFile, value: ValueNames -> eachCallback(key, value); true },
                GlobalSearchScope.allScope(project)
            )
            Unit
        }
    }

    private fun getAllKeys(): Set<TypeName> {
        val keys = mutableSetOf<TypeName>()
        getIndex().processAllKeys(id, { key -> keys.add(key); true }, project)
        return keys
    }

    private fun getIndex() = FileBasedIndex.getInstance()
}