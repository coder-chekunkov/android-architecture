package com.cdr.core.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Navigation for your application.
 */
interface Navigator {

    /**
     * Launch a new direction using a Navigation Controller.
     */
    fun launchByNavController(navController: NavController, direction: NavDirections)

    /**
     * Launching a new direction using a Top Navigation Controller.
     */
    fun launchByTopNavController(fragment: Fragment, direction: NavDirections)

    /**
     * PopBackStack using a Navigation Controller.
     */
    fun popBackStackByNavController(navController: NavController)

    /**
     * PopBackStack using a Top Navigation Controller.
     */
    fun popBackStackByTopNavController(fragment: Fragment)
}