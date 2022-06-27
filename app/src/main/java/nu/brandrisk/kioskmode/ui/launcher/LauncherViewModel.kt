package nu.brandrisk.kioskmode.ui.launcher

import android.content.Context
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import nu.brandrisk.kioskmode.data.model.App
import nu.brandrisk.kioskmode.domain.AppRepository
import nu.brandrisk.kioskmode.utils.ApplicationUtils
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    repository: AppRepository,
    @ApplicationContext val context: Context,
    val imageLoader: ImageLoader,
    val applicationUtils: ApplicationUtils
): ViewModel() {

    fun startApplication(app: App) {
        applicationUtils.startApplication(app.packageName)
    }

    val apps = repository.getApps()
}