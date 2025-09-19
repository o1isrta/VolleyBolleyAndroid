package cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
            cornerRadius = VolleyDimens.DIMEN_32,
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
                    IconButton(
                        onClick = { vm.obtainEvent(AboutLevelsEvent.OnBackClicked) },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(VolleyDimens.DIMEN_24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(id = R.string.back),
                            tint = VolleyColor.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

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

                LevelItem(
                    prefixResId = R.string.level_light_prefix,
                    descriptionResId = R.string.level_light_description,
                    gradient = gradientBrush
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                LevelItem(
                    prefixResId = R.string.level_medium_prefix,
                    descriptionResId = R.string.level_medium_description,
                    gradient = gradientBrush
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                LevelItem(
                    prefixResId = R.string.level_hard_prefix,
                    descriptionResId = R.string.level_hard_description,
                    gradient = gradientBrush
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                LevelItem(
                    prefixResId = R.string.level_pro_prefix,
                    descriptionResId = R.string.level_pro_description,
                    gradient = gradientBrush
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_20.dp))
            }
        }
    }
}

@Composable
private fun LevelItem(prefixResId: Int, descriptionResId: Int, gradient: Brush) {
    val annotated = buildAnnotatedString {
        withStyle(style = SpanStyle(brush = gradient, fontWeight = FontWeight.Bold)) {
            append(stringResource(id = prefixResId))
            append(" ")
        }
        append(stringResource(id = descriptionResId))
    }

    Text(
        text = annotated,
        style = VolleyTypography.BodyRegular,
        color = VolleyColor.White
    )
}

@Preview(showBackground = true)
@Composable
private fun AboutLevelsScreenPreview() {
    AboutLevelsScreen(navController = rememberNavController())
}
