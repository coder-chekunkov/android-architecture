package com.cdr.mvvm_prototype.screens.internet

import android.view.View
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.screens.home.HomeFragment

class InternetConnectionViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions
) : BaseViewModel() {

    var isFirstMessageOfError: Boolean = true

    fun showFirstMessageOfError(view: View) {
        isFirstMessageOfError = false
        showSnackbarWithErrorConnection(view)
    }

    fun showSnackbarWithErrorConnection(view: View) = uiActions.showSnackbar(
        view = view,
        message = uiActions.getString(R.string.error_message_snackbar),
        backgroundColor = R.color.red
    )

    fun launchAllCitiesScreen() = navigator.launch(HomeFragment.Screen(), false)
}