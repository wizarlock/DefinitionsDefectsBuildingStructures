package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidEmail
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidPassword
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.actions.LogInUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateBoolean = MutableStateFlow(LogInUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()

    fun onUiAction(action: LogInUiAction) {
        when (action) {
            is LogInUiAction.UpdateEmail -> _uiState.update {
                uiState.value.copy(email = action.email)
            }

            is LogInUiAction.UpdatePassword -> _uiState.update {
                uiState.value.copy(password = action.password)
            }
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidEmail = isValidEmail(uiState.value.email)
        val isValidPassword = isValidPassword(uiState.value.password)
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(email = isValidEmail)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(password =  isValidPassword)
        }
        return isValidEmail && isValidPassword
    }

    data class LogInUiStateBoolean(
        val email: Boolean = true,
        val password: Boolean = true
    )

    data class LogInUiState(
        val email: String = "",
        val password: String = ""
    )
}