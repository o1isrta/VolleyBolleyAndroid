package cy.volleybolley

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.presentation.ui.component.VolleyButton
import cy.volleybolley.presentation.ui.model.VolleyColor
import cy.volleybolley.ui.theme.VolleybolleyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent {
//            VolleybolleyTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//            /* VolleyButton.ActiveButton(
//                 onClick = {},
//                 Modifier.padding(5.dp),
//                 true, "111"//, false
//             )*/
//        }


//        setContent {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = VolleyColor.TurquoiseDark)
//            ) {
//                Column {
//                    VolleyButton.ActiveButton(
//                        modifier = Modifier
//                            // .padding(it)
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "ACTIVE BUTTON",
//                        onClick = {}
//                    )
//                    VolleyButton.OutlinedActiveButton(
//                        modifier = Modifier
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "OUTLINED BUTTON",
//                        onClick = {}
//                    )
//                    VolleyButton.ActiveGradientButton(
//                        modifier = Modifier
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "Gradient button",
//                        onClick = {}
//                    )
//                }
//            }
//        }

//        setContent {
////            Box(
////                modifier = Modifier
////                    .fillMaxSize()
////                    .background(color = VolleyColor.TurquoiseDark)
////            ) {
//                LazyColumn(
//                    //state = rememberLazyListState(),
//                    modifier = Modifier.fillMaxSize()
//                        .background(color = VolleyColor.TurquoiseDark),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    item {
//                    VolleyButton.ActiveButton(
//                        modifier = Modifier
//                            // .padding(it)
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "ACTIVE BUTTON",
//                        onClick = {}
//                    )}
//                    item {
//                    VolleyButton.OutlinedActiveButton(
//                        modifier = Modifier
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "OUTLINED BUTTON",
//                        onClick = {}
//                    )}
//                    item {VolleyButton.ActiveGradientButton(
//                        modifier = Modifier
//                            .padding(24.dp)
//                            .height(44.dp)
//                            .fillMaxWidth(),
//                        text = "Gradient button",
//                        onClick = {}
//                    )}
//                    item {
//                        VolleyButton.OutlinedGradientButton(
//                            modifier = Modifier
//                                .padding(24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "Outlined gradient button",
//                            onClick = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckGradientButton(
//                            modifier = Modifier
//                                .padding(24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "CheckGradientButton. Checked = true",
//                            onClick = {},
//                            isChecked = true
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckGradientButton(
//                            modifier = Modifier
//                                .padding(24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "CheckGradientButton. Checked = false",
//                            onClick = {},
//                            isChecked = false
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonRightImage(
//                            modifier = Modifier
//                                 .padding(24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = true",
//                            onClick = {},
//                            isChecked = true
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonRightImage(
//                            modifier = Modifier
//                                .padding(24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = false",
//                            onClick = {},
//                            isChecked = false
//                        )
//                    }
////                    item {
////                        VolleyButton.CheckedGradientButtonTopImage(
////                            modifier = Modifier
////                                .padding(horizontal = 24.dp)
////                                .height(63.dp)
////                                .fillMaxWidth(),
////                            text = "isChecked = true",
////                            onClick = {},
////                            isChecked = true,
////                            //iconResId = R.drawable.mark_black
////                            iconPainter = painterResource(R.drawable.mark_black)
////                        )
////                    }
//                    item{
//                        VolleyButton.GroupButtonsForChangeLevel(
//                            1,
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.CenterHorizontally),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForDate2(
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForDate3(
//                            3,
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForPrivacy(
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForGender3(
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForGender2(
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForLevel(
//                            2,
//                            modifier = Modifier
//                                .padding(vertical = 12.dp),
//                                //.align(Alignment.Start),
//                            onSelected = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.GroupButtonsForTourneyType(
//                            modifier = Modifier
//                                .padding(vertical = 12.dp)
//                                //.align(Alignment.CenterHorizontally)
//                                .fillMaxWidth(),
//                            onSelected = {}
//                        )
//                    }
//                }

        setContent {
            VolleybolleyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = VolleyColor.TurquoiseDark)
//            ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(color = VolleyColor.TurquoiseDark),
                verticalArrangement = Arrangement.Center
            ) {
//                VolleyButton.ActiveButton(
//                    modifier = Modifier
//                        // .padding(it)
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "ACTIVE BUTTON",
//                    onClick = {}
//                )
//                VolleyButton.OutlinedActiveButton(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "OUTLINED BUTTON",
//                    onClick = {}
//                )
//                VolleyButton.ActiveGradientButton(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "Gradient button",
//                    onClick = {}
//                )
//                VolleyButton.OutlinedGradientButton(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "Outlined gradient button",
//                    onClick = {}
//                )
//                VolleyButton.CheckGradientButton(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "CheckGradientButton. Checked = true",
//                    onClick = {},
//                    isChecked = true
//                )
//                VolleyButton.CheckGradientButton(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "CheckGradientButton. Checked = false",
//                    onClick = {},
//                    isChecked = false
//                )
//                VolleyButton.CheckedGradientButtonRightImage(
//                    modifier = Modifier
//                        .padding(24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "isChecked = true",
//                    onClick = {},
//                    isChecked = true
//                )
                VolleyButton.CheckedGradientButtonRightImage(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(44.dp)
                        .fillMaxWidth(),
                    text = "isChecked = false",
                    onClick = {},
                    isChecked = false
                )
                VolleyButton.GroupButtonsForChangeLevel(
                    1,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    //.align(Alignment.CenterHorizontally),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForDate2(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    //.align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForDate3(
                    3,
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
                    2,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForTourneyType(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    onSelected = {}
                )
            }
        }
    }
}

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        VolleybolleyTheme {
            Greeting("Android")
        }
    }

    @Composable
    fun Root1(content: @Composable (PaddingValues) -> Unit) {
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

//    @Composable
//    fun
//            Demo() {
//        Root {
//            val lazyListState = rememberLazyListState()
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = VolleyColor.TurquoiseDark)
//
//                // .scrollState(scrollState)
//                //.verticalScroll(scrollState)
//            ) {
//                LazyColumn(
//                    state = lazyListState,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    item {
//                        VolleyButton.ActiveButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "ACTIVE BUTTON",
//                            onClick = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.OutlinedActiveButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "OUTLINED BUTTON",
//                            onClick = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.ActiveGradientButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "Gradient button",
//                            onClick = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.OutlinedGradientButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "Outlined gradient button",
//                            onClick = {}
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckGradientButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "CheckGradientButton. Checked = true",
//                            onClick = {},
//                            isChecked = true
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckGradientButton(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "CheckGradientButton. Checked = false",
//                            onClick = {},
//                            isChecked = false
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonRightImage(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = true",
//                            onClick = {},
//                            isChecked = true
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonRightImage(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(44.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = false",
//                            onClick = {},
//                            isChecked = false
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonTopImage(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(63.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = true",
//                            onClick = {},
//                            isChecked = true,
//                            //iconResId = R.drawable.mark_black
//                            iconPainter = painterResource(id = R.drawable.mark_black)
//                        )
//                    }
//                    item {
//                        VolleyButton.CheckedGradientButtonTopImage(
//                            modifier = Modifier
//                                .padding(it)
//                                .padding(horizontal = 24.dp)
//                                .height(63.dp)
//                                .fillMaxWidth(),
//                            text = "isChecked = false",
//                            onClick = {},
//                            isChecked = false,
//                            //iconResId = R.drawable.mark_gradient
//                            iconPainter = painterResource(id = R.drawable.mark_gradient)
//                        )
//                    }
//                }
//            }
//        }
//    }
