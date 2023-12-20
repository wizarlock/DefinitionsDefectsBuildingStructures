package com.example.definitionsdefectsbuildingstructures.data.model

import java.util.UUID

data class Label (
    val id: String = UUID.randomUUID().toString(),
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 0f,
    var height: Float = 0f,
    var fileName: String = ""
)