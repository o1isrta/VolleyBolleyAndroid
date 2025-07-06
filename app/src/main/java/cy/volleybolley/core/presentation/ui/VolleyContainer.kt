package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cy.volleybolley.ui.theme.VolleybolleyTheme

object VolleyContainer {

    @Composable
    fun Root(content: @Composable (PaddingValues) -> Unit) {
        VolleybolleyTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    content(paddingValues)

                }
            }
        }
    }

}