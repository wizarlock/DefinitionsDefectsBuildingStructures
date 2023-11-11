package com.example.definitionsdefectsbuildingstructures.data.model

import java.util.UUID

data class ProjectItem(
    val id: String = UUID.randomUUID().toString(),
    var name: String = ""
)