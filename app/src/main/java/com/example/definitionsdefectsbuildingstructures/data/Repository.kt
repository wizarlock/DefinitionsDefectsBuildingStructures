package com.example.definitionsdefectsbuildingstructures.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.pdf.PdfRenderer
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
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : RepositoryInterface {
    private val _projectItems: MutableStateFlow<List<ProjectItem>> = MutableStateFlow(listOf())
    override var pdfFile: File? = null
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
                pdfFile = withContext(Dispatchers.IO) {
                    File.createTempFile("output", ".pdf", applicationContext.cacheDir)
                }
                applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val outputStream = FileOutputStream(pdfFile)
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

    override fun convertPdfPageToPng() {
        try {
            val outputDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

            val parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)

            // Получение первой страницы PDF
            val pdfPage: PdfRenderer.Page = pdfRenderer.openPage(0)

            // Создание Bitmap для рендеринга страницы PDF
            val bitmap: Bitmap = Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)

            // Рендеринг страницы PDF в Bitmap
            pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            // Создание файла для сохранения изображения
            val outputFileName = "output_page1.png"
            val outputFile = File(outputDir, outputFileName)

            // Сохранение изображения в файл
            saveBitmapToFile(bitmap, outputFile)

            // Закрываем страницу и PdfRenderer
            pdfPage.close()
            pdfRenderer.close()

            println("PDF успешно преобразован в изображение и сохранен в файле: ${outputFile.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Не удалось преобразовать PDF в изображение.")
        }
    }

    // Функция для сохранения Bitmap в файл
    @Throws(IOException::class)
    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        val outputStream = FileOutputStream(outputFile)
        try {
            // Используйте метод compress с PNG, чтобы избежать потери данных
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } finally {
            outputStream.close()
        }
    }
}
