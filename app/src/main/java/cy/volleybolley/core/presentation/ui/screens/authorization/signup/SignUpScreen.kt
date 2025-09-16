package cy.volleybolley.core.presentation.ui.screens.authorization.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.RegistrationByPhoneRoute
import cy.volleybolley.core.presentation.ui.navigation.RegistrationRoute

@Composable
fun SignUpScreen(
    navController: NavHostController,
    vm: SignUpViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                is SignUpEffect.NavigateToRegistrationByPhone -> navController.navigate(RegistrationByPhoneRoute)
                is SignUpEffect.NavigateToRegistration -> navController.navigate(RegistrationRoute)
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg2_launch),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = VolleyDimens.DIMEN_20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_28.dp))

            VolleyText.TitleXLAlt(
                text = stringResource(id = R.string.sign_up),
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = VolleyDimens.DIMEN_112.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = VolleyColor.TurquoiseDark,
                    shape = RoundedCornerShape(
                        topStart = VolleyDimens.DIMEN_24.dp,
                        topEnd = VolleyDimens.DIMEN_24.dp
                    )
                )
                .padding(
                    start = VolleyDimens.DIMEN_16.dp,
                    end = VolleyDimens.DIMEN_16.dp,
                    top = VolleyDimens.DIMEN_20.dp,
                    bottom = VolleyDimens.DIMEN_20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { vm.obtainEvent(SignUpEvent.ContinueWithPhoneClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.YellowPro),
                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp),
                contentPadding = PaddingValues(horizontal = VolleyDimens.DIMEN_16.dp)
            ) {
                VolleyText.BodyBoldMedium(
                    text = stringResource(id = R.string.continue_with_phone_number),
                    color = VolleyColor.TextDark
                )
            }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_12.dp))

            Button(
                onClick = { vm.obtainEvent(SignUpEvent.ContinueWithGoogleClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.White),
                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp),
                contentPadding = PaddingValues(
                    start = VolleyDimens.DIMEN_16.dp,
                    end = VolleyDimens.DIMEN_16.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(VolleyDimens.DIMEN_24.dp)
                        .padding(end = VolleyDimens.DIMEN_12.dp)
                )
                VolleyText.BodyBoldMedium(
                    text = stringResource(id = R.string.continue_with_google),
                    color = VolleyColor.TextDark
                )
            }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_12.dp))

            Button(
                onClick = { vm.obtainEvent(SignUpEvent.ContinueWithFacebookClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.BlueLight),
                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp),
                contentPadding = PaddingValues(
                    start = VolleyDimens.DIMEN_16.dp,
                    end = VolleyDimens.DIMEN_16.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_facebook_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(VolleyDimens.DIMEN_24.dp)
                        .padding(end = VolleyDimens.DIMEN_12.dp)
                )
                VolleyText.BodyBoldMedium(
                    text = stringResource(id = R.string.continue_with_facebook),
                    color = VolleyColor.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}
