package gl.ro.dude.jetbrains.index

import com.goide.GoFileType
import com.intellij.util.indexing.FileBasedIndex.InputFilter
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.ID
import com.intellij.util.io.EnumDataDescriptor
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Values

class ValuesIndexExtension : FileBasedIndexExtension<Type, Values>() {
    companion object {
        val NAME = ID.create<Type, Values>("gl.ro.dude.values_by_type")
    }

    override fun getIndexer() = ValueByTypeIndexer
    override fun getName() = NAME
    override fun getKeyDescriptor() = EnumDataDescriptor(Type::class.java)
    override fun getValueExternalizer() = ValuesExternalizer
    override fun getVersion() = 1
    override fun getInputFilter() = InputFilter { it.fileType == GoFileType.INSTANCE }
    override fun dependsOnFileContent() = true
}