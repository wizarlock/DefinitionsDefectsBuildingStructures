package com.example.definitionsdefectsbuildingstructures.data

import android.content.Context
import android.net.Uri
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : RepositoryInterface {
    private val _projectItems: MutableStateFlow<List<ProjectItem>> = MutableStateFlow(listOf())
    private var _pdfFile: File? = null
    override val projectItems = _projectItems.asStateFlow()
    override var currentProject = ProjectItem()
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

    override suspend fun addDrawing(drawingItem: DrawingItem) {
        currentProject.drawings.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(drawingItem)
            updatedList.toList()
        }
    }

    override suspend fun removeDrawing(drawingId: String) {
        val containsDrawingItem = currentProject.drawings.value.any { it.id == drawingId }
        if (containsDrawingItem) {
            currentProject.drawings.update { currentList ->
                currentList.filter { it.id != drawingId }
            }
        }
    }

    override suspend fun loadDrawing(uri: Uri?): Boolean {
        if (uri != null) {
            try {
                _pdfFile = withContext(Dispatchers.IO) {
                    File.createTempFile("output", ".pdf", applicationContext.cacheDir)
                }
                applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val outputStream = FileOutputStream(_pdfFile)
                    inputStream.copyTo(outputStream)
                    outputStream.close()

                    return true
                }
            } catch (e: IOException) {
                return false
            }
        }
        return false
    }
}