package spiral.bit.dev.safy.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Folder::class, Image::class], version = 11)
abstract class FolderDb : RoomDatabase() {
    abstract fun getFolderDAO(): FolderDAO
    abstract fun getImageDAO(): ImageDAO
}