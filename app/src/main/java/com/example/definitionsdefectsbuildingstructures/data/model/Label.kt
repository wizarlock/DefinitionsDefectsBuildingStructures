package com.example.definitionsdefectsbuildingstructures.data.model

import java.util.UUID

data class Label (
    val id: String = UUID.randomUUID().toString(),
    var fileName: String = ""
)