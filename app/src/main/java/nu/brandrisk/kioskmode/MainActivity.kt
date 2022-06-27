package nu.brandrisk.kioskmode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nu.brandrisk.kioskmode.domain.AppRepository
import nu.brandrisk.kioskmode.ui.adbsetup.AdbSetupView
import nu.brandrisk.kioskmode.ui.config.ConfigView
import nu.brandrisk.kioskmode.ui.configparentalcheck.ConfigParentalCheckView
import nu.brandrisk.kioskmode.ui.launcher.LauncherView
import nu.brandrisk.kioskmode.ui.theme.KioskModeTheme
import nu.brandrisk.kioskmode.utils.Routes
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            appRepository.refreshApps()
        }

        setContent {
            KioskModeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.LAUNCHER
                    ) {
                        composable(Routes.LAUNCHER) {
                            LauncherView(navController = navController)
                        }
                        composable(Routes.CONFIG_PARENTAL_CHECK) {
                            ConfigParentalCheckView(navController = navController)
                        }
                        composable(Routes.CONFIG) {
                            ConfigView(navController = navController)
                        }
                        composable(Routes.ADB_SETUP) {
                            AdbSetupView()
                        }
                    }
                }
            }
        }
    }
}