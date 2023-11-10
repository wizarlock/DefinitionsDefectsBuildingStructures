package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp.actions.SignUpAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: SignUpAction) {
        when(action) {
            is SignUpAction.UpdateEmail -> _uiState.update {
                uiState.value.copy(email = action.email)
            }
            is SignUpAction.UpdatePhoneNum -> _uiState.update {
                uiState.value.copy(phoneNum = action.phoneNum)
            }
            is SignUpAction.UpdateName -> _uiState.update {
                uiState.value.copy(name = action.name)
            }
            is SignUpAction.UpdateSurname -> _uiState.update {
                uiState.value.copy(surname = action.surname)
            }
            is SignUpAction.UpdatePatronymic -> _uiState.update {
                uiState.value.copy(patronymic = action.patronymic)
            }
            is SignUpAction.UpdatePassword -> _uiState.update {
                uiState.value.copy(password = action.password)
            }
        }
    }

    data class SignUpUiState(
        val email: String = "",
        val phoneNum: String = "",
        val name: String = "",
        val surname: String = "",
        val patronymic: String = "",
        val password: String = ""
    )
}