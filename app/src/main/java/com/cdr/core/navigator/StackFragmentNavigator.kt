package com.cdr.core.navigator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cdr.core.views.*
import com.cdr.core.views.BaseScreen.Companion.ARG_SCREEN
import com.google.android.material.appbar.MaterialToolbar

class StackFragmentNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int,
    private val toolbar: MaterialToolbar?,
    private val defaultTitle: String,
    private val animations: Animations?,
    private val initialScreenCreator: () -> BaseScreen
) : Navigator {

    private var result: Any? = null

    /**
     * Launch a new screen at the top of back stack.
     */
    override fun launch(screen: BaseScreen) = launchFragment(screen)

    /**
     * Launch a new screen without adding it at the top of back stack.
     */
    override fun launch(screen: BaseScreen, addToBackStack: Boolean) =
        launchFragment(screen, addToBackStack)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    override fun goBack(result: Any?) {
        if (result != null) this.result = result
        activity.onBackPressed()
    }

    /**
     * A method that duplicates the Activity lifecycle-method and launches a new screen if it has not been launched.
     */
    @SuppressLint("UseSupportActionBar")
    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            launchFragment(screen = initialScreenCreator(), addToBackStack = false)
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
        if (toolbar != null) activity.setSupportActionBar(toolbar)
    }

    /**
     * A method that duplicates the Activity lifecycle-method
     */
    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    /**
     * New screen launch method.
     */
    private fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true) {
        // as screen classes are inside fragments -> we can create fragment directly from screen
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // set screen object as fragment's argument
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        if (animations != null) transaction.setCustomAnimations(
            animations.enterAnim,
            animations.exitAnim,
            animations.popEnterAnim,
            animations.popExitAnim,
        )

        transaction.replace(containerId, fragment).commit()
    }

    /**
     * Method to update items on activity (e.g. toolbar).
     */
    fun notifyScreenUpdates() {
        val f = activity.supportFragmentManager.findFragmentById(containerId)

        // if toolbar is exist on activity
        if (toolbar != null) {
            // more than 1 screen -> show back button in the toolbar
            if (activity.supportFragmentManager.backStackEntryCount > 0) activity.supportActionBar?.setDisplayHomeAsUpEnabled(
                true
            )
            else activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

            // fragment has custom screen title -> display it
            if (f is HasCustomTitle) toolbar.title = f.getScreenTitle()
            else toolbar.title = defaultTitle

            // fragment has custom action -> display it
            if (f is HasCustomAction) createCustomToolbarAction(f.getCustomAction())
            else toolbar.menu.clear()

            // fragment has several custom actions -> display it
            if (f is HasSeveralCustomActions) createSeveralCustomToolbarActions(f.getSeveralCustomActions())
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
     * Method of updating UI with several custom actions. If custom implements [HasSeveralCustomActions].
     */
    private fun createSeveralCustomToolbarActions(actions: SeveralCustomActions) {
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
     * Method of getting internet connection status.
     */
    fun checkInternetConnection(): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var network: Network? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) network =
            connectivityManager.activeNetwork ?: return false
        else Log.d("INTERNET_CONNECTION", "Error with API.")

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    /**
     * Method of "sending" result-data to the screen's view-model.
     */
    private fun publishResults(fragment: Fragment) {
        val result = this.result ?: return
        if (fragment is BaseFragment) fragment.viewModel.onResult(result)
    }

    /**
     * Controlling object lifecycle callbacks of fragment.
     */
    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
            publishResults(f)
        }
    }

    /**
     * Class-support for animation-appearing of new screen.
     */
    class Animations(
        @AnimRes val enterAnim: Int,
        @AnimRes val exitAnim: Int,
        @AnimRes val popEnterAnim: Int,
        @AnimRes val popExitAnim: Int,
    )
}

