package gl.ro.dude.domain.retriever

data class Value(
    val name: ValueName,
    val typeName: TypeName,
    val type: Type,
) {
    companion object {
        fun create(
            name: ValueName,
            typeName: TypeName,
            type: Type,
            occurrences: Int = 1
        ): Value {
            val v = Value(name, typeName, type)
            v.occurrences = occurrences
            return v
        }
    }

    fun getVisibleName(): String = when (type) {
        Type.IMPORT -> "$name \"$typeName\""
        else -> "$name $typeName"
    }
    var occurrences: Int = 1
}
