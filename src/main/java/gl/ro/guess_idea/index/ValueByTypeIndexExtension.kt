package gl.ro.guess_idea.index

import com.intellij.json.JsonFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor

// IDE extension for indexer.
class ValueByTypeIndexExtension : FileBasedIndexExtension<String, String>() {
    override fun getName(): ID<String, String> {
        return NAME
    }

    override fun getIndexer(): DataIndexer<String, String, FileContent> {
        return INDEXER
    }

    override fun getKeyDescriptor(): KeyDescriptor<String> {
        return DESCRIPTOR
    }

    override fun getValueExternalizer(): DataExternalizer<String> {
        return DESCRIPTOR
    }

    override fun getVersion(): Int {
        return VERSION
    }

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter { file: VirtualFile -> file.fileType === JsonFileType.INSTANCE }
    }

    override fun dependsOnFileContent(): Boolean {
        return true
    }

    companion object {
        val NAME = ID.create<String, String>("gl.ro.guess_idea.index.value_by_type")
        const val VERSION = 1
        private val INDEXER = ValueByTypeIndexer.INSTANCE
        private val DESCRIPTOR = EnumeratorStringDescriptor.INSTANCE
    }
}