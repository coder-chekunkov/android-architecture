package com.cdr.core.uiactions

/**
 * Common actions that can be performed in the view-model.
 */
interface UiActions {

    /**
     * Display a simple toast message.
     */
    fun showToast(message: String)

    /**
     * Get string resource.
     */
    fun getString(res: Int): String
}