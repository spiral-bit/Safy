package spiral.bit.dev.safy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FolderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Query("SELECT * FROM folders ORDER BY id DESC")
    fun getFolders():LiveData<List<Folder>>


}