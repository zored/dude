package gl.ro.dude

import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import gl.ro.dude.jetbrains.action.DebugAction
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KClass

@RunWith(JUnitParamsRunner::class)
class GoCompletionTestCase : BasePlatformTestCase() {
    var fileIndex = 1

    @Before
    public override fun setUp() {
        super.setUp()
        newDefinitions()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `field`() {
        file(
            """
type Ticket struct {
  ric<caret>
}
    """
        )
        assertCompletions(
            "ricardo Person",
            "richard Person"
        )
    }

    @Test
    @Parameters(
        """import ric<caret>""",
        """import (
            "fmt"
            ric<caret>
       )"""
    )
    fun completion(text: String) {
        file(text)
        assertCompletions(
            "rick \"github.com/zored/rick.git/v5\"",
            "ricky \"github.com/zored/ricky.git/v5\""
        )
    }

    @Test
    @Parameters(
        """var ric<caret>"""
    )
    fun `var`(text: String) {
        file(text)
        assertCompletions(
            "ricardo Person",
            "richard Person"
        )
    }

    @Test
    fun action() {
        @Suppress("UnstableApiUsage") // TODO
        Messages.setTestDialog {
            assertTrue(it.contains("Go names"))
            Messages.OK
        }
        runAction(DebugAction::class)
    }

    private fun <T : Any> runAction(actionClass: KClass<T>) = myFixture.performEditorAction(getActionId(actionClass))

    private fun <T : Any> getActionId(actionClass: KClass<T>): String =
        actionClass.java.toString().substring("class.".length)

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

    private fun assertCompletions(vararg expected: String) =
        assertEquals(
            expected.toList(),
            myFixture.completeBasic().map { it.lookupString }.toList()
        )

    private fun newDefinitions(): PsiFile =
        validGo(
            file(
                """
import rick "github.com/zored/rick.git/v5"
import ricky "github.com/zored/ricky.git/v5"

// Types:
type Person struct{}

// Entities:
var ricardo Person;
func ShowPerson(richard Person) {}
"""
            )
        )
}