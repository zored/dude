package gl.ro.dude

import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import gl.ro.dude.jetbrains.action.DebugAction
import kotlin.reflect.KClass

class DudeGoTest : BasePlatformTestCase() {
    var fileIndex = 1

    override fun setUp() {
        super.setUp()
        newDefinitions()
    }

    fun `test completion`() {
        file(
            """
    type Ticket struct {
      ric<caret>
    }
    """
        )
        val lookupElements = myFixture.completeBasic()
        assertNotNull(lookupElements)
        assertSize(2, lookupElements)
        assertEquals(
            listOf("ricardo Person", "richard Person"),
            lookupElements.map { it.lookupString }.toList()
        )
    }

    fun `test action`() {
        Messages.setTestDialog { assertTrue(it.contains("Go names")); Messages.OK }
        runAction(DebugAction::class)
    }

    private fun <T : Any> runAction(actionClass: KClass<T>) = myFixture.performEditorAction(getActionId(actionClass))

    private fun <T : Any> getActionId(actionClass: KClass<T>): String =
        actionClass.java.toString().substring("class.".length)

    private fun newDefinitions(): PsiFile = validGo(
        file(
            """
// Types:
type Person struct{}

// Entities:
var ricardo Person;
func ShowPerson(richard Person) {}
"""
        )
    )

    private fun file(text: String): PsiFile = myFixture.configureByText(
        "${fileIndex++}.go",
        "package main\n\n$text".trimIndent()
    )

    private fun validGo(file: PsiFile): PsiFile {
        file.accept(object : PsiElementVisitor() {
            override fun visitErrorElement(element: PsiErrorElement) {
                fail("test file '${file.name}' has error: ${element.errorDescription}")
            }
        })
        return file
    }
}