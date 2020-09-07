package gl.ro.dude.jetbrains.index.extension

import com.goide.GoFileType
import com.intellij.util.indexing.FileBasedIndex
import gl.ro.dude.jetbrains.index.indexer.ValueByTypeIndexer

class GoValuesIndexExtension : AbstractValuesIndexExtension() {
    override fun getIndexName() = "go"
    override fun getInputFilter() =
        FileBasedIndex.InputFilter {
            it.fileType == GoFileType.INSTANCE
        }
    override fun getIndexer() = ValueByTypeIndexer
}