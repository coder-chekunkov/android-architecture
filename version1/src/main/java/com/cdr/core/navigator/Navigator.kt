package com.cdr.core.navigator

import com.cdr.core.views.BaseScreen

/**
 * Navigation for your application
 */
interface Navigator {
    /**
     * Launch a new screen at the top of back stack.
     */
    fun launch(screen: BaseScreen)

    /**
     * Launch a new screen  without adding it at the top of back stack.
     */
    fun launch(screen: BaseScreen, addToBackStack: Boolean)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)
}