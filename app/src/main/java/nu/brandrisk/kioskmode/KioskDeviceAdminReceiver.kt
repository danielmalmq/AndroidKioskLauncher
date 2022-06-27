package nu.brandrisk.kioskmode

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context

/**
 * Receiver required inorder to set up proper kiosk mode
 */
class KioskDeviceAdminReceiver : DeviceAdminReceiver() {
    companion object {
        fun getComponentName(context: Context): ComponentName {
            return ComponentName(context.applicationContext, KioskDeviceAdminReceiver::class.java)
        }
    }
}