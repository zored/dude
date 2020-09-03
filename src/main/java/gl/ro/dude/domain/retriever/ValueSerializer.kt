package gl.ro.dude.domain.retriever

import java.lang.IllegalArgumentException

class ValueSerializer {
    private val delimiter = "|"

    fun serialize(value: Value): String = listOf(value.name, value.type.name, value.typeName).joinToString(delimiter)

    fun deserialize(value: String): Value? {
        val parts = value.split(delimiter)
        if (parts.size != 3) {
            return null
        }
        val (valueName, typeString, typeName) = parts
        val type: Type
        try {
            type = Type.valueOf(typeString)
        } catch (e: IllegalArgumentException) {
            return null
        }
        return Value(valueName, typeName, type)
    }
}