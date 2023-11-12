package com.example.definitionsdefectsbuildingstructures.data

import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectItems: StateFlow<List<ProjectItem>>
    suspend fun addProject(projectItem: ProjectItem)

    suspend fun removeProject(id: String)

    suspend fun getProject(id: String): ProjectItem?

    suspend fun addDrawing(projectId: String, drawingItem: DrawingItem)

    suspend fun removeDrawing(projectId: String, drawingId: String)

    suspend fun getDrawing(projectId: String, drawingId: String): DrawingItem?

    suspend fun getDrawings(projectId: String): StateFlow<List<DrawingItem>>
}