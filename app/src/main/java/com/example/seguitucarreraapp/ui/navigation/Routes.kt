sealed class Routes(val route: String) {
    object Auth : Routes("auth")
    object Home : Routes("home")
    object Materias : Routes("materias") // 👈 NUEVA
}
