package gl.ro.dude.jetbrains.index

import gl.ro.dude.domain.retriever.TypeName
import gl.ro.dude.domain.retriever.ValueName
import gl.ro.dude.domain.retriever.ValueNames

class MutableValuesByType {
    private var map = mutableMapOf<TypeName, ValueNames>()

    operator fun set(t: TypeName, v: ValueName): Boolean {
        return getOrCreateValues(t).set.add(v)
    }

    private fun getOrCreateValues(t: TypeName): ValueNames {
        val values = map[t] ?: ValueNames.empty()
        map[t] = values
        return values
    }

    fun toMap(): Map<TypeName, ValueNames> {
        return map
    }
}