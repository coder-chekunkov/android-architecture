package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.create_user

import android.view.View
import androidx.navigation.NavController
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.users.User
import com.cdr.mvvm_prototype.model.users.UserRepository
import com.cdr.version2.R

class CreateUserViewModel(
    private val uiActions: UiActions,
    private val navigator: Navigator,
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun createNewUser(username: String, companyName: String, isMakeSelect: Boolean,
                      view: View,navController: NavController) {
        val user = User(
            id = (userRepository.getAllUsers().size + 1).toLong(),
            name = username,
            company = companyName,
            photo = "empty",
            isSelected = false
        )

        uiActions.showSnackbar(
            view = view,
            message = uiActions.getString(R.string.message_user_created_snackbar),
            backgroundColor = R.color.green,
            mainColor = R.color.black
        )
        userRepository.createNewUser(user, isMakeSelect)
        navigator.popBackStackByNavController(navController)
    }

    fun showErrorSnackbar(view: View) = uiActions.showSnackbar(
        view = view,
        message = uiActions.getString(R.string.message_error_empty_snackbar),
        backgroundColor = R.color.red,
        mainColor = R.color.white
    )

}