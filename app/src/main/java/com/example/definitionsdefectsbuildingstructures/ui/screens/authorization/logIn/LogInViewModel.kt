package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.actions.LogInUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: LogInUiAction) {
        when(action) {
            is LogInUiAction.UpdateEmail -> _uiState.update {
                uiState.value.copy(email = action.email)
            }
            is LogInUiAction.UpdatePassword -> _uiState.update {
                uiState.value.copy(password = action.password)
            }
        }
    }

    data class LogInUiState(
        val email: String = "",
        val password: String = ""
    )
}