package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing

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
import javax.inject.Inject

@HiltViewModel
class AddDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private var drawingItem = DrawingItem()
    private val _uiState = MutableStateFlow(AddDrawingUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiStateBoolean = MutableStateFlow(AddDrawingUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()

    fun onUiAction(action: AddDrawingAction) {
        when (action) {
            is AddDrawingAction.UpdateDrawingName -> _uiState.update {
                uiState.value.copy(drawingName = action.name)
            }

            AddDrawingAction.SaveDrawing -> saveDrawing()
        }
    }

    private fun saveDrawing() {
        drawingItem.name = uiState.value.drawingName
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDrawing(drawingItem)
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidDrawingName = isValidProjectOrDrawingName(uiState.value.drawingName)
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(drawingName = isValidDrawingName)
        }

        return isValidDrawingName
    }

    data class AddDrawingUiStateBoolean(
        val drawingName: Boolean = true
    )

    data class AddDrawingUiState(
        val drawingName: String = ""
    )
}