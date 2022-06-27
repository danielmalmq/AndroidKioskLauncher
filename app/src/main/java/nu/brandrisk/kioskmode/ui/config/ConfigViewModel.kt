package nu.brandrisk.kioskmode.ui.config

import android.app.admin.DevicePolicyManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nu.brandrisk.kioskmode.data.model.App
import nu.brandrisk.kioskmode.domain.AppRepository
import nu.brandrisk.kioskmode.domain.ToggleKioskMode
import nu.brandrisk.kioskmode.utils.Routes
import nu.brandrisk.kioskmode.utils.UiEvent
import javax.inject.Inject


@HiltViewModel
open class ConfigViewModel @Inject constructor(
    internal val toggleKioskMode: ToggleKioskMode,
    private val repository: AppRepository,
    @ApplicationContext private val context: Context,
    val imageLoader: ImageLoader
) : ViewModel() {

    val apps = repository.getApps()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun enableKioskMode(context: Context) {
        val result = toggleKioskMode.enableKioskMode(context)
        if (result == ToggleKioskMode.Result.NotDeviceOwner) {
            showADBSetupScreen()
        }
    }

    fun disableKioskMode(context: Context) {
        val result = toggleKioskMode.disableKioskMode(context)
        if (result == ToggleKioskMode.Result.NotDeviceOwner) {
            showADBSetupScreen()
        }
    }

    fun showADBSetupScreen() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(Routes.ADB_SETUP))
        }
    }

    fun isDeviceOwner(context: Context): Boolean {
        val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return devicePolicyManager.isDeviceOwnerApp(context.packageName)
    }

    fun updateEnabledFlag(app: App) {
        if (app.packageName != context.packageName) {
            viewModelScope.launch {
                repository.upsertApp(app.copy(isEnabled = !app.isEnabled))
            }
        }
    }

    fun disableAllApps() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.disableAllApps()
        }
    }

    fun enableAllApps() {
        viewModelScope.launch((Dispatchers.IO)) {
            repository.enableAllApps()
        }
    }
}