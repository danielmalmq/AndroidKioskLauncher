package nu.brandrisk.kioskmode.ui.launcher

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nu.brandrisk.kioskmode.R
import nu.brandrisk.kioskmode.ui.shared.AppIconItem
import nu.brandrisk.kioskmode.utils.Routes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LauncherView(
    navController: NavController,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    val apps = viewModel.apps.collectAsState(initial = emptyList())
    val imageLoader = viewModel.imageLoader
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(120.dp),
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState()
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        navController.navigate(Routes.CONFIG_PARENTAL_CHECK)
                    }, modifier = Modifier.padding(8.dp)) {
                        Text(text = stringResource(R.string.config))
                    }
                }
            }

            items(apps.value.filter { it.isEnabled && it.packageName != context.packageName }) { app ->
                AppIconItem(modifier = Modifier.clickable {
                    viewModel.startApplication(app)
                }, app, imageLoader)
            }
        }
    }
}
