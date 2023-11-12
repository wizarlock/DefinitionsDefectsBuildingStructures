package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.projectsList

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    val projects = repository.projectItems

    fun updateProject(projectItem: ProjectItem) {
        repository.currentProject = projectItem
    }
}
