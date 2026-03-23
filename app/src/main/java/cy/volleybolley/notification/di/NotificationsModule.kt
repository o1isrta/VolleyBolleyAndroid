package cy.volleybolley.notification.di

import android.content.Context
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.notification.data.impl.DeviceTokenRepositoryImpl
import cy.volleybolley.notification.data.impl.FCMTokenStoreImpl
import cy.volleybolley.notification.data.impl.NotificationPermissionCheckerImpl
import cy.volleybolley.notification.data.impl.NotificationsRepositoryImpl
import cy.volleybolley.notification.data.network.firebase.DeviceTokenNetworkClient
import cy.volleybolley.notification.data.network.firebase.DeviceTokenRequest
import cy.volleybolley.notification.data.network.firebase.DeviceTokenResponse
import cy.volleybolley.notification.data.network.notifications.NotificationsNetworkClient
import cy.volleybolley.notification.data.network.notifications.NotificationsRequest
import cy.volleybolley.notification.data.network.notifications.NotificationsResponse
import cy.volleybolley.notification.domain.api.notifications.NotificationsRepository
import cy.volleybolley.notification.domain.api.notifications.NotificationsUseCase
import cy.volleybolley.notification.domain.api.permission.NotificationPermissionChecker
import cy.volleybolley.notification.domain.api.registration.DeviceTokenRepository
import cy.volleybolley.notification.domain.api.registration.SendDeviceTokenUseCase
import cy.volleybolley.notification.domain.api.storages.FCMTokenStore
import cy.volleybolley.notification.domain.impl.NotificationsUseCaseImpl
import cy.volleybolley.notification.domain.impl.SendDeviceTokenUseCaseImpl
import cy.volleybolley.notification.presentation.NotificationsViewModel
import cy.volleybolley.notification.utils.Constants.APP_PREFS
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notificationsModule = module {
    single<FCMTokenStore> {
        FCMTokenStoreImpl(
            prefs = androidContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        )
    }

    single<NotificationPermissionChecker> { NotificationPermissionCheckerImpl(androidContext()) }

    single<NetworkClient<DeviceTokenRequest, DeviceTokenResponse>>(
        qualifier = HttpClientQualifier.DEVICE_TOKEN.qualifier
    ) {
        DeviceTokenNetworkClient()
    }

    single<DeviceTokenRepository> {
        DeviceTokenRepositoryImpl(
            networkClient = get(HttpClientQualifier.DEVICE_TOKEN.qualifier)
        )
    }

    single<SendDeviceTokenUseCase> {
        SendDeviceTokenUseCaseImpl(repository = get())
    }

    single<NetworkClient<NotificationsRequest, NotificationsResponse>>(
        qualifier = HttpClientQualifier.NOTIFICATIONS.qualifier
    ) {
        NotificationsNetworkClient()
    }

    single<NotificationsRepository> {
        NotificationsRepositoryImpl(
            networkClient = get(HttpClientQualifier.NOTIFICATIONS.qualifier)
        )
    }

    single<NotificationsUseCase> {
        NotificationsUseCaseImpl(repository = get())
    }

    viewModel {
        NotificationsViewModel(notificationsUseCase = get())
    }
}
