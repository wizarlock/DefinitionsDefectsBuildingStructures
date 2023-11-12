package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import com.example.definitionsdefectsbuildingstructures.data.navigation.Drawings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState = _uiState.asStateFlow()
    private var projectItem = ProjectItem()

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(Drawings.id) ?: ""
            repository.getProject(id)?.let { item ->
                projectItem = item

                _uiState.update {
                    uiState.value.copy(
                        projectName = item.name
                    )
                }
                _uiState.update {
                    uiState.value.copy(
                        drawings = item.drawings
                    )
                }
            }
        }
    }
}

data class DrawingUiState(
    val projectName: String = "",
    val drawings: MutableStateFlow<List<DrawingItem>> = MutableStateFlow(listOf())
)
