package gl.ro.dude.jetbrains.action

import com.intellij.ide.util.gotoByName.GotoSymbolModel2
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.util.indexing.FindSymbolParameters

class DebugAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(getMessage(e), "debug action", null)
    }

    private fun getMessage(e: AnActionEvent): String {
        var totalNames = 0
        val project = e.project ?: return ""
        val params = FindSymbolParameters.simple(project, false)
            .withCompletePattern("Repo")
        GotoSymbolModel2(project).processNames({
            totalNames++
            true
        }, params)
        return "Total Go names: $totalNames"
    }
}