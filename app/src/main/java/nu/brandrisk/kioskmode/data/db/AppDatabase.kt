package nu.brandrisk.kioskmode.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import nu.brandrisk.kioskmode.data.db.model.AppEntity

@Database(entities = [AppEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: AppDao

}