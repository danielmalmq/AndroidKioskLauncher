package nu.brandrisk.kioskmode.domain

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import nu.brandrisk.kioskmode.MainActivity
import nu.brandrisk.kioskmode.KioskDeviceAdminReceiver
import nu.brandrisk.kioskmode.utils.ApplicationUtils
import nu.brandrisk.kioskmode.utils.ContextUtils.getActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToggleKioskMode @Inject constructor(
    @ApplicationContext val context: Context,
    val applicationUtils: ApplicationUtils
) {
    private val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponentName = KioskDeviceAdminReceiver.getComponentName(context)

    sealed class Result {
        object Success : Result()
        object NotDeviceOwner : Result()
    }

    fun enableKioskMode(context: Context): Result {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
            devicePolicyManager.setLockTaskPackages(
                adminComponentName,
                applicationUtils.getAllInstalledPackages().toTypedArray()
            )

            devicePolicyManager.setBackupServiceEnabled(adminComponentName, true)

            val intentFilter = IntentFilter(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                addCategory(Intent.CATEGORY_DEFAULT)
            }
            devicePolicyManager.addPersistentPreferredActivity(
                adminComponentName,
                intentFilter,
                ComponentName(context.packageName, MainActivity::class.java.name)
            )

            try {
                context.getActivity()?.startLockTask() ?: throw Exception("Activity is null")
                return Result.Success
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return Result.NotDeviceOwner
    }

    fun getAddDeviceAdminIntent(): Intent {
        val deviceAdminReceiver = ComponentName(context, KioskDeviceAdminReceiver::class.java)
        return Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminReceiver)
        }
    }

    fun disableKioskMode(context: Context): Result {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
            devicePolicyManager.setLockTaskPackages(adminComponentName, emptyArray())
            context.getActivity()?.let {
                it.stopLockTask()
                return Result.Success
            }
        }

        return Result.NotDeviceOwner
    }
}