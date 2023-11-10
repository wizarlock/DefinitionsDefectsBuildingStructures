package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.actions

sealed class LogInUiAction {
    data class UpdateEmail(val email: String) : LogInUiAction()
    data class UpdatePassword(val password: String) : LogInUiAction()
}
