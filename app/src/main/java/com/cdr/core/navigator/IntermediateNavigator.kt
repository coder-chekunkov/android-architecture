package com.cdr.core.navigator

import com.cdr.core.utils.ResourceActions
import com.cdr.core.views.BaseScreen

/**
 * Mediator that holds nav-actions in the queue if real navigator is not active.
 */
class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    /**
     * Implementation of launching a new screen at the top of the back stack.
     */
    override fun launch(screen: BaseScreen) = targetNavigator {
        it.launch(screen)
    }

    /**
     * Implementation of launching a new screen without adding it at the top of back stack.
     */
    override fun launch(screen: BaseScreen, addToBackStack: Boolean) = targetNavigator {
        it.launch(screen, addToBackStack)
    }

    /**
     * Implementation of Go back to the previous screen and optionally send some results.
     */
    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    /**
     * Set navigator in target
     */
    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    /**
     * Clean all navigation
     */
    fun cleared() {
        targetNavigator.clear()
    }
}