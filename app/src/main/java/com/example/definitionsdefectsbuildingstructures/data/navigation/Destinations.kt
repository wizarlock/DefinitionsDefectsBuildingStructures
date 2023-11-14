package com.example.definitionsdefectsbuildingstructures.data.navigation


object Start : Destination {
    override val route: String = "start"
}

object SignUp : Destination {
    override val route: String = "signUp"
}

object LogIn : Destination {
    override val route: String = "logIn"
}

object Recovery : Destination {
    override val route: String = "recovery"
}

object ProjectsList : Destination {
    override val route: String = "projects"
}

object AddProject : Destination {
    override val route: String = "addProject"
}

object Drawings : Destination {
    const val id = "id"
    override val route: String = "drawings"
}

object AddDrawing : Destination {
    const val id = "id"
    override val route: String = "addDrawing"
}

object WorkWithDrawing : Destination {
    const val id = "id"
    override val route: String = "workWithDrawing"
}