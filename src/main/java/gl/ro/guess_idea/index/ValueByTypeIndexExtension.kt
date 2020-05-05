package gl.ro.guess_idea.index

import com.goide.GoFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.util.indexing.*
import com.intellij.util.indexing.FileBasedIndex.InputFilter
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor

class ValueByTypeIndexExtension : FileBasedIndexExtension<Type, Values>() {
    override fun getName(): ID<Type, Values> = NAME
    override fun getIndexer(): DataIndexer<Type, Values, FileContent> = ValueByTypeIndexer
    override fun getKeyDescriptor(): KeyDescriptor<Type> = DESCRIPTOR
    override fun getValueExternalizer(): DataExternalizer<Values> = ValuesExternalizer
    override fun getVersion(): Int = VERSION
    override fun getInputFilter(): InputFilter = InputFilter { f -> FILE_TYPES.contains(f.fileType) }
    override fun dependsOnFileContent(): Boolean = true

    companion object {
        val NAME = ID.create<Type, Values>("gl.ro.guess_idea.index.value_by_type")
        const val VERSION = 1
        private val DESCRIPTOR = EnumeratorStringDescriptor.INSTANCE
        private val FILE_TYPES = setOf<LanguageFileType>(
            JsonFileType.INSTANCE,
            GoFileType.INSTANCE
        )
    }
}