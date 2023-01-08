package com.cdr.core.navigator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import com.cdr.core.views.*
import com.google.android.material.appbar.MaterialToolbar

class NavigatorExecutor(
    private val activity: AppCompatActivity,
    private val navController: NavController,
    private val toolbar: MaterialToolbar?,
    private val defaultTitle: String,
    private val topLevelDestinations: List<Int>,
    private val animations: CustomAnimations?
    ) : Navigator {

    /**
     * Launch new destination. - without arguments.
     */
    override fun launchDestination(destination: Int) = launchScreen(destination)

    /**
     * Launch new destination. - with arguments.
     */
    override fun launchDestination(destination: Int, args: Bundle?) =
        launchScreen(destination, args)

    /**
     * Launching a new action.
     */
    override fun launchAction(action: Int) = launchScreen(action)

    /**
     * Launching new direction.
     */
    override fun launchDirection(direction: NavDirections) = launchScreen(direction)

    /**
     * Launch a root destination (declared when creating the navigator).
     */
    override fun launchRootDestination() { navController.popBackStack(topLevelDestinations.first(), false) }

    /**
     * Launch a previous destination.
     */
    override fun launchPreviousDestination() { navController.popBackStack() }

    /**
     * Implementation of launching new destination.
     */
    private fun launchScreen(destination: Int, args: Bundle? = null) {
        // if animations is exist -> launch new screen with animation
        // else -> launch new screen without animations
        if (animations != null) navController.navigate(destination, args, navOptions {
            anim {
                enter = animations.enterAnim
                exit = animations.exitAnim
                popEnter = animations.popEnterAnim
                popExit = animations.popExitAnim
            }
        }) else navController.navigate(destination, args)
    }

    /**
     * Implementation of launching new action.
     */
    private fun launchScreen(action: Int) = navController.navigate(action)

    /**
     * Implementation of launching new direction.
     */
    private fun launchScreen(direction: NavDirections) {
        // if animations is exist -> launch new screen with animation
        // else -> launch new screen without animations
        if (animations != null) navController.navigate(direction, navOptions {
            anim {
                enter = animations.enterAnim
                exit = animations.exitAnim
                popEnter = animations.popEnterAnim
                popExit = animations.popExitAnim
            }
        }) else navController.navigate(direction)
    }

    /**
     * A method that duplicates the Activity lifecycle-method.
     */
    @SuppressLint("UseSupportActionBar")
    fun onCreate() {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
        if (toolbar != null) activity.setSupportActionBar(toolbar)
    }

    /**
     * A method that duplicates the Activity lifecycle-method.
     */
    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    /**
     * A method that duplicates the Activity method.
     */
    fun onSupportNavigateUp() = navController.navigateUp()

    /**
     * Method to update items on activity (e.g. toolbar).
     */
    fun notifyScreenUpdates() {
        val f =
            activity.supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.first()

        // if toolbar is exist on activity
        if (toolbar != null) {
            // more than 1 screen -> show back button in the toolbar
            if (topLevelDestinations.contains(navController.currentDestination?.id))
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            else activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // fragment has custom screen title -> display it
            if (f is HasCustomTitle) toolbar.title = f.getScreenTitle()
            else toolbar.title = defaultTitle

            // fragment has custom action -> display it
            if (f is HasCustomAction) createCustomToolbarAction(f.getCustomAction())
            else toolbar.menu.clear()

            // fragment has several custom actions -> display it
            if (f is HasCustomMultipleAction) createCustomMultipleToolbarActions(f.getMultipleCustomAction())
            else activity.invalidateOptionsMenu()
        }
    }

    /**
     * Method of updating UI with custom action. If custom implements [HasCustomAction].
     */
    private fun createCustomToolbarAction(action: CustomAction) {
        toolbar!!.menu.clear()

        val iconDrawable =
            DrawableCompat.wrap(ContextCompat.getDrawable(activity, action.iconRes)!!)
        iconDrawable.setTint(Color.WHITE)

        val menuItem = toolbar.menu.add(action.textAction)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }

    /**
     * Method of updating UI with several custom actions. If custom implements [HasCustomMultipleAction].
     */
    private fun createCustomMultipleToolbarActions(actions: MultipleCustomAction) {
        activity.invalidateOptionsMenu()
        toolbar!!.menu.clear()

        toolbar.inflateMenu(actions.menuRes)
        toolbar.menu.forEach { item ->
            item.setOnMenuItemClickListener {
                val itemId = it.itemId
                val actionId = actions.onSeveralCustomActions.indexOfFirst { a -> a.id == itemId }

                if (actionId == -1) return@setOnMenuItemClickListener false
                actions.onSeveralCustomActions[actionId].action.run()
                return@setOnMenuItemClickListener true
            }
        }
    }

    /**
     * Controlling object lifecycle callbacks of fragment.
     */
    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
        }
    }

    /**
     * Class-support for animation-appearing of new screen.
     */
    class CustomAnimations(
        @AnimRes val enterAnim: Int,
        @AnimRes val exitAnim: Int,
        @AnimRes val popEnterAnim: Int,
        @AnimRes val popExitAnim: Int,
    )
}