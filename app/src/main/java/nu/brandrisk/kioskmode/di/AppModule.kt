package nu.brandrisk.kioskmode.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nu.brandrisk.kioskmode.data.AppRepositoryImpl
import nu.brandrisk.kioskmode.data.db.AppDatabase
import nu.brandrisk.kioskmode.domain.AppRepository
import nu.brandrisk.kioskmode.utils.ApplicationIconFetcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "appEntity"
        ).build()
    }

    @Provides
    @Singleton
    fun providesAppRepository(db: AppDatabase, @ApplicationContext context: Context): AppRepository {
        return AppRepositoryImpl(db.dao, context)
    }

    @Provides
    @Singleton
    fun providesApplicationImageLoader(@ApplicationContext context: Context, applicationIconFetcher: ApplicationIconFetcher): ImageLoader {
        return  ImageLoader.Builder(context)
            .components {
                add(applicationIconFetcher)
            }
            .memoryCache(MemoryCache.Builder(context).maxSizePercent(0.2).build())
            .logger(DebugLogger())
            .build()
    }
}