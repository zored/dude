package main.java

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.guess_idea.index.ValueByTypeIndexExtension

class SampleAction() : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val d = e.getData(LangDataKeys.PSI_FILE) ?: return

        var message = ""
        val index = FileBasedIndex.getInstance()
        var keys = HashSet<String>()
        val id = ValueByTypeIndexExtension.NAME

        index.processAllKeys(id, { key -> keys.add(key); true }, e.project)
        keys.forEach { key ->
            index.processValues(
                id,
                key,
                null,
                { file: VirtualFile, value: String? ->
                    message += "$key $value "
                    true
                },
                GlobalSearchScope.allScope(e.project!!)
            )
        }

        Messages.showMessageDialog(e.project, message, d.name, Messages.getInformationIcon())
    }
}

