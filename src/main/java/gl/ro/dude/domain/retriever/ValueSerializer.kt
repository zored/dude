package gl.ro.dude.domain.retriever

import java.lang.IllegalArgumentException

class ValueSerializer {
    private val delimiter = "|"

    fun serialize(value: Value): String = listOf(value.value, value.type.name, value.typeName).joinToString(delimiter)

    fun deserialize(value: String): Value? {
        val (valueName, typeString, typeName) = value.split(delimiter)
        val type: Type
        try {
            type = Type.valueOf(typeString)
        } catch (e: IllegalArgumentException) {
            return null
        }
        return Value(valueName, typeName, type)
    }
}