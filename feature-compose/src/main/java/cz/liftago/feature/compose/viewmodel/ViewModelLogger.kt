package cz.liftago.feature.compose.viewmodel

import android.util.Log

object ViewModelLogger {

    private const val TAG = "ViewModelLifecycle"

    fun init(value: String) = Log.d(TAG, "starting: $value")

    fun clear(value: String) = Log.d(TAG, "clearing: $value")
}