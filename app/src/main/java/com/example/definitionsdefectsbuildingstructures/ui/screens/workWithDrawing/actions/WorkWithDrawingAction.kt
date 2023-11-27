package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions



sealed class WorkWithDrawingAction {
    data class AddLabel(val imageX: Float, val imageY: Float, val fileName: String): WorkWithDrawingAction()

    data class StartRecord(val name: String) : WorkWithDrawingAction()

    object StopRecord : WorkWithDrawingAction()

    data class UpdatePhotoNum(val num: Int) : WorkWithDrawingAction()

    data class UpdateAudioNum(val num: Int) : WorkWithDrawingAction()

}