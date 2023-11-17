package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions.DrawingsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState = _uiState.asStateFlow()
    private var projectItem = ProjectItem()

    fun updateDrawing(drawingItem: DrawingItem) {
        repository.currentDrawing = drawingItem
    }

    init {
        viewModelScope.launch {
            projectItem = repository.currentProject

            _uiState.update {
                uiState.value.copy(
                    projectName = projectItem.name
                )
            }
            _uiState.update {
                uiState.value.copy(
                    drawings = projectItem.drawings
                )
            }
        }
    }

    fun onUiAction(action: DrawingsAction) {
        when (action) {
            DrawingsAction.StartRecord -> repository.startRecording()
            DrawingsAction.StopRecord -> repository.stopRecording()
        }
    }
}

data class DrawingUiState(
    val projectName: String = "",
    val drawings: MutableStateFlow<List<DrawingItem>> = MutableStateFlow(listOf())
)
