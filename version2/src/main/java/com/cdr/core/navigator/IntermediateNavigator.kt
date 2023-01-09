package com.cdr.core.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.cdr.core.utils.ResourceActions

/**
 * Mediator that holds nav-actions in the queue if real navigator is not active.
 */
class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    /**
     * Implementation of launching a new screen using a Navigation Controller.
     */
    override fun launchByNavController(navController: NavController, direction: NavDirections) = targetNavigator {
        it.launchByNavController(navController, direction)
    }

    /**
     * Implementation of launching a new direction using a Top Navigation Controller.
     */
    override fun launchByTopNavController(fragment: Fragment, direction: NavDirections) = targetNavigator {
        it.launchByTopNavController(fragment, direction)
    }

    /**
     * Set navigator in target.
     */
    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    /**
     * Clean all navigation.
     */
    fun cleared() {
        targetNavigator.clear()
    }
}