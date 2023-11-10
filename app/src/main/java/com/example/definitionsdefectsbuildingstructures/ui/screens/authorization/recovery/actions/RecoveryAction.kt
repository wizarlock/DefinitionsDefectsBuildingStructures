package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery.actions


sealed class RecoveryAction {
    data class UpdateEmail(val email: String) : RecoveryAction()
    data class UpdatePhoneNum(val phoneNum: String) : RecoveryAction()
    data class UpdatePassword(val password: String) : RecoveryAction()
    data class UpdateRepeatPassword(val repeatPassword: String) : RecoveryAction()
}