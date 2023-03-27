package cz.liftago.steamthresher

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

internal class LifecycleLogger : Application.ActivityLifecycleCallbacks {
    private fun log(method: String, activity: Activity, bundle: Bundle? = null) {
        Log.d("AppLifecycle", "${activity.localClassName}.$method(bundle:$bundle)")
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) =
        log("onActivityCreated", activity, bundle)

    override fun onActivityDestroyed(activity: Activity) =
        log("onActivityDestroyed", activity)

    override fun onActivityPaused(activity: Activity) =
        log("onActivityPaused", activity)

    override fun onActivityResumed(activity: Activity) =
        log("onActivityResumed", activity)

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) =
        log("onActivitySaveInstanceState", activity, outState)

    override fun onActivityStarted(activity: Activity) =
        log("onActivityStarted", activity)

    override fun onActivityStopped(activity: Activity) =
        log("onActivityStopped", activity)
}
