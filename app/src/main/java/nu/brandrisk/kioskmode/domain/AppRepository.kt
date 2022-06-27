package nu.brandrisk.kioskmode.domain

import kotlinx.coroutines.flow.Flow
import nu.brandrisk.kioskmode.data.model.App

interface AppRepository {

    suspend fun insertApp(app: App)

    suspend fun upsertApp(app: App)

    fun getApps(): Flow<List<App>>

    suspend fun refreshApps()

    suspend fun disableAllApps()
    suspend fun enableAllApps()
}