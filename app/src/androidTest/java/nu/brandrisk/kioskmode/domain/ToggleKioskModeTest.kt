package nu.brandrisk.kioskmode.domain


import android.app.admin.DevicePolicyManager
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nu.brandrisk.kioskmode.utils.ApplicationUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToggleKioskModeTest {
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val toggleKioskMode = ToggleKioskMode(appContext, ApplicationUtils(appContext))
    private val devicePolicyManager = appContext.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    @Test
    fun enableKioskMode() {
        if (!devicePolicyManager.isDeviceOwnerApp(appContext.packageName)) {
            val result = toggleKioskMode.enableKioskMode(appContext)
            Assert.assertSame(ToggleKioskMode.Result.NotDeviceOwner, result)
        }
    }

    @Test
    fun getAddDeviceAdminIntent() {
        val result = toggleKioskMode.getAddDeviceAdminIntent()
        val foundActivityForIntent = result.resolveActivity(appContext.packageManager).packageName.isNotEmpty()
        Assert.assertTrue(foundActivityForIntent)
    }

    @Test
    fun disableKioskMode() {
        if (!devicePolicyManager.isDeviceOwnerApp(appContext.packageName)) {
            val result = toggleKioskMode.disableKioskMode(appContext)
            Assert.assertSame(ToggleKioskMode.Result.NotDeviceOwner, result)
        }
    }
}