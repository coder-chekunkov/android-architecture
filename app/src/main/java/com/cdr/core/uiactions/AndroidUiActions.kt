package com.cdr.core.uiactions

import android.content.Context
import android.widget.Toast

/**
 * Android implementation of [UiActions].
 */
class AndroidUiActions(
    private val appContext: Context
) : UiActions {

    /**
     * Implementation of displaying a simple tpast message.
     */
    override fun showToast(message: String) =
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()

    /**
     * Implementation of getting string resource.
     */
    override fun getString(res: Int): String = appContext.getString(res)
}