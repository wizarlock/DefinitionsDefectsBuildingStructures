package com.example.definitionsdefectsbuildingstructures.ui.components.projects

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import com.example.definitionsdefectsbuildingstructures.data.model.ProjectItem


@Composable
fun ListOfProjects(paddingValues: PaddingValues, projects: List<ProjectItem>) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        items(projects) { project ->
            ProjectCard(project)
        }
    }
}