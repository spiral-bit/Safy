package spiral.bit.dev.safy.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.repositories.FolderRepository

class FolderViewModel @ViewModelInject constructor(private var folderRepository: FolderRepository) :
    ViewModel() {

    var foldersLiveData: LiveData<List<Folder>> = folderRepository.getAllFolders()

    fun insertFolder(folder: Folder) = viewModelScope.launch {
        folderRepository.insertFolder(folder)
    }

    fun updateFolder(folder: Folder) = viewModelScope.launch {
        folderRepository.updateFolder(folder)
    }

    fun deleteFolder(folder: Folder) = CoroutineScope(Dispatchers.IO).launch {
        folderRepository.deleteFolder(folder)
    }
}