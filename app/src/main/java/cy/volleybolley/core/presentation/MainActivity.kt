package cy.volleybolley.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cy.volleybolley.auth.ui.RegistrationByPhoneScreen
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.compose.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleybolleyTheme {
                RegistrationByPhoneScreen(
                    onAuthorized = { token -> Log.d("ТелефонТокен", token) },
                    onError = { message -> Log.d("Ошибка авторизации", message) },
                    phoneAuthHelper = getKoin().get(),
                    activityProvider = { this@MainActivity }
                )
            }
        }
    }
}
