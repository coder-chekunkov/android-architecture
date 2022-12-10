package com.cdr.mvvm_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cdr.core.ActivityScopeViewModel
import com.cdr.core.navigator.IntermediateNavigator
import com.cdr.core.navigator.StackFragmentNavigator
import com.cdr.core.uiactions.AndroidUiActions
import com.cdr.core.utils.viewModelCreator
import com.cdr.core.views.FragmentHolder
import com.cdr.mvvm_prototype.screens.home.HomeFragment

class MainActivity : AppCompatActivity(), FragmentHolder {

    private lateinit var navigator: StackFragmentNavigator
    private val viewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            navigator = IntermediateNavigator(),
            uiActions = AndroidUiActions(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = StackFragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContainer,
            defaultTitle = "My MVVM",
            animations = null,
            initialScreenCreator = { HomeFragment.Screen() }
        )

        navigator.onCreate(savedInstanceState)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun notifyScreenUpdates() = navigator.notifyScreenUpdates()
    override fun getActivityScopeViewModel(): ActivityScopeViewModel = viewModel
}