package com.example.definitionsdefectsbuildingstructures.data.model

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class ProjectItem(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var drawings: MutableStateFlow<List<DrawingItem>> = MutableStateFlow(listOf()),
    var recordings: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
)