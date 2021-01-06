package spiral.bit.dev.safy.listeners

import com.skydoves.transformationlayout.TransformationLayout
import spiral.bit.dev.safy.data.Image

interface ImageListener {
    fun onImgClicked(image: Image, position: Int, transformationLayout: TransformationLayout)
    fun onImgLongClicked(image: Image, position: Int)
}