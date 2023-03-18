package com.airbot.utils

sealed class UiEvent {
    data class Navigate(val route: String = "") : UiEvent()
    object OnBackNavigate : UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
}

