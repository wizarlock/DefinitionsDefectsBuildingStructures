package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.projectsList

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val repository: RepositoryInterface
): ViewModel() {
    val projects = repository.projectItems
}
