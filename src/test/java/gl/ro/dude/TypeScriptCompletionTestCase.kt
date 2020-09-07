package gl.ro.dude

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.intellij.lang.annotations.Language
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class TypeScriptCompletionTestCase : AbstractCompletionTestCase() {
    override fun getFileExtension(): String = "ts"

    @Language("TypeScript")
    override fun getInitialFile(): String = """
// TODO: imports

// Types:
class Person{}

// Entities:
const ricardo = new Person();
class X {
  ricard: Person
}
 
function ShowPerson(richard: Person) {}
    """

    override fun getVariables(): Array<String> = arrayOf(
        "ricardo: Person",
        "richard: Person"
    )

    override fun getImports(): Array<String> = arrayOf(
        "import * as rick from \"github.com/zored/rick.git/v5\"",
        "import * as ricky from \"github.com/zored/ricky.git/v5\""
    )

    @Test
    @Parameters(
        """const ric<caret>"""
    )
    fun completeVariables(text: String) {
        file(text)
        assertVariables()
    }
}