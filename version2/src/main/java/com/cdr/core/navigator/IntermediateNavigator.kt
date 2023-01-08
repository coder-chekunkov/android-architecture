package com.cdr.core.navigator

import android.os.Bundle
import androidx.navigation.NavDirections
import com.cdr.core.utils.ResourceActions

/**
 * Mediator that holds nav-actions in the queue if real navigator is not active.
 */
class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    /**
     * Implementation of launching a new destination using ID of destination from Navigation Graph. - without arguments.
     */
    override fun launchDestination(destination: Int) = targetNavigator {
        it.launchDestination(destination)
    }

    /**
     * Implementation of launching a new destination using ID of destination from Navigation Graph. - with arguments.
     */
    override fun launchDestination(destination: Int, args: Bundle?) = targetNavigator {
        it.launchDestination(destination, args)
    }

    /**
     * Implementation of launching action.
     */
    override fun launchAction(action: Int) = targetNavigator {
        it.launchAction(action)
    }

    /**
     * Implementation of launching direction.
     */
    override fun launchDirection(direction: NavDirections) = targetNavigator {
        it.launchDirection(direction)
    }

    /**
     * Implementation of launching a root fragment.
     */
    override fun launchRootDestination() = targetNavigator {
        it.launchRootDestination()
    }

    /**
     * Implementation of launching previous destination.
     */
    override fun launchPreviousDestination() = targetNavigator {
        it.launchPreviousDestination()
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