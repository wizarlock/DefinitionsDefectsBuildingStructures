package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidProjectOrDrawingName
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.actions.AddDrawingAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface,
) : ViewModel() {
    private var drawingItem = DrawingItem()
    private val _uiState = MutableStateFlow(AddDrawingUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiStateBoolean = MutableStateFlow(AddDrawingUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()
    fun onUiAction(action: AddDrawingAction) {
        when (action) {
            is AddDrawingAction.UpdateDrawingName -> {
                _uiState.update {
                    uiState.value.copy(drawingName = action.name)
                }
            }

            is AddDrawingAction.UpdateSelectedFile -> {
                viewModelScope.launch {
                    val isFileExists = repository.loadDrawing(uri = action.uri)
                    if (isFileExists) {
                        _uiState.update {
                            uiState.value.copy(drawingFileUri = action.uri)
                        }
                    }
                }
            }

            AddDrawingAction.SaveDrawing -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveDrawing()
                    repository.convertPdfPageToPng(drawingItem)
                }
            }
        }
    }

    private fun saveDrawing() {
        drawingItem.name = uiState.value.drawingName
        drawingItem.fileName = UUID.randomUUID().toString() + ".png"
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDrawing(drawingItem = drawingItem)
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidDrawingName = isValidProjectOrDrawingName(uiState.value.drawingName)
        val isFileLoaded = uiState.value.drawingFileUri != null
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(drawingName = isValidDrawingName)
        }
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(isFileExists = isFileLoaded)
        }
        return isValidDrawingName && isFileLoaded
    }

    data class AddDrawingUiStateBoolean(
        val drawingName: Boolean = true,
        val isFileExists: Boolean = true
    )

    data class AddDrawingUiState(
        val drawingName: String = "",
        val drawingFileUri: Uri? = null
    )
}