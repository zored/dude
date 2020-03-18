package gl.ro.guess_idea.index

import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.DataInput
import java.io.DataOutput

object ValuesExternalizer : DataExternalizer<Values> {
    private val ENUM = EnumeratorStringDescriptor.INSTANCE
    private val JSON = Json(JsonConfiguration.Stable)
    private val VALUES = Values.serializer()

    override fun save(out: DataOutput, value: Values?) {
        ENUM.save(
            out,
            if (value == null) ""
            else JSON.stringify(VALUES, value)
        )
    }

    override fun read(d: DataInput): Values {
        return JSON.parse(VALUES, ENUM.read(d))
    }
}