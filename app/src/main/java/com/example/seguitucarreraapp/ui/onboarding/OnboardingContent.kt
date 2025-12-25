import com.example.seguitucarreraapp.R
import com.example.seguitucarreraapp.ui.onboarding.OnboardingPage

val onboardingPages = listOf(
    OnboardingPage(
        title = "Bienvenido a SeguiTuCarrera",
        description = "Visualizá y organizá tu progreso académico de forma simple.",
        imageRes = null,
        showLogo = true
    ),
    OnboardingPage(
        title = "Seguí tus materias",
        description = "Registrá materias aprobadas, pendientes y tu avance.",
        imageRes = R.drawable.ic_logo_sin_texto
    ),
    OnboardingPage(
        title = "Entendé tu avance",
        description = "Visualizá tu progreso por año y cuatrimestre.",
        imageRes = R.drawable.ic_launcher_background
    ),
    OnboardingPage(
        title = "Llegá a tu meta",
        description = "Planificá tu carrera hasta alcanzar el título 🎓",
        imageRes = R.drawable.ic_launcher_background
    )
)
