package gl.ro.dude.jetbrains.index.common

import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Value
import gl.ro.dude.domain.retriever.Values

class MutableValuesByType {
    private var map = mutableMapOf<Type, Values>()

    operator fun set(t: Type, v: Value): Boolean =
        getOrCreateValues(t).add(v)

    private fun getOrCreateValues(t: Type): Values {
        val values = map[t] ?: Values.empty()
        map[t] = values
        return values
    }

    fun toMap(): Map<Type, Values> =
        map
}
