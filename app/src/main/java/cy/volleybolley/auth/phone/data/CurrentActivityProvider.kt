package cy.volleybolley.auth.phone.data

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cy.volleybolley.core.util.VolleyLog
import java.lang.ref.WeakReference

interface CurrentActivityProvider {
    val currentActivity: Activity?
}

class CurrentActivityProviderImpl(
    application: Application
) : CurrentActivityProvider, Application.ActivityLifecycleCallbacks {

    private companion object {
        const val TAG = "CurrentActivityProvider"
    }

    private var activityRef: WeakReference<Activity>? = null

    init {
        VolleyLog.d(TAG, "init() registering activity lifecycle callbacks")
        application.registerActivityLifecycleCallbacks(this)
    }

    override val currentActivity: Activity?
        get() {
            val activity = activityRef?.get()
            VolleyLog.d(TAG, "currentActivity.get() = ${activity?.let { it::class.simpleName } ?: "null"}")
            return activity
        }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        VolleyLog.d(TAG, "onActivityCreated() ${activity::class.simpleName}")
        activityRef = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        VolleyLog.d(TAG, "onActivityStarted() ${activity::class.simpleName}")
        activityRef = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        VolleyLog.d(TAG, "onActivityResumed() ${activity::class.simpleName}")
        activityRef = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        VolleyLog.d(TAG, "onActivityPaused() ${activity::class.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        VolleyLog.d(TAG, "onActivityStopped() ${activity::class.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        VolleyLog.d(TAG, "onActivityDestroyed() ${activity::class.simpleName}")
        if (activityRef?.get() == activity) {
            activityRef?.clear()
        }
    }
}
