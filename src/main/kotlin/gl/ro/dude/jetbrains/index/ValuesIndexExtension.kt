package gl.ro.dude.jetbrains.index

import com.goide.GoFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileBasedIndex.InputFilter
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.FileContent
import com.intellij.util.indexing.ID
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumDataDescriptor
import com.intellij.util.io.EnumeratorStringDescriptor
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Value
import gl.ro.dude.domain.retriever.Values
import gl.ro.dude.jetbrains.index.common.MutableValuesByType
import gl.ro.dude.jetbrains.index.visitors.GoVisitor
import java.io.DataInput
import java.io.DataOutput

class ValuesIndexExtension : FileBasedIndexExtension<Type, Values>() {
    companion object {
        val NAME = ID.create<Type, Values>("gl.ro.dude.values_by_type")
        private val empty by lazy { mapOf<Type, Values>() }
        private val roots = hashMapOf<Project, String>()
    }
    override fun getInputFilter() = InputFilter { it.fileType == GoFileType.INSTANCE }
    override fun getKeyDescriptor() = EnumDataDescriptor(Type::class.java)
    override fun getIndexer() = DataIndexer<Type, Values, FileContent> {
        if (!isProjectFile(it)) {
            return@DataIndexer empty
        }
        val result = MutableValuesByType()
        val visitor = GoVisitor { type, value, source -> if (!(source == Type.VARIABLE && value == "_")) result[source] = Value(value, type, source) }
        if (visitor.suitsFile(it.fileType)) {
            it.psiFile.accept(visitor)
        }
        result.toMap()
    }
    override fun getName() = NAME
    override fun getValueExternalizer() = object : DataExternalizer<Values> {
        private val ENUM = EnumeratorStringDescriptor.INSTANCE
        override fun save(out: DataOutput, values: Values?) = ENUM.save(out, Values.stringify(values))
        override fun read(d: DataInput): Values = Values.fromString(ENUM.read(d))
    }
    override fun dependsOnFileContent() = true
    override fun getVersion() = 1

    private fun isProjectFile(content: FileContent): Boolean {
        val project = content.psiFile.project
        val root =
            roots[project]
                ?: project.guessProjectDir()?.path
                ?: return false
        roots[project] = root
        return content.file.path.startsWith(root)
    }
}
