package gl.ro.guess_idea.index

import com.intellij.json.JsonUtil
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import java.util.*

// Indexes values by types.
class ValueByTypeIndexer : DataIndexer<String, String, FileContent> {
    override fun map(inputData: FileContent): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        val file = inputData.psiFile
        if (file is JsonFile) {
            fillJson(map, file)
        }
        return map
    }

    private fun fillJson(
        map: MutableMap<String, String>,
        file: JsonFile
    ) {
        val jsonObject = JsonUtil.getTopLevelObject(file) ?: return
        jsonObject.accept(object : JsonRecursiveElementVisitor() {
            override fun visitProperty(o: JsonProperty) {
                val value = o.value?.text ?: return
                val type = o.name
                map[type] = value
            }
        })
    }

    companion object {
        var INSTANCE = ValueByTypeIndexer()
    }
}