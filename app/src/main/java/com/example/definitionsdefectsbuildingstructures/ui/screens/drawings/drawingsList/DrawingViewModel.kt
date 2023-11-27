package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.datastore.DataStoreManager
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions.DrawingsAction
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStoreManager: DataStoreManager
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
                    projectName = projectItem.name,
                    drawings = projectItem.drawings
                )
            }
            viewModelScope.launch {
                dataStoreManager.userPreferences.collectLatest { userPref ->
                    _uiState.update {
                        uiState.value.copy(
                            audioNum =  userPref.audioNum
                        )
                    }
                }
            }
        }
    }

    fun onUiAction(action: DrawingsAction) {
        when (action) {
            is DrawingsAction.DeleteDrawing -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeDrawing(action.drawing)
                }
            }

            is DrawingsAction.StartRecord -> repository.startRecording(action.name)

            DrawingsAction.StopRecord -> repository.stopRecording()

            DrawingsAction.DeleteProject -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeProject()
                }
            }
            is DrawingsAction.UpdateAudioNum -> {
                _uiState.update {
                    uiState.value.copy(
                        audioNum = action.num
                    )
                }
                viewModelScope.launch {
                    dataStoreManager.updateAudioNum(action.num)
                }
            }
        }
    }
}

data class DrawingUiState(
    val projectName: String = "",
    val drawings: MutableStateFlow<List<DrawingItem>> = MutableStateFlow(listOf()),
    val audioNum: Int = 0
)
