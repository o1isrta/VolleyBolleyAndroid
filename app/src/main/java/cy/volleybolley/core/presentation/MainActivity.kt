package cy.volleybolley.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.navigation.NavHostContainer
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
@Stable
private fun ButtonDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = VolleyColor.TurquoiseDark),
        verticalArrangement = Arrangement.Center
    ) {
        VolleyButton.CheckedGradientButtonRightImage(
            modifier = Modifier
                .padding(24.dp)
                .height(44.dp)
                .align(Alignment.CenterHorizontally),
            isChecked = true,
            text = ISCHECKED_TRUE_TEXT,
            onClick = {}
        )
        VolleyButton.SliderButtonsMap(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp),
            onClick = {}
        )
        VolleyButton.GroupButtonsForChangeLevel(
            checkId = 1,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForDate2(
            checkId = 2,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForDate3(
            checkId = 3,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForPrivacy(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForGender3(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForGender2(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForLevel(
            checkId = 2,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
    }
}

@Composable
fun RootContainer(
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = VolleyColor.TurquoiseDark,
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
