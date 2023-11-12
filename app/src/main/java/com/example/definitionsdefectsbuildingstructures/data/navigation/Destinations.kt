package com.example.definitionsdefectsbuildingstructures.data.navigation
import androidx.navigation.navArgument
import androidx.navigation.NavType

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

    const val routeWithArgs = "drawings/{id}"

    val arguments = listOf(
        navArgument(id) {
            type = NavType.StringType
        }
    )
    fun navToOrderWithArgs(
        id: String = ""
    ): String {
        return "$route/$id"
    }
}

object AddDrawing : Destination {
    const val id = "id"
    override val route: String = "addDrawing"
}
