package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.actions

sealed class AddDrawingAction {
    data class UpdateDrawingName(val name: String) : AddDrawingAction()
    object SaveDrawing : AddDrawingAction()
}
