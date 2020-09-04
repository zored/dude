package gl.ro.dude.domain.retriever

data class Values(private val set: MutableSet<Value>) : Iterable<Value> {
    companion object {
        private const val delimiter = ","
        private val valueSerializer = ValueSerializer()

        fun empty(): Values = Values(mutableSetOf())

        fun stringify(values: Values?): String =
            values?.set
                ?.joinToString(delimiter)
                { valueSerializer.serialize(it) } ?: ""

        fun fromString(str: String): Values =
            Values(
                str.splitToSequence(delimiter)
                    .map { valueSerializer.deserialize(it) }
                    .filter { it != null }
                    .fold(mutableSetOf(), { set, value ->
                        if (value != null) {
                            set += value
                        };
                        set
                    })
            )
    }

    override fun iterator() = set.iterator()

    fun add(v: Value): Boolean =
        if (set.contains(v)) {
            set.first { it == v }.occurrences++
            true
        } else {
            set.add(v)
            false
        }
}
