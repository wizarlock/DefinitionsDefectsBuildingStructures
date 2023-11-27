package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions

import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

sealed class DrawingsAction {
    data class DeleteDrawing(val drawing: DrawingItem) : DrawingsAction()
    data class StartRecord(val name: String) : DrawingsAction()

    data class UpdateAudioNum(val num: Int) : DrawingsAction()

    object StopRecord : DrawingsAction()
    object DeleteProject : DrawingsAction()
}

