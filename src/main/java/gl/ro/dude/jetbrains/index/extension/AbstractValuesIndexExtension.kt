package gl.ro.dude.jetbrains.index.extension

import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.ID
import com.intellij.util.io.EnumDataDescriptor
import gl.ro.dude.domain.retriever.Type
import gl.ro.dude.domain.retriever.Values
import gl.ro.dude.jetbrains.index.ValuesExternalizer

abstract class AbstractValuesIndexExtension : FileBasedIndexExtension<Type, Values>() {
    protected abstract fun getIndexName(): String
    override fun getName() = ID.create<Type, Values>("gl.ro.dude.${getIndexName()}.values_by_type")
    override fun getKeyDescriptor() = EnumDataDescriptor(Type::class.java)
    override fun getValueExternalizer() = ValuesExternalizer
    override fun getVersion() = 1
    override fun dependsOnFileContent() = true
}