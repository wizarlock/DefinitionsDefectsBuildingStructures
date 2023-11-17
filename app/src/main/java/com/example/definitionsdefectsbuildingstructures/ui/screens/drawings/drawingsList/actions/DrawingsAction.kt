package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions

sealed class DrawingsAction {
    object StartRecord : DrawingsAction()
    object StopRecord : DrawingsAction()
}