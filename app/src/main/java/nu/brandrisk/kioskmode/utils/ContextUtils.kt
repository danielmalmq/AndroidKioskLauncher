package nu.brandrisk.kioskmode.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object ContextUtils {
    fun Context.getActivity(): Activity? {
        var currentContext = this

        while (currentContext is ContextWrapper) {
            if (currentContext is Activity) {
                return currentContext
            }
            currentContext = currentContext.baseContext
        }
        return null
    }
}