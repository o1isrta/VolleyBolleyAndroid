package cy.volleybolley.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.ui.theme.VolleybolleyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column {
                VolleybolleyTheme {
                    VolleyTopBar.TopBar(
                        "Andrey",
                        "https://avatars.mds.yandex.net/get-/15298/aPbyeCWI9oijiql2AFh3GaX3xyg-1/orig",
                        "LIGHT"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                VolleybolleyTheme {
                    VolleyTopBar.TopBar(
                        "Denis",
                        "https://avatars.mds.yandex.net/get-yapic/15298/aPbyeCWI9oijiql2AFh3GaX3xyg-1/orig",
                        "MEDIUM"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                VolleybolleyTheme {
                    VolleyTopBar.TopBar(
                        "Vitache",
                        null,
                        "HARD"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                VolleybolleyTheme {
                    VolleyTopBar.TopBar(
                        "Artem",
                        "https://avatars.mds.yandex.net/i?id=a92656606b6741a1ac9e32c966a2c796_l-12714643-images-thumbs&n=13",
                        "PRO"
                    )
                }
            }
        }
    }
}
