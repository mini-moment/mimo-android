package com.mimo.android.presentation.util

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
}


fun<T> handleUiState(result : UiState<T>) : Any? {
    return when(result){
        is UiState.Success -> result.data
        is UiState.Error -> result.message
        is UiState.Loading -> null
    }
}