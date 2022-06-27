package nu.brandrisk.kioskmode.ui.config

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nu.brandrisk.kioskmode.R
import nu.brandrisk.kioskmode.domain.HomeScreenSettings
import nu.brandrisk.kioskmode.ui.shared.AppIconItem
import nu.brandrisk.kioskmode.utils.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConfigView(
    navController: NavController,
    viewModel: ConfigViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val apps = viewModel.apps.collectAsState(initial = emptyList())
    val imageLoader = viewModel.imageLoader

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.enableKioskMode(context)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Column(Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            cells = GridCells.Adaptive(120.dp),
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState()
        ) {

            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (!HomeScreenSettings().isThisAppTheHomeScreen(context)) {
                        Button(onClick = {
                            HomeScreenSettings().showSelectHomeScreen(context)
                        }) {
                            Text(text = stringResource(R.string.set_as_launcher))
                        }
                    }

                    Button(onClick = {
                        if (viewModel.isDeviceOwner(context)) {
                            launcher.launch(viewModel.toggleKioskMode.getAddDeviceAdminIntent())
                        } else {
                            viewModel.showADBSetupScreen()
                        }
                    }) {
                        Text(text = stringResource(R.string.enable_kiosk_mode))
                    }

                    if (viewModel.isDeviceOwner(context)) {
                        Button(onClick = {
                            viewModel.disableKioskMode(context)
                        }) {
                            Text(text = stringResource(R.string.disable_kiosk_mode))
                        }
                    }

                    Button(onClick = {
                        viewModel.enableAllApps()
                    }) {
                        Text(text = stringResource(R.string.enable_all_apps))
                    }

                    Button(onClick = {
                        viewModel.disableAllApps()
                    }) {
                        Text(text = stringResource(R.string.disable_all_apps))
                    }


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = stringResource(R.string.enable_disable_apps))

                }
            }

            items(apps.value) { app ->
                AppIconItem(modifier = Modifier.clickable {
                    viewModel.updateEnabledFlag(app)
                }, app, imageLoader)
            }
        }
    }
}