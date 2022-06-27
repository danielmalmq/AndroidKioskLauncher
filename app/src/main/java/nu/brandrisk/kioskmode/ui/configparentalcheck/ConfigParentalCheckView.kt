package nu.brandrisk.kioskmode.ui.configparentalcheck

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import nu.brandrisk.kioskmode.utils.Routes
import kotlin.random.Random

@Composable
fun ConfigParentalCheckView(navController: NavHostController) {

    var inputNumber by remember { mutableStateOf("") }
    val randomNumber = remember {
        Pair(Random.nextInt(2, 7), Random.nextInt(2, 7))
    }

    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            text = buildAnnotatedString {
                append("What is ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${randomNumber.first} * ${randomNumber.second}")
                }

                append("?")
            }
        )

        TextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = inputNumber,
            onValueChange = {
                inputNumber = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (validateInput(inputNumber, randomNumber)) {
                    navController.navigate(Routes.CONFIG)  {
                        popUpTo(Routes.LAUNCHER) {
                            inclusive = false
                        }
                    }
                }
            })
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

fun validateInput(inputNumber: String, randomNumber: Pair<Int, Int>): Boolean {
    if (randomNumber.first * randomNumber.second == inputNumber.toIntOrNull()) {
        return true
    }
    return false
}
