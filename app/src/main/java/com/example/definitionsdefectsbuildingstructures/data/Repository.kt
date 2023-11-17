package com.example.definitionsdefectsbuildingstructures.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
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
import java.util.UUID
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : RepositoryInterface {
    private var recorder: MediaRecorder? = null
    private val _projectItems: MutableStateFlow<List<ProjectItem>> = MutableStateFlow(listOf())
    private var _pdfFile: File? = null
    override val projectItems = _projectItems.asStateFlow()
    override var currentProject = ProjectItem()
    override var currentDrawing = DrawingItem()

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

    override fun convertPdfPageToPng(drawingItem: DrawingItem) {
        try {
            val outputDir =
                applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val parcelFileDescriptor =
                ParcelFileDescriptor.open(_pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)
            val pdfPage: PdfRenderer.Page = pdfRenderer.openPage(0)
            val bitmap: Bitmap =
                Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
            pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            val outputFileName = "${drawingItem.fileName}.png"
            val outputFile = File(outputDir, outputFileName)
            saveBitmapToFile(bitmap, outputFile)
            pdfPage.close()
            pdfRenderer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }

    override fun startRecording() {
        val outputDir =
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val outputFileName = UUID.randomUUID().toString() + ".3gp"
        addRecording(outputFileName)
        val outputFile =
            outputDir.toString() + "/" + outputFileName
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)

            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addRecording(name: String) {
        currentProject.recordings.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(name)
            updatedList.toList()
        }
    }
    override fun stopRecording() {
        if (recorder != null) {
            recorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            recorder = null
        }
    }
}
