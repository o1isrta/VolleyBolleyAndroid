package cy.volleybolley.core.presentation

import android.app.Application
import cy.volleybolley.BuildConfig
import cy.volleybolley.auth.phone.data.CurrentActivityProviderImpl
import cy.volleybolley.core.DiProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Eagerly initialized to register ActivityLifecycleCallbacks before any Activity is created
    val currentActivityProvider by lazy { CurrentActivityProviderImpl(this) }

    override fun onCreate() {
        super.onCreate()

        currentActivityProvider.hashCode()

        startKoin {
            androidContext(applicationContext)
            if (BuildConfig.IS_LOG_ENABLED) {
                androidLogger(level = Level.INFO)
            }
            modules(DiProvider.modules)
        }
    }
}
