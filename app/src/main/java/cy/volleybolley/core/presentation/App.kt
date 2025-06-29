package cy.volleybolley.core.presentation

import android.app.Application
import cy.volleybolley.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(coreModule)
        }
    }
}