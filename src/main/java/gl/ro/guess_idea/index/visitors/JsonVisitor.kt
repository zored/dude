package gl.ro.guess_idea.index.visitors

import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor
import gl.ro.guess_idea.index.ValuesByType

class JsonVisitor(private val valuesByType: ValuesByType) : JsonRecursiveElementVisitor() {
    override fun visitProperty(o: JsonProperty) {
        valuesByType[o.name] = (o.value?.text ?: return)
    }
}