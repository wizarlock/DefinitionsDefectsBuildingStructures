package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp.actions

sealed class SignUpAction {
    data class UpdateEmail(val email: String) : SignUpAction()
    data class UpdatePhoneNum(val phoneNum: String) : SignUpAction()
    data class UpdateName(val name: String) : SignUpAction()
    data class UpdateSurname(val surname: String) : SignUpAction()
    data class UpdatePatronymic(val patronymic: String) : SignUpAction()
    data class UpdatePassword(val password: String) : SignUpAction()
}