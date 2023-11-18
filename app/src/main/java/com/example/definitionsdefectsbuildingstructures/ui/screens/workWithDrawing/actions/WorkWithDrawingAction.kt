package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions

import com.example.definitionsdefectsbuildingstructures.data.model.Label


sealed class WorkWithDrawingAction {
    data class AddLabel(val imageX: Float, val imageY: Float, val fileName: String): WorkWithDrawingAction()

    data class RemoveLabel(val label: Label): WorkWithDrawingAction()

    data class UpdateLabel(val label: Label, val newFileName: String): WorkWithDrawingAction()

    object StartRecord : WorkWithDrawingAction()
    object StopRecord : WorkWithDrawingAction()
}