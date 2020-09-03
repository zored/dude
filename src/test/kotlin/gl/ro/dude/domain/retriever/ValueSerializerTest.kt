package gl.ro.dude.domain.retriever

import junit.framework.TestCase

class ValueSerializerTest: TestCase() {
    fun testAll() {
        val serializer = ValueSerializer()
        val input = Value("bob", "Person", Type.VARIABLE)
        val output = serializer.deserialize(serializer.serialize(input))
        assertEquals(input, output)
    }
}