package spiral.bit.dev.safy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ImageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImg(image: Image)

    @Delete
    fun deleteImg(image: Image)

    @Query("DELETE FROM images_table WHERE folder_id == :folderID")
    suspend fun deleteAllImagesInFolder(folderID: Int)

    @Query("SELECT * FROM images_table WHERE folder_id == :folderId")
    fun getAllImagesByFolderId(folderId: Int): LiveData<List<Image>>

    @Query("SELECT * FROM images_table WHERE folder_id == :folderID ORDER BY timeStamp DESC LIMIT 1")
    fun getLastPhoto(folderID: Int): LiveData<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfImages(list: List<Image>)

}