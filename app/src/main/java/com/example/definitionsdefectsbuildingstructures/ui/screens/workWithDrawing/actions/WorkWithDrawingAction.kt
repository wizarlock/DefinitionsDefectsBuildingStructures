package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions



sealed class WorkWithDrawingAction {
    data class AddLabel(val imageX: Float, val imageY: Float, val fileName: String): WorkWithDrawingAction()

    object StartRecord : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()
}