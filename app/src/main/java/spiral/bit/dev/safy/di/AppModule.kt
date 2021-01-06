package spiral.bit.dev.safy.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import spiral.bit.dev.safy.data.FolderDb
import spiral.bit.dev.safy.utils.PREF_MODE
import spiral.bit.dev.safy.utils.SHARED_PREFERENCES_NAME
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFolderDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FolderDb::class.java,
        "folders_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideFolderDao(db: FolderDb) = db.getFolderDAO()

    @Singleton
    @Provides
    fun provideImageDao(db: FolderDb) = db.getImageDAO()

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences(SHARED_PREFERENCES_NAME, PREF_MODE)
}