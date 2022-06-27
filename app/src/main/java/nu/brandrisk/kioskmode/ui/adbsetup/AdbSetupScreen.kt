package nu.brandrisk.kioskmode.ui.adbsetup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nu.brandrisk.kioskmode.R

@Composable
fun AdbSetupView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.to_enable_kiosk_mode), fontSize = 22.sp)
        Text(fontSize = 18.sp, text = buildAnnotatedString {
            append(stringResource(R.string.you_first_need_to))
            withStyle(
                style = SpanStyle(
                    fontFamily = FontFamily.Monospace
                )
            ) {
                append("adb shell dpm set-device-owner nu.brandrisk.kioskmode/.MyDeviceAdminReceiver")
            }
        })

        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(R.string.to_disable_kiosk_mode), fontSize = 22.sp)
        Text(fontSize = 18.sp, text = buildAnnotatedString {
            append(stringResource(R.string.you_first_need_to))
            withStyle(
                style = SpanStyle(
                    fontFamily = FontFamily.Monospace
                )
            ) {
                append("adb shell dpm remove-active-admin nu.brandrisk.kioskmode/.MyDeviceAdminReceiver")
            }
        })
    }

}