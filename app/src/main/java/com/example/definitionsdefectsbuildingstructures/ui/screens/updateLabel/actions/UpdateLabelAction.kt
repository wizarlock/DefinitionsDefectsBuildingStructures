package com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.actions


sealed class UpdateLabelAction {
    data class UpdateLabel(val fileName: String) : UpdateLabelAction()
    data class UpdatePhoto(val fileName: String) : UpdateLabelAction()

    object DeleteLabel : UpdateLabelAction()
}