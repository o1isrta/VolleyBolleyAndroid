package cy.volleybolley.core.presentation

import android.app.Application
import cy.volleybolley.BuildConfig
import cy.volleybolley.core.DiProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            if (BuildConfig.DEBUG) {
                androidLogger(level = Level.DEBUG)
            }
            modules(DiProvider.modules)
        }
    }
}