package cy.volleybolley.core.presentation.ui.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.SignUpRoute

@Composable
fun OnboardingScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_launch),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = VolleyDimens.DIMEN_28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_40.dp))

            VolleyText.TitleXL(
                text = stringResource(id = R.string.welcome),
                color = VolleyColor.White
            )

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyText.HeroBody(
                text = stringResource(id = R.string.app_description),
                color = VolleyColor.White
            )

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_80.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.size(VolleyDimens.DIMEN_200.dp) // DIMEN_200 добавлен
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                VolleyText.LogoDisplay(
                    text = stringResource(id = R.string.volleybolley),
                    color = VolleyColor.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = VolleyDimens.DIMEN_64.dp),
                contentAlignment = Alignment.Center
            ) {
                VolleyButton.ActiveButton(
                    text = stringResource(id = R.string.get_started),
                    modifier = Modifier
                        .width(VolleyDimens.DIMEN_335.dp)
                        .height(VolleyDimens.DIMEN_44.dp),
                    onClick = { navController.navigate(SignUpRoute) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}
