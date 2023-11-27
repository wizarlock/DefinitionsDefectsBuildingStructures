package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.datastore.DataStoreManager
import com.example.definitionsdefectsbuildingstructures.data.datastore.UserPreferences
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.Label
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
class WorkWithDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkWithDrawingUiState())
    val uiState = _uiState.asStateFlow()

    private var drawingItem = DrawingItem()

    fun updateLabel(label: Label) {
        repository.currentLabel = label
    }

    init {
        viewModelScope.launch {
            drawingItem = repository.currentDrawing
        }
        val pair = repository.getImageDimensions()
        _uiState.update {
            uiState.value.copy(
                fileName = drawingItem.fileName,
                width = pair.first,
                height = pair.second,
                labels = drawingItem.labels
            )
        }

        viewModelScope.launch {
            dataStoreManager.userPreferences.collectLatest { userPref ->
                _uiState.update {
                    uiState.value.copy(
                        photoNum = userPref.photoNum,
                        audioNum =  userPref.audioNum
                    )
                }
            }
        }
    }

    fun onUiAction(action: WorkWithDrawingAction) {
        when (action) {
            is WorkWithDrawingAction.AddLabel -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.addLabel(
                        imageX = action.imageX, imageY = action.imageY, fileName = action.fileName
                    )
                }
            }

            is WorkWithDrawingAction.StartRecord -> repository.startRecording(action.name)
            WorkWithDrawingAction.StopRecord -> repository.stopRecording()
            is WorkWithDrawingAction.UpdatePhotoNum -> {
                _uiState.update {
                    uiState.value.copy(
                        photoNum = action.num
                    )
                }
                viewModelScope.launch {
                    dataStoreManager.updatePhotoNum(action.num)
                }
            }

            is WorkWithDrawingAction.UpdateAudioNum -> {
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

data class WorkWithDrawingUiState(
    val fileName: String = "",
    val height: Int = 0,
    val width: Int = 0,
    val labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf()),
    val initialScale: MutableState<Float> = mutableStateOf(1f),
    val initialOffsetX: MutableState<Float> = mutableStateOf(0f),
    val initialOffsetY: MutableState<Float> = mutableStateOf(0f),
    val photoNum: Int = 0,
    val audioNum: Int = 0
)