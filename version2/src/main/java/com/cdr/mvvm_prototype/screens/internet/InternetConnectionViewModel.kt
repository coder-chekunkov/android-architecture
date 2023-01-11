package com.cdr.mvvm_prototype.screens.internet

import android.view.View
import androidx.fragment.app.Fragment
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.version2.R

class InternetConnectionViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions
) : BaseViewModel() {

    fun launchRootScreen(fragment: Fragment) {
        val direction =
            InternetConnectionFragmentDirections.actionInternetConnectionFragmentToRootFragment()
        navigator.launchByTopNavController(fragment, direction)
    }

    fun showErrorSnackbar(view: View) {
        uiActions.showSnackbar(
            view = view,
            message = uiActions.getString(R.string.error_message_snackbar),
            backgroundColor = R.color.red,
            mainColor = R.color.white
        )
    }
}