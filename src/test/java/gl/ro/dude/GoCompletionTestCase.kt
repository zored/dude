package gl.ro.dude

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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
    @Parameters(
        """import ric<caret>""",
        """import (
            "fmt"
            ric<caret>
       )"""
    )
    fun completeImports(text: String) {
        file(text)
        assertImports()
    }

    @Test
    @Parameters(
        """func F(ric<caret>) {}""",
        """func F() (ric<caret>) {}""",
        """
           struct S{}
           func (s *S) F(ric<caret>) {}
        """,
        """
           struct S{}
           func (s *S) F() (ric<caret>) {}
        """,
        """
type Ticket struct {
  ric<caret>
}
    """,
        """var ric<caret>"""
    )
    fun completeVariables(text: String) {
        file(text)
        assertVariables()
    }

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

    private fun assertCompletions(vararg expected: String) {
        val actual = myFixture.completeBasic() ?: LookupElement.EMPTY_ARRAY

        assertEquals(
            expected.toList(),
            actual.map { it.lookupString }.toList()
        )
    }

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

    private fun assertVariables() =
        assertCompletions(
            "ricardo Person",
            "richard Person"
        )

    private fun assertImports() =
        assertCompletions(
            "rick \"github.com/zored/rick.git/v5\"",
            "ricky \"github.com/zored/ricky.git/v5\""
        )
}