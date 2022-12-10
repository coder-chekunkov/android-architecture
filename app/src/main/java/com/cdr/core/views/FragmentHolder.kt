package com.cdr.core.views

import com.cdr.core.ActivityScopeViewModel

/**
 * Implement this interface in the activity.
 */
interface FragmentHolder {
    /**
     * Called when activity views should be re-drawn.
     */
    fun notifyScreenUpdates()

    /**
     * Get the current implementations of dependencies from activity VM scope.
     */
    fun getActivityScopeViewModel(): ActivityScopeViewModel
}