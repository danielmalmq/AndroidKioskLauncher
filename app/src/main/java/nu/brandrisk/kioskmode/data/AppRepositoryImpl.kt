package nu.brandrisk.kioskmode.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nu.brandrisk.kioskmode.data.db.AppDao
import nu.brandrisk.kioskmode.data.db.model.AppEntity
import nu.brandrisk.kioskmode.data.model.App
import nu.brandrisk.kioskmode.domain.AppRepository

class AppRepositoryImpl(
    private val dao: AppDao,
    private val context: Context
) : AppRepository {
    override suspend fun insertApp(app: App) {
        dao.insertApp(app.toAppEntity())
    }

    override suspend fun upsertApp(app: App) {
        dao.upsertApp(app.toAppEntity())
    }

    override fun getApps(): Flow<List<App>> {
        return dao.getApps().map { list ->
            list.map {
                it.toApp()
            }
        }
    }

    override suspend fun refreshApps() {
        val pm: PackageManager = context.applicationContext.packageManager

        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activityPackages = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_ALL)
            .map { it.activityInfo.packageName }
        val allInstalledLauncherPackages = activityPackages.toSet()

        for (packageName in allInstalledLauncherPackages) {
            val title = try {
                pm.getApplicationInfo(packageName, 0).let { pm.getApplicationLabel(it) }
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }

            title?.let {
                insertApp(App(packageName, it.toString(), true))
            }
        }
    }

    override suspend fun disableAllApps() {
        dao.disableAllAppsExcept(context.packageName)
    }

    override suspend fun enableAllApps() {
        dao.enableAllApps()
    }

}

private fun App.toAppEntity(): AppEntity {
    return AppEntity(packageName, title, isEnabled)
}

private fun AppEntity.toApp(): App {
    return App(packageName, title, isEnabled)
}