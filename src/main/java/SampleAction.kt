package main.java

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class SampleAction() : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val presentation = e.presentation
        Messages.showMessageDialog(
            project,
            presentation.description,
            presentation.text,
            Messages.getInformationIcon()
        )
    }
}