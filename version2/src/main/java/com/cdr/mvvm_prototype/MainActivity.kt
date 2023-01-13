package com.cdr.mvvm_prototype

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cdr.core.ActivityScopeViewModel
import com.cdr.core.navigator.IntermediateNavigator
import com.cdr.core.navigator.NavigatorExecutor
import com.cdr.core.uiactions.AndroidUiActions
import com.cdr.core.utils.internet.IsInternetConnection
import com.cdr.core.utils.internet.isInternetAvailable
import com.cdr.core.utils.viewModelCreator
import com.cdr.core.views.FragmentHolder
import com.cdr.version2.R
import com.cdr.version2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentHolder, IsInternetConnection {

    private lateinit var navigator: NavigatorExecutor
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            navigator = IntermediateNavigator(),
            uiActions = AndroidUiActions(
                activity = this,
                appContext = applicationContext
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val navController = createNavigationController()
        createStartDestination(navController)

        navigator = NavigatorExecutor(
            activity = this,
            navController = navController,
            fragmentContainer = R.id.fragmentContainer,
            toolbar = binding.toolbar,
            defaultTitle = getString(R.string.app_name),
            topLevelDestinations = createListOfTopLevelDestination()
        )

        navigator.onCreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        viewModel.navigator.setTarget(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean =
        navigator.onSupportNavigateUp() || super.onSupportNavigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        notifyScreenUpdates()
        return true
    }

    override fun notifyScreenUpdates() = navigator.notifyScreenUpdates()
    override fun getActivityScopeViewModel(): ActivityScopeViewModel = viewModel
    override fun checkInternetConnection(): Boolean = isInternetAvailable()

    private fun createNavigationController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun createStartDestination(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        graph.setStartDestination(if (checkInternetConnection()) R.id.rootFragment else R.id.internetConnectionFragment)
        navController.graph = graph
    }

    private fun createListOfTopLevelDestination(): List<Int> =
        listOf(R.id.rootFragment, R.id.internetConnectionFragment)
}