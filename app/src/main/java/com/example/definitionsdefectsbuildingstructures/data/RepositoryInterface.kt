package com.example.definitionsdefectsbuildingstructures.data

import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectItems: StateFlow<List<ProjectItem>>
    suspend fun addProject(projectItem: ProjectItem)
    suspend fun removeProject(id: String)
}