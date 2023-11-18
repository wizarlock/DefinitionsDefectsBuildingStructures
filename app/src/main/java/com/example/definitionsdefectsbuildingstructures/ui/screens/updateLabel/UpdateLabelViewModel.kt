package com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.Label
import com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.actions.UpdateLabelAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateLabelViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateLabelUiState())
    val uiState = _uiState.asStateFlow()
    private var label = Label()

    init {
        viewModelScope.launch {
            label = repository.currentLabel
        }

        _uiState.update {
            uiState.value.copy(
                fileName = label.fileName
            )
        }
    }

    fun onUiAction(action: UpdateLabelAction) {
        when (action) {
            is UpdateLabelAction.UpdateLabel -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateLabel(action.fileName)
                }
            }
            is UpdateLabelAction.UpdatePhoto -> {
                _uiState.update {
                    uiState.value.copy(
                        fileName = action.fileName
                    )
                }
            }
            UpdateLabelAction.DeleteLabel -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.removeLabel()
                }
            }
        }
    }
}

data class UpdateLabelUiState(
    val fileName: String = ""
)