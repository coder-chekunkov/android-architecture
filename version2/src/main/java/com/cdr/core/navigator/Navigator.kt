package com.cdr.core.navigator

import android.os.Bundle
import androidx.navigation.NavDirections

/**
 * Navigation for your application.
 */
interface Navigator {

    /**
     * Launch a new destination using ID of destination from Navigation Graph. - without arguments.
     */
    fun launchDestination(destination: Int)

    /**
     * Launch a new destination using ID of destination from Navigation Graph. - with arguments.
     */
    fun launchDestination(destination: Int, args: Bundle?)

    /**
     * Launch a new action.
     */
    fun launchAction(action: Int)

    /**
     * Launch a new direction.
     */
    fun launchDirection(direction: NavDirections)
    
    /**
     * Launch a root destination.
     */
    fun launchRootDestination()

    /**
     * Launch a previous destination.
     */
    fun launchPreviousDestination()
}