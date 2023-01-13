package com.cdr.core.uiactions

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Android implementation of [UiActions].
 */
class AndroidUiActions(
    private val activity: AppCompatActivity,
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
    override fun showSnackbar(view: View, message: String, backgroundColor: Int, mainColor: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(appContext, backgroundColor))
        snackbar.setTextColor(ContextCompat.getColor(appContext, mainColor))
        snackbar.setActionTextColor(ContextCompat.getColor(appContext, mainColor))
        snackbar.setAction("OK") { snackbar.dismiss() }
        snackbar.show()
    }

    /**
     * Implementation of displaying a simple alert dialog.
     */
    override fun showAlertDialog(
        icon: Int,
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        positiveAction: Runnable
    ) {
        val listener = DialogInterface.OnClickListener { _, clickedButton ->
            when (clickedButton) {
                AlertDialog.BUTTON_POSITIVE -> positiveAction.run()
            }
        }

        val dialog = AlertDialog.Builder(activity)
            .setIcon(ContextCompat.getDrawable(appContext, icon))
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText, listener)
            .setNegativeButton(negativeButtonText, listener)

        dialog.show()
    }

    /**
     * Implementation of getting string resource.
     */
    override fun getString(res: Int): String = appContext.getString(res)
}