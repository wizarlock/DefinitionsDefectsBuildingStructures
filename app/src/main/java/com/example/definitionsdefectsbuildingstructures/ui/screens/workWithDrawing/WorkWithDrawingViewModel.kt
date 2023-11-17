package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkWithDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(WorkWithDrawingUiState())
    val uiState = _uiState.asStateFlow()
    private var drawingItem = DrawingItem()

    init {
        viewModelScope.launch {
            drawingItem = repository.currentDrawing
        }

        _uiState.update {
            uiState.value.copy(
                fileName = drawingItem.fileName
            )
        }

        val pair = repository.getImageDimensions()

        _uiState.update {
            uiState.value.copy(
                width = pair.first
            )
        }

        _uiState.update {
            uiState.value.copy(
                height = pair.second
            )
        }
    }

    fun onUiAction(action: WorkWithDrawingAction) {
        when (action) {
            WorkWithDrawingAction.StartRecord -> repository.startRecording()
            WorkWithDrawingAction.StopRecord -> repository.stopRecording()
        }
    }
}

    data class WorkWithDrawingUiState(
        val fileName: String = "",
        val height: Int = 0,
        val width: Int = 0
    )