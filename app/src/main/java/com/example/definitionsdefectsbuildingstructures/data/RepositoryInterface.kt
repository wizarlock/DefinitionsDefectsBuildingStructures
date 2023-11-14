package com.example.definitionsdefectsbuildingstructures.data

import android.net.Uri
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectItems: StateFlow<List<ProjectItem>>

    var currentProject: ProjectItem

    suspend fun addProject(projectItem: ProjectItem)

    suspend fun removeProject(id: String)

    suspend fun getProject(id: String): ProjectItem?

    suspend fun addDrawing(drawingItem: DrawingItem)

    suspend fun removeDrawing(drawingId: String)

    suspend fun loadDrawing(uri: Uri?): Boolean

    fun convertPdfPageToPng()
}