package spiral.bit.dev.safy.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.repositories.ImageRepository

class ImageViewModel @ViewModelInject constructor(private var imageRepository: ImageRepository) :
    ViewModel() {

    lateinit var imagesLiveData: LiveData<List<Image>>
    lateinit var imageBackLiveData: LiveData<Image>

    fun insertImg(image: Image) = viewModelScope.launch {
        imageRepository.insertImage(image)
    }

    fun insertListOfImages(list: List<Image>) = viewModelScope.launch {
        imageRepository.insertListOfImages(list)
    }

    fun deleteImg(image: Image) = CoroutineScope(Dispatchers.IO).launch {
        imageRepository.deleteImage(image)
    }

    fun deleteAllImagesByFolderId(folderID: Int) = CoroutineScope(Dispatchers.IO).launch {
        imageRepository.deleteAllImagesInFolder(folderID)
    }

    fun setParentId(parentId: Int) {
        imagesLiveData = imageRepository.getAllImagesByFolderId(parentId)
        imageBackLiveData = imageRepository.getLastPhoto(parentId)
    }
}