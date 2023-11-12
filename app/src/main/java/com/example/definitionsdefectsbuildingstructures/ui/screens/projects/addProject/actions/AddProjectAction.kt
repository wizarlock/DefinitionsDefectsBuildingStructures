package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject.actions

sealed class AddProjectAction {
    data class UpdateProjectName(val name: String) : AddProjectAction()
    object SaveProject : AddProjectAction()
}
