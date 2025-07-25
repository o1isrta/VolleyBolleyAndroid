package cy.volleybolley.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import cy.volleybolley.core.presentation.ui.navigation.NavHostContainer
import cy.volleybolley.ui.theme.Background
import cy.volleybolley.ui.theme.VolleybolleyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleybolleyTheme {
                RootContainer { innerPadding ->
                    NavHostContainer(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RootContainer(
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Background,
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    VolleybolleyTheme {
        RootContainer { padding ->
            NavHostContainer(modifier = Modifier.padding(padding))
        }
    }
}