package nu.brandrisk.kioskmode.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.Fetcher
import coil.request.Options
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Custom coil fetcher that fetches icons for the installed applications
 */
@Singleton
class ApplicationIconFetcher @Inject constructor(
    @ApplicationContext val context: Context
) : Fetcher.Factory<Uri> {
    override fun create(data: Uri, options: Options, imageLoader: ImageLoader): Fetcher {
        return Fetcher {
            val icon = context.packageManager.getApplicationIcon(data.toString()).toBitmap()
            DrawableResult(BitmapDrawable(context.resources, icon), false, DataSource.DISK)
        }
    }
}
