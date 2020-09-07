package gl.ro.dude

import junitparams.Parameters
import org.intellij.lang.annotations.Language
import org.junit.Test

class GoCompletionTestCase : AbstractCompletionTestCase() {
    override fun getFileExtension(): String = "go"

    override fun getVariables(): Array<String> = arrayOf(
        "ricardo Person",
        "richard Person"
    )

    override fun getImports(): Array<String> = arrayOf(
        "rick \"github.com/zored/rick.git/v5\"",
        "ricky \"github.com/zored/ricky.git/v5\""
    )

    @Language("GoLang")
    override fun getInitialFile(): String =
        """
import rick "github.com/zored/rick.git/v5"
import ricky "github.com/zored/ricky.git/v5"

// Types:
type Person struct{}

// Entities:
var ricardo Person;
type x struct {
   ricardo Person // - we have more of these names.
}
func ShowPerson(richard Person) {}
"""

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

    @Test
    fun completeVariablesOrder() {
        validFile(
            """
type s1 struct {richard Person}
type s2 struct {richard Person}
type s3 struct {richard Person}
        """
        )
        file("""func F(ric<caret>) {}""")
        assertVariables(true)
    }
}