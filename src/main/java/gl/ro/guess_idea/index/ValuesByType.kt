package gl.ro.guess_idea.index

class ValuesByType {
    private var map = mutableMapOf<Type, Values>()

    operator fun set(t: Type, v: Value): Boolean {
        return getOrCreateValues(t).set.add(v)
    }

    private fun getOrCreateValues(t: Type): Values {
        val values = map[t] ?: Values.empty()
        map[t] = values
        return values
    }

    fun toMap(): Map<Type, Values> {
        return map
    }
}