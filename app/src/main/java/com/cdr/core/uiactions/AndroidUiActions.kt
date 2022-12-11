package com.cdr.core.uiactions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cdr.mvvm_prototype.R
import com.google.android.material.snackbar.Snackbar

/**
 * Android implementation of [UiActions].
 */
class AndroidUiActions(
    private val appContext: Context
) : UiActions {

    /**
     * Implementation of displaying a simple toast message.
     */
    override fun showToast(message: String) =
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()

    /**
     * Implementation of displaying a simple snackbar.
     */
    override fun showSnackbar(view: View, message: String, backgroundColor: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(appContext, backgroundColor))
        snackbar.setTextColor(ContextCompat.getColor(appContext, R.color.white))
        snackbar.setActionTextColor(ContextCompat.getColor(appContext, R.color.white))
        snackbar.setAction("OK") { snackbar.dismiss() }
        snackbar.show()
    }

    /**
     * Implementation of getting string resource.
     */
    override fun getString(res: Int): String = appContext.getString(res)
}