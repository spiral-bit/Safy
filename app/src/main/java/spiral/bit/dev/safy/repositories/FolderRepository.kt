package spiral.bit.dev.safy.repositories

import androidx.lifecycle.LiveData
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.data.FolderDAO
import spiral.bit.dev.safy.data.Image
import javax.inject.Inject

class FolderRepository @Inject constructor(var folderDAO: FolderDAO) {

    suspend fun insertFolder(folder: Folder) = folderDAO.insertFolder(folder)

    suspend fun updateFolder(folder: Folder) = folderDAO.updateFolder(folder)

    suspend fun deleteFolder(folder: Folder) = folderDAO.deleteFolder(folder)

    fun getAllFolders(): LiveData<List<Folder>> = folderDAO.getFolders()
}