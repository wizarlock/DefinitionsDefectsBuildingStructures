package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions

import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem

sealed class DrawingsAction {
    data class DeleteDrawing(val drawing: DrawingItem) : DrawingsAction()
    object StartRecord : DrawingsAction()
    object StopRecord : DrawingsAction()
    object DeleteProject : DrawingsAction()
}

