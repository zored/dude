package gl.ro.guess_idea.index

import com.goide.psi.GoFile
import com.intellij.json.JsonUtil
import com.intellij.json.psi.JsonFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import gl.ro.guess_idea.index.visitors.GoVisitor
import gl.ro.guess_idea.index.visitors.JsonVisitor

// Indexes values by types.
object ValueByTypeIndexer : DataIndexer<Type, Values, FileContent> {
    override fun map(inputData: FileContent): Map<Type, Values> {
        val valuesByType = ValuesByType()
        val file = inputData.psiFile
        if (file is JsonFile) {
            fillJson(valuesByType, file)
        } else if (file is GoFile) {
            fillGo(valuesByType, file)
        }
        return valuesByType.toMap()
    }

    private fun fillJson(valuesByType: ValuesByType, file: JsonFile) {
        val json = JsonUtil.getTopLevelObject(file) ?: return
        json.accept(JsonVisitor(valuesByType))
    }

    private fun fillGo(map: ValuesByType, file: GoFile) {
        file.accept(GoVisitor(map))
    }
}