package nu.brandrisk.kioskmode.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import nu.brandrisk.kioskmode.R
import nu.brandrisk.kioskmode.data.model.App

@Composable
fun AppIconItem(
    modifier: Modifier = Modifier,
    app: App,
    imageLoader: ImageLoader
) {
    Box(modifier = modifier
        .aspectRatio(1f)
        .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(
                        model = app.packageName,
                        imageLoader = imageLoader
                    ),
                    contentDescription = stringResource(R.string.icon_of_string),
                    modifier = Modifier.fillMaxSize()
                )

                if (!app.isEnabled) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(R.string.not_enabled),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = app.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}