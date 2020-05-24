package gl.ro.dude.index

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

class ValueByTypeIndexExtension : FileBasedIndexExtension<Type, Values>() {
    companion object {
        val NAME = ID.create<Type, Values>("gl.ro.dude.index.value_by_type")
        private val FILE_TYPES = setOf<LanguageFileType>(
            JsonFileType.INSTANCE,
            GoFileType.INSTANCE
        )
    }

    override fun getName(): ID<Type, Values> = NAME
    override fun getIndexer(): DataIndexer<Type, Values, FileContent> = ValueByTypeIndexer
    override fun getKeyDescriptor(): KeyDescriptor<Type> = EnumeratorStringDescriptor.INSTANCE
    override fun getValueExternalizer(): DataExternalizer<Values> = ValuesExternalizer
    override fun getVersion(): Int = 1
    override fun getInputFilter(): InputFilter = InputFilter { f -> FILE_TYPES.contains(f.fileType) }
    override fun dependsOnFileContent(): Boolean = true
}