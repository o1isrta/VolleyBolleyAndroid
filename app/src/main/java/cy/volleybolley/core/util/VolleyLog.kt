package cy.volleybolley.core.util

import android.util.Log
import cy.volleybolley.BuildConfig

object VolleyLog {
    @JvmStatic
    fun v(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.IS_LOG_ENABLED) {
            Log.v(tag, msg, throwable)
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.IS_LOG_ENABLED) {
            Log.d(tag, msg, throwable)
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.IS_LOG_ENABLED) {
            Log.i(tag, msg, throwable)
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.IS_LOG_ENABLED) {
            Log.w(tag, msg, throwable)
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.IS_LOG_ENABLED) {
            Log.e(tag, msg, throwable)
        }
    }
}
