package gl.ro.guess_idea.index

import com.goide.GoFileType
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor

// IDE extension for indexer.
class ValueByTypeIndexExtension : FileBasedIndexExtension<Type, Values>() {
    override fun getName(): ID<Type, Values> {
        return NAME
    }

    override fun getIndexer(): DataIndexer<Type, Values, FileContent> {
        return ValueByTypeIndexer
    }

    override fun getKeyDescriptor(): KeyDescriptor<Type> {
        return DESCRIPTOR
    }

    override fun getValueExternalizer(): DataExternalizer<Values> {
        return ValuesExternalizer
    }

    override fun getVersion(): Int {
        return VERSION
    }

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter { file: VirtualFile -> FILE_TYPES.contains(file.fileType) }
    }

    override fun dependsOnFileContent(): Boolean {
        return true
    }

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