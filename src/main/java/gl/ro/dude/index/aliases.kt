package gl.ro.dude.index

import kotlinx.serialization.Serializable

typealias Type = String
typealias Value = String

@Serializable
data class Values(val set: MutableSet<Value>): Iterable<Value> {
    companion object {
        fun empty(): Values {
            return Values(mutableSetOf())
        }
    }

    override fun iterator(): Iterator<Value> {
        return set.iterator()
    }
}

