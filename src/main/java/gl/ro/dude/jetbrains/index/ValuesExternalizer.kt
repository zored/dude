package gl.ro.dude.jetbrains.index

import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import gl.ro.dude.domain.retriever.Values
import java.io.DataInput
import java.io.DataOutput

object ValuesExternalizer : DataExternalizer<Values> {
    private val ENUM = EnumeratorStringDescriptor.INSTANCE

    override fun save(out: DataOutput, values: Values?) = ENUM.save(out, Values.stringify(values))
    override fun read(d: DataInput): Values = Values.fromString(ENUM.read(d))
}
