package gl.ro.dude.jetbrains.index

import com.goide.GoFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileBasedIndex.InputFilter
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.FileContent
import com.intellij.util.indexing.ID
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor
import gl.ro.dude.domain.retriever.TypeName
import gl.ro.dude.domain.retriever.ValueNames

class ValueByTypeIndexExtension : FileBasedIndexExtension<TypeName, ValueNames>() {
    companion object {
        val NAME = ID.create<TypeName, ValueNames>("gl.ro.dude.jetbrains.index.value_by_type")
        private val FILE_TYPES = setOf<LanguageFileType>(
            JsonFileType.INSTANCE,
            GoFileType.INSTANCE
        )
    }

    override fun getName(): ID<TypeName, ValueNames> = NAME
    override fun getIndexer(): DataIndexer<TypeName, ValueNames, FileContent> = ValueByTypeIndexer
    override fun getKeyDescriptor(): KeyDescriptor<TypeName> = EnumeratorStringDescriptor.INSTANCE
    override fun getValueExternalizer(): DataExternalizer<ValueNames> = ValuesExternalizer
    override fun getVersion(): Int = 1
    override fun getInputFilter(): InputFilter = InputFilter { f -> FILE_TYPES.contains(f.fileType) }
    override fun dependsOnFileContent(): Boolean = true
}