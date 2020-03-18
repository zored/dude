package gl.ro.guess_idea.index

import kotlinx.serialization.Serializable

typealias Type = String
typealias Value = String

@Serializable
data class Values(val set: MutableSet<Value>) {
    companion object {
        fun empty(): Values {
            return Values(mutableSetOf())
        }
    }
}

