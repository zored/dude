package gl.ro.dude.jetbrains.index

import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import gl.ro.dude.domain.retriever.ValueNames
import java.io.DataInput
import java.io.DataOutput

object ValuesExternalizer : DataExternalizer<ValueNames> {
    private val ENUM = EnumeratorStringDescriptor.INSTANCE

    override fun save(out: DataOutput, valueNames: ValueNames?) = ENUM.save(out, ValueNames.stringify(valueNames))
    override fun read(d: DataInput): ValueNames = ValueNames.fromString(ENUM.read(d))
}