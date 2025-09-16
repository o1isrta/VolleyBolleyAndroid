package cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography

@Composable
fun AboutLevelsScreen(
    navController: NavHostController,
    vm: AboutLevelsViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                is AboutLevelsEffect.NavigateBack -> navController.popBackStack()
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .padding(
                top = VolleyDimens.DIMEN_38.dp,
                start = VolleyDimens.DIMEN_16.dp,
                end = VolleyDimens.DIMEN_16.dp
            )
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter),
            cornerRadius = VolleyDimens.DIMEN_24,
            mainContainerAlignment = Alignment.TopStart,
            contentContainerAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = VolleyColor.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(VolleyDimens.DIMEN_24.dp)
                            .clickable { vm.obtainEvent(AboutLevelsEvent.OnBackClicked) }
                    )
                    VolleyText.TitleLarge(
                        text = stringResource(id = R.string.about_levels),
                        color = VolleyColor.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                )

                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = stringResource(id = R.string.level_light_prefix),
                        style = VolleyTypography.BodyBold.copy(brush = gradientBrush)
                    )
                    Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    VolleyText.BodyRegular(
                        text = stringResource(id = R.string.level_light_description),
                        color = VolleyColor.White
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = stringResource(id = R.string.level_medium_prefix),
                        style = VolleyTypography.BodyBold.copy(brush = gradientBrush)
                    )
                    Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    VolleyText.BodyRegular(
                        text = stringResource(id = R.string.level_medium_description),
                        color = VolleyColor.White
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = stringResource(id = R.string.level_hard_prefix),
                        style = VolleyTypography.BodyBold.copy(brush = gradientBrush)
                    )
                    Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    VolleyText.BodyRegular(
                        text = stringResource(id = R.string.level_hard_description),
                        color = VolleyColor.White
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = stringResource(id = R.string.level_pro_prefix),
                        style = VolleyTypography.BodyBold.copy(brush = gradientBrush)
                    )
                    Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    VolleyText.BodyRegular(
                        text = stringResource(id = R.string.level_pro_description),
                        color = VolleyColor.White
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutLevelsScreenPreview() {
    AboutLevelsScreen(navController = rememberNavController())
}
