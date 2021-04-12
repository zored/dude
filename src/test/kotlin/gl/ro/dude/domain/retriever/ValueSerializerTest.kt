package gl.ro.dude.domain.retriever

import junit.framework.TestCase

class ValueSerializerTest : TestCase() {
    fun testAll() {
        val serializer = ValueSerializer()
        val input = Value.create("bob", "Person", Type.VARIABLE, 5)
        val output = serializer.deserialize(serializer.serialize(input))
        assertEquals(input, output)
        assertEquals(input.occurrences, output?.occurrences)

        input.occurrences++
        assertEquals(input, output)
    }
}
