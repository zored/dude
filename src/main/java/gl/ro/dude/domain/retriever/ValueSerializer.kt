package gl.ro.dude.domain.retriever

class ValueSerializer {
    private val delimiter = "|"

    fun serialize(value: Value): String = listOf(
        value.name,
        value.type.name,
        value.typeName,
        value.occurrences,
    ).joinToString(delimiter)

    fun deserialize(value: String): Value? {
        val parts = value.split(delimiter)
        if (parts.size != 4) {
            return null
        }
        val (valueName, typeString, typeName, occurrences) = parts

        return try {
            Value.create(
                valueName,
                typeName,
                Type.valueOf(typeString),
                occurrences.toInt()
            )
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}