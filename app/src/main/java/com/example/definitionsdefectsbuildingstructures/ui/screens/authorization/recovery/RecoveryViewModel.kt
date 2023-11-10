package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidEmail
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidPassword
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidPhoneNum
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidRepeatPassword
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery.actions.RecoveryAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(RecoveryUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateBoolean = MutableStateFlow(RecoveryUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()

    fun onUiAction(action: RecoveryAction) {
        when(action) {
            is RecoveryAction.UpdateEmail-> _uiState.update {
                uiState.value.copy(email = action.email)
            }
            is RecoveryAction.UpdatePhoneNum -> _uiState.update {
                uiState.value.copy(phoneNum = action.phoneNum)
            }
            is RecoveryAction.UpdatePassword -> _uiState.update {
                uiState.value.copy(password = action.password)
            }
            is RecoveryAction.UpdateRepeatPassword -> _uiState.update {
                uiState.value.copy(repeatPassword = action.repeatPassword)
            }
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidEmail = isValidEmail(uiState.value.email)
        val isValidPassword = isValidPassword(uiState.value.password)
        val isValidRepeatPassword = isValidRepeatPassword(uiState.value.password, uiState.value.repeatPassword)
        val isValidPhoneNum = isValidPhoneNum(uiState.value.phoneNum)
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(email = isValidEmail)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(password = isValidPassword)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(repeatPassword = isValidRepeatPassword)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(phoneNum = isValidPhoneNum)
        }
        return isValidEmail && isValidPassword && isValidRepeatPassword && isValidPhoneNum
    }

    data class RecoveryUiStateBoolean(
        val email: Boolean = true,
        val phoneNum: Boolean = true,
        val repeatPassword: Boolean = true,
        val password: Boolean = true
    )

    data class RecoveryUiState(
        val email: String = "",
        val phoneNum: String = "",
        val password: String = "",
        val repeatPassword: String = ""
    )
}