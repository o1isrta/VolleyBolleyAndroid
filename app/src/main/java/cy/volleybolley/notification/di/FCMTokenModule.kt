package cy.volleybolley.notification.di

import android.content.Context
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.notification.data.DeviceTokenRepositoryImpl
import cy.volleybolley.notification.data.FCMTokenStoreImpl
import cy.volleybolley.notification.data.NotificationPermissionCheckerImpl
import cy.volleybolley.notification.data.network.DeviceTokenNetworkClient
import cy.volleybolley.notification.data.network.DeviceTokenRequest
import cy.volleybolley.notification.data.network.DeviceTokenResponse
import cy.volleybolley.notification.domain.SendDeviceTokenUseCaseImpl
import cy.volleybolley.notification.domain.api.FCMTokenStore
import cy.volleybolley.notification.domain.api.NotificationPermissionChecker
import cy.volleybolley.notification.domain.api.registration.DeviceTokenRepository
import cy.volleybolley.notification.domain.api.registration.SendDeviceTokenUseCase
import cy.volleybolley.notification.utils.Constants.APP_PREFS
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val fcmTokenModule = module {
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
}
