package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidEmail
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidFullName
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidPassword
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidPhoneNum
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

    private val _uiStateBoolean = MutableStateFlow(SignUpUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()

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

    fun areFieldsValid(): Boolean {
        val isValidEmail = isValidEmail(uiState.value.email)
        val isValidPassword = isValidPassword(uiState.value.password)
        val isValidPhoneNum = isValidPhoneNum(uiState.value.phoneNum)
        val isValidName = isValidFullName(uiState.value.name)
        val isValidSurName = isValidFullName(uiState.value.surname)
        val isValidPatronymic = isValidFullName(uiState.value.patronymic)
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(email = isValidEmail)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(password = isValidPassword)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(name = isValidName)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(surname = isValidSurName)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(patronymic = isValidPatronymic)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(phoneNum = isValidPhoneNum)
        }
        return isValidEmail && isValidPassword && isValidPhoneNum && isValidName && isValidSurName && isValidPatronymic
    }

    data class SignUpUiStateBoolean(
        val email: Boolean = true,
        val phoneNum: Boolean = true,
        val name: Boolean = true,
        val surname: Boolean = true,
        val patronymic: Boolean = true,
        val password: Boolean = true
    )

    data class SignUpUiState(
        val email: String = "",
        val phoneNum: String = "",
        val name: String = "",
        val surname: String = "",
        val patronymic: String = "",
        val password: String = ""
    )
}