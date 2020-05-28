package gl.ro.dude.domain.retriever

import kotlinx.serialization.Serializable

@Serializable
data class Values(val set: MutableSet<Value>) : Iterable<Value> {
    companion object {
        private val delimiter = ","

        fun empty(): Values {
            return Values(mutableSetOf())
        }


        fun stringify(values: Values?): String = values?.set?.joinToString(
            delimiter
        ) ?: ""
        fun fromString(str: String): Values =
            Values(
                str.splitToSequence(delimiter)
                    .toMutableSet()
            )
    }

    override fun iterator(): Iterator<Value> {
        return set.iterator()
    }

}