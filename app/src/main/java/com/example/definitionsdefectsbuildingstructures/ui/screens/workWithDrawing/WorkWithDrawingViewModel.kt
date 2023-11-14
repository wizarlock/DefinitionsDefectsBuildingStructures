package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import androidx.lifecycle.ViewModel
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkWithDrawingViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    val pdfFile = repository.pdfFile
}