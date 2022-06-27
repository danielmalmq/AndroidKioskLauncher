package nu.brandrisk.kioskmode.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nu.brandrisk.kioskmode.data.db.model.AppEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApp(app: AppEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertApp(app: AppEntity)

    @Query("SELECT * FROM AppEntity ORDER BY title")
    fun getApps(): Flow<List<AppEntity>>

    @Query("UPDATE AppEntity SET isEnabled = 0 WHERE packageName <> :packageName")
    fun disableAllAppsExcept(packageName: String)

    @Query("UPDATE AppEntity SET isEnabled = 1")
    fun enableAllApps()
}