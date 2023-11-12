package com.example.definitionsdefectsbuildingstructures.data

import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class Repository @Inject constructor() : RepositoryInterface {
    private val _projectItems: MutableStateFlow<List<ProjectItem>> = MutableStateFlow(listOf())
    override val projectItems = _projectItems.asStateFlow()

    override suspend fun addProject(projectItem: ProjectItem) {
        _projectItems.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(projectItem)
            updatedList.toList()
        }
    }

    override suspend fun removeProject(id: String) {
        val containsTodoItem = _projectItems.value.any { it.id == id }
        if (containsTodoItem) {
            _projectItems.update { currentList ->
                currentList.filter { it.id != id }
            }
        }
    }
    override suspend fun getProject(id: String) = _projectItems.value.firstOrNull { it.id == id }

    override suspend fun addDrawing(projectId: String, drawingItem: DrawingItem) {
        val project = getProject(projectId)
        project?.drawings?.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(drawingItem)
            updatedList.toList()
        }
    }

    override suspend fun removeDrawing(projectId: String, drawingId: String) {
        val project = getProject(projectId)
        val containsDrawingItem = project?.drawings?.value?.any { it.id == drawingId }
        if (containsDrawingItem == true) {
            project.drawings.update { currentList ->
                currentList.filter { it.id != drawingId }
            }
        }
    }

    override suspend fun getDrawing(projectId: String, drawingId: String): DrawingItem {
        val project = getProject(projectId)
        return project?.drawings?.value?.firstOrNull { it.id == drawingId }!!
    }

    override suspend fun getDrawings(projectId: String): StateFlow<List<DrawingItem>> {
        return getProject(projectId)?.drawings!!.asStateFlow()
    }
}