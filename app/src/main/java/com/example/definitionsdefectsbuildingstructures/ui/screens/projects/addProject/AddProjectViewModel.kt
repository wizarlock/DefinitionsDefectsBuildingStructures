package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.isValidProjectName
import com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject.actions.AddProjectAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    private var projectItem = ProjectItem()
    private val _uiState = MutableStateFlow(AddProjectUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiStateBoolean = MutableStateFlow(AddProjectUiStateBoolean())
    val uiStateBoolean = _uiStateBoolean.asStateFlow()

    fun onUiAction(action: AddProjectAction) {
        when (action) {
            is AddProjectAction.UpdateProjectName -> _uiState.update {
                uiState.value.copy(projectName = action.name)
            }

            AddProjectAction.SaveProject -> saveProject()
        }
    }

    private fun saveProject() {
        projectItem.name = uiState.value.projectName
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProject(projectItem)
        }
    }

    fun areFieldsValid(): Boolean {
        val isValidProjectName = isValidProjectName(uiState.value.projectName)
        _uiStateBoolean.update {
            uiStateBoolean.value.copy(projectName = isValidProjectName)
        }

        return isValidProjectName
    }

    data class AddProjectUiStateBoolean(
        val projectName: Boolean = true
    )

    data class AddProjectUiState(
        val projectName: String = ""
    )
}