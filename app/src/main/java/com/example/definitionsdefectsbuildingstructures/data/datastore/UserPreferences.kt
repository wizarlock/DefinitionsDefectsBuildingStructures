package com.example.definitionsdefectsbuildingstructures.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val photoNum: Int = 0,
    val audioNum: Int = 0
)