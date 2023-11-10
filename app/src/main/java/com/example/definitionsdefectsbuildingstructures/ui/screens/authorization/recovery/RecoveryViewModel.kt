package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery

import androidx.lifecycle.ViewModel
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

    data class RecoveryUiState(
        val email: String = "",
        val phoneNum: String = "",
        val password: String = "",
        val repeatPassword: String = ""
    )
}