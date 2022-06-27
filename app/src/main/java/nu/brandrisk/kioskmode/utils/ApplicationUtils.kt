package nu.brandrisk.kioskmode.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationUtils @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun startApplication(packageName: String) {
        context.packageManager?.getLaunchIntentForPackage(packageName)?.let {
                try {
                    context.startActivity(it)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Failed to start app $packageName", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun getAllInstalledPackages(): List<String> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val packages = context.packageManager.getInstalledPackages(PackageManager.GET_META_DATA).map { it.packageName }
        val activityPackages = context.packageManager.queryIntentActivities(mainIntent, 0).map { it.activityInfo.packageName }
        val allPackages = packages + activityPackages

        return allPackages.toSet().toList()
    }
}