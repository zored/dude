package gl.ro.dude.domain.retriever

data class ValueNames(val set: MutableSet<ValueName>) : Iterable<ValueName> {
    companion object {
        private const val delimiter = ","

        fun empty(): ValueNames {
            return ValueNames(mutableSetOf())
        }

        fun stringify(valueNames: ValueNames?): String = valueNames?.set?.joinToString(
            delimiter
        ) ?: ""

        fun fromString(str: String): ValueNames =
            ValueNames(
                str.splitToSequence(delimiter)
                    .toMutableSet()
            )
    }

    override fun iterator() = set.iterator()
}
