package com.example.definitionsdefectsbuildingstructures.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfRenderer
import android.media.MediaRecorder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem
import com.example.definitionsdefectsbuildingstructures.data.model.Label
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import org.apache.commons.io.FileUtils
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
    override var currentLabel = Label()

    override suspend fun addProject(projectItem: ProjectItem) {
        _projectItems.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(projectItem)
            updatedList.toList()
        }
    }

    override suspend fun removeProject() {
        _projectItems.update { currentList ->
            currentList.filterNot { it == currentProject }
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

    override suspend fun removeDrawing(drawing: DrawingItem) {
        currentProject.drawings.update { currentList ->
            currentList.filterNot { it == drawing }
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
        val outputDir =
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val parcelFileDescriptor =
            ParcelFileDescriptor.open(_pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(parcelFileDescriptor)
        val pdfPage: PdfRenderer.Page = pdfRenderer.openPage(0)
        val bitmap: Bitmap =
            Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        val outputFileName = drawingItem.fileName
        val outputFile = File(outputDir, outputFileName)
        saveBitmapToFile(bitmap, outputFile)
        pdfPage.close()
        pdfRenderer.close()
    }

    @Throws(IOException::class)
    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File) {
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }

    override fun startRecording(name: String) {
        val outputDir =
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val outputFileName = "$name.3gp"
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

    override fun getImageDimensions(): Pair<Int, Int> {
        val options = BitmapFactory.Options()
        val dir =
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(dir.toString() + "/" + currentDrawing.fileName, options)
        return Pair(options.outWidth, options.outHeight)
    }

    override suspend fun addLabel(
        imageX: Float,
        imageY: Float,
        fileName: String,
        width: Float,
        height: Float
    ) {
        val newLabel =
            Label(x = imageX, y = imageY, fileName = fileName, width = width, height = height)
        currentDrawing.labels.update { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.add(newLabel)
            updatedList.toList()
        }
    }

    override suspend fun removeLabel() {
        currentDrawing.labels.update { currentList ->
            currentList.filterNot { it == currentLabel }
        }
    }

    override suspend fun updateLabel(newFileName: String) {
        val dir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val oldFile = File(dir, currentLabel.fileName)
        val newFile = File(dir, newFileName)
        FileUtils.copyFile(newFile, oldFile)
        val directoryPath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Checkpoint-K"
        )
        if (!directoryPath.exists()) {
            directoryPath.mkdirs()
        }
        val file = File(
            directoryPath,
            currentLabel.fileName
        )
        FileUtils.copyFile(newFile, file)
        newFile.delete()
    }

    override suspend fun saveProgress() {
        val directoryPath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Checkpoint-K"
        )
        if (!directoryPath.exists()) {
            directoryPath.mkdirs()
        }
        val oldFile = File(
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            currentDrawing.fileName
        )
        val newFile = File(
            directoryPath,
            "${UUID.randomUUID()}.png"
        )
        val realPair = getImageDimensions()
        withContext(Dispatchers.IO) {
            newFile.createNewFile()
            val originalBitmap = BitmapFactory.decodeFile(oldFile.absolutePath)
            val newBitmap = Bitmap.createBitmap(
                originalBitmap.width,
                originalBitmap.height,
                originalBitmap.config
            )
            val canvas = Canvas(newBitmap)
            canvas.drawBitmap(originalBitmap, 0f, 0f, null)
            val circlePaint = Paint().apply {
                color = Color.RED
            }
            val strokePaint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 3f
            }
            val textPaint = Paint().apply {
                color = Color.BLACK
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textSize = 13f
            }
            currentDrawing.labels.value.forEach { entry ->
                val cofForWidth = realPair.first / entry.width
                val cofForHeight = realPair.second / entry.height
                canvas.drawCircle(cofForWidth * entry.x, cofForHeight * entry.y, 15f, circlePaint)
                canvas.drawCircle(cofForWidth * entry.x, cofForHeight * entry.y, 15f, strokePaint)
                val text = takeLastDigits(entry.fileName)
                canvas.drawText(
                    text,
                    cofForWidth * entry.x - textPaint.textSize / 2,
                    cofForHeight * entry.y + textPaint.textSize / 2,
                    textPaint
                )
            }
            FileOutputStream(newFile).use { out ->
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        }
        MediaScannerConnection.scanFile(
            applicationContext,
            arrayOf(newFile.absolutePath),
            null
        ) { _, _ -> }
    }

    private fun takeLastDigits(name: String): String {
        val list = name.split(".")
        return if (list[0].length > 1) list[0].takeLast(2)
        else "0" + list[0]
    }
}
