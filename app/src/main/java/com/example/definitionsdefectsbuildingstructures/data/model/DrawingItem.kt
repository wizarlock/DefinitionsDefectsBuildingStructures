package com.example.definitionsdefectsbuildingstructures.data.model

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class DrawingItem(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var fileName: String = "",
    var labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
)