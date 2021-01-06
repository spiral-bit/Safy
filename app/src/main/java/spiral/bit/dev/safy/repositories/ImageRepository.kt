package spiral.bit.dev.safy.repositories

import androidx.lifecycle.LiveData
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.data.ImageDAO
import javax.inject.Inject

class ImageRepository @Inject constructor(var imageDAO: ImageDAO) {

    suspend fun insertListOfImages(list: List<Image>) = imageDAO.insertListOfImages(list)

    suspend fun insertImage(image: Image) = imageDAO.insertImg(image)

    fun deleteImage(image: Image) = imageDAO.deleteImg(image)

    suspend fun deleteAllImagesInFolder(folderID: Int) = imageDAO.deleteAllImagesInFolder(folderID)

    fun getLastPhoto(folderID: Int): LiveData<Image> = imageDAO.getLastPhoto(folderID)

    fun getAllImagesByFolderId(parentId: Int): LiveData<List<Image>> = imageDAO.getAllImagesByFolderId(parentId)
}