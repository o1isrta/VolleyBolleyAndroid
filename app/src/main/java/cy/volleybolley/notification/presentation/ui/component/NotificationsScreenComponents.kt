package cy.volleybolley.notification.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyTitleBar.VolleyArrowBackTitleBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.notification.presentation.ui.component.NotificationsScreenComponents.NotificationListContent
import cy.volleybolley.notification.presentation.ui.model.NotificationItem
import cy.volleybolley.notification.presentation.ui.model.RouteScreen

object NotificationsScreenComponents {
    @Composable
    private fun NotificationContent(
        title: String,
        message: String,
        date: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_16))
                .background(VolleyColor.NotificationColor)
                .padding(VolleyDimens.DIMEN_16.dp)
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradientText(
                    text = title,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = date,
                    color = VolleyColor.White,
                    style = VolleyTypography.BodyLight
                )
            }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_4.dp))

            Text(
                text = message,
                color = VolleyColor.White,
                style = VolleyTypography.BodySmall
            )
        }
    }

    @Composable
    fun NotificationListContent(
        notifications: List<NotificationItem>,
        navController: NavHostController,
        onItemClick: (NotificationItem) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Box(modifier = Modifier.padding(VolleyDimens.DIMEN_8.dp)) {
                NotificationsList(
                    notifications = notifications,
                    onBackClick = { navController.popBackStack() },
                    onItemClick = onItemClick
                )
            }
        }
    }

    @Composable
    private fun NotificationsList(
        notifications: List<NotificationItem>,
        onBackClick: () -> Unit,
        onItemClick: (NotificationItem) -> Unit
    ) {
        TransparentContainer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                VolleyArrowBackTitleBar(
                    title = stringResource(R.string.notifications),
                    modifier = Modifier
                        .padding(
                            horizontal = VolleyDimens.DIMEN_20.dp,
                            vertical = VolleyDimens.DIMEN_12.dp
                        ),
                    onBackClick = onBackClick
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = VolleyDimens.DIMEN_20.dp),
                    contentPadding = PaddingValues(bottom = VolleyDimens.DIMEN_16.dp)
                ) {
                    itemsIndexed(notifications) { index, notification ->
                        NotificationContent(
                            title = notification.title,
                            message = notification.message,
                            date = notification.createdAt,
                            onClick = { onItemClick(notification) }
                        )
                        if (index < notifications.lastIndex) {
                            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun GradientText(
        text: String,
        modifier: Modifier = Modifier
    ) {
        val gradient = Brush.verticalGradient(
            colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
        )
        Text(
            text = text,
            modifier = modifier,
            style = VolleyTypography.BodyBold.copy(
                brush = gradient
            )
        )
    }
}

@Preview
@Composable
private fun PreviewNotificationsScreen() {
    val sampleData = listOf(
        NotificationItem(
            title = "New invite",
            message = "Anton Ivanov invited you",
            createdAt = "28.08.2025",
            screen = RouteScreen.JOIN_GAME.screenName,
            id = 1,
            gameId = "1",
        ),
        NotificationItem(
            title = "Removed from game",
            message = "1 September, 6:00–8:00 pm",
            createdAt = "27.08.2025",
            screen = RouteScreen.JOIN_GAME.screenName,
            id = 2,
            gameId = "2"
        ),
        NotificationItem(
            title = "Tourney cancelled",
            message = "12 September, 2:00–8:00 pm",
            createdAt = "26.08.2025",
            screen = RouteScreen.JOIN_GAME.screenName,
            id = 3,
            gameId = "3"
        ),
        NotificationItem(
            title = "Removed from tourney",
            message = "15 September, 9:00–11:00 am",
            createdAt = "25.08.2025",
            screen = RouteScreen.JOIN_GAME.screenName,
            id = 4,
            gameId = "4"
        ),
        NotificationItem(
            title = "Game cancelled",
            message = "5 September, 4:00–6:00 pm",
            createdAt = "24.09.2025",
            screen = RouteScreen.JOIN_GAME.screenName,
            id = 5,
            gameId = "5"
        )
    )
    Box() {
        NotificationListContent(
            navController = rememberNavController(),
            notifications = sampleData,
            onItemClick = {}
        )
    }
}
