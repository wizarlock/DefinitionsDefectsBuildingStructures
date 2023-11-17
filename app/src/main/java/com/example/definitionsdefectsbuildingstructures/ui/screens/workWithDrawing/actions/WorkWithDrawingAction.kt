package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions


sealed class WorkWithDrawingAction {
    object StartRecord : WorkWithDrawingAction()
    object StopRecord : WorkWithDrawingAction()
}