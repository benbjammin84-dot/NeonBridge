package com.neonbridge.ui

import android.app.Activity
import android.app.AlertDialog

object BridgePrompt {
    fun show(activity: Activity, title: String = "Confirm", message: String = "Continue?", callback: ((Boolean) -> Unit)? = null) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes") { _, _ -> callback?.invoke(true) }
            .setNegativeButton("No") { _, _ -> callback?.invoke(false) }
            .show()
    }

    // Convenience overloads used in the app
    fun show(activity: Activity, callback: (Boolean) -> Unit) = show(activity, "Confirm", "Continue?", callback)
    fun show(activity: Activity, title: String, message: String) = show(activity, title, message, null)
}
