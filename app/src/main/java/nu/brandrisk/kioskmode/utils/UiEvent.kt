package nu.brandrisk.kioskmode.utils

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
}
