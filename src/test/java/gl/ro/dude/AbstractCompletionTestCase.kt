package gl.ro.dude

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junitparams.JUnitParamsRunner
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
abstract class AbstractCompletionTestCase : BasePlatformTestCase() {
    protected abstract fun getFileExtension(): String

    protected abstract fun getInitialFile(): String

    private var fileIndex = 1

    @Before
    public override fun setUp() {
        super.setUp()
        newDefinitions()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
    }

    protected fun file(text: String): PsiFile = myFixture.configureByText(
        "${fileIndex++}.${getFileExtension()}",
        "package main\n\n$text".trimIndent()
    )

    private fun validatePsi(file: PsiFile): PsiFile {
        file.accept(object : PsiElementVisitor() {
            override fun visitErrorElement(element: PsiErrorElement) {
                fail("test file '${file.name}' has error: ${element.errorDescription}")
            }
        })
        return file
    }

    protected fun assertCompletions(vararg expected: String) {
        val actual = myFixture.completeBasic() ?: LookupElement.EMPTY_ARRAY

        assertEquals(
            expected.toList(),
            actual.map { it.lookupString }.toList()
        )
    }

    private fun newDefinitions(): PsiFile =
        validFile(getInitialFile())

    protected fun validFile(text: String) = validatePsi(file(text))

    protected fun assertVariables(reverse: Boolean = false) {
        val items = getVariables()
        val expected =
            if (reverse) items.reversedArray()
            else items
        assertCompletions(*expected)
    }

    protected abstract fun getVariables(): Array<String>
    protected abstract fun getImports(): Array<String>

    protected fun assertImports() =
        assertCompletions(*getImports())
}