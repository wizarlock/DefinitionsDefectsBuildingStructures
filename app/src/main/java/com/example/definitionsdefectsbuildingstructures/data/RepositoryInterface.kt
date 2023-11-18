package com.example.definitionsdefectsbuildingstructures.data

import android.net.Uri
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.Label
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import kotlinx.coroutines.flow.StateFlow

interface RepositoryInterface {
    val projectItems: StateFlow<List<ProjectItem>>

    var currentProject: ProjectItem

    var currentDrawing: DrawingItem

    suspend fun addProject(projectItem: ProjectItem)

    suspend fun removeProject()

    suspend fun getProject(id: String): ProjectItem?

    suspend fun addDrawing(drawingItem: DrawingItem)

    suspend fun removeDrawing(drawing: DrawingItem)

    suspend fun loadDrawing(uri: Uri?): Boolean

    fun convertPdfPageToPng(drawingItem: DrawingItem)

    fun startRecording()

    fun stopRecording()

    fun getImageDimensions(): Pair<Int, Int>

    suspend fun addLabel(imageX: Float, imageY: Float, fileName: String)

    suspend fun removeLabel(label: Label)

    suspend fun updateLabel(label: Label, newFileName: String)
}