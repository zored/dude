package gl.ro.guess_idea.index

import com.goide.psi.*
import com.intellij.json.JsonUtil
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.guess_idea.index.visitors.GoVisitor

// Indexes values by types.
object ValueByTypeIndexer : DataIndexer<String, String, FileContent> {
    override fun map(inputData: FileContent): Map<String, String> {
        val valuesByType = ValuesByType()
        val file = inputData.psiFile
        if (file is JsonFile) {
            fillJson(valuesByType, file)
        }
        if (file is GoFile) {
            fillGo(valuesByType, file)
        }
        return valuesByType
    }

    private fun fillJson(map: ValuesByType, file: JsonFile) {
        val jsonObject = JsonUtil.getTopLevelObject(file) ?: return
        jsonObject.accept(object : JsonRecursiveElementVisitor() {
            override fun visitProperty(o: JsonProperty) {
                val value = o.value?.text ?: return
                val type = o.name
                map[type] = value
            }
        })
    }

    private fun fillGo(map: ValuesByType, file: GoFile) {
        val visitor = GoVisitor(map)
        file.accept(visitor)
    }
}