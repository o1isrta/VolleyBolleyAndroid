package cy.volleybolley

import android.app.Application
import cy.volleybolley.auth.di.authViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(authViewModelModule)
        }
    }
}