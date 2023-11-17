package com.example.definitionsdefectsbuildingstructures.data.model

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class Label (
    val id: String = UUID.randomUUID().toString(),
    var fileNames: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
)