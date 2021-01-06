package spiral.bit.dev.safy.listeners

import com.skydoves.transformationlayout.TransformationLayout
import spiral.bit.dev.safy.data.Folder

interface FolderListener {
    fun onFolderClicked(folder: Folder, position: Int, transformationLayout: TransformationLayout)
    fun onFolderLongClicked(folder: Folder, position: Int)
}