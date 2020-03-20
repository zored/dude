package gl.ro.guess_idea.completion

import com.goide.GoTypes
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import gl.ro.guess_idea.index.Type
import gl.ro.guess_idea.index.Value
import gl.ro.guess_idea.index.ValuesByTypeIterator

object ValueCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val position = parameters.position
        val type = getParameterType(position) ?: return

        result.addAllElements(createElements(position, type))
    }

    private fun getParameterType(position: PsiElement): Type? {
        val definition = position.parent
        if (definition.elementType != GoTypes.PARAM_DEFINITION) {
            return null
        }

        val declaration = definition.parent
        if (declaration.elementType != GoTypes.PARAMETER_DECLARATION) {
            return null
        }

        return declaration.children.firstOrNull { it.elementType == GoTypes.TYPE }?.text
    }

    private fun createElements(
        position: PsiElement,
        expectedType: Type
    ): List<LookupElementBuilder> =
        ValuesByTypeIterator(position.project)
            .filter { (type) -> type == expectedType }
            .flatMap { (type, values) ->
                values.set.map { value -> createElement(type, value) }
            }

    private fun createElement(type: Type, value: Value) =
        LookupElementBuilder.create(value).appendTailText("project name for $type", true)
}