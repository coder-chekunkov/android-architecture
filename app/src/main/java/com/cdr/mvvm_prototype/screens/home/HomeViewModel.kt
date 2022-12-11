package com.cdr.mvvm_prototype.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.model.SelectedUserListener
import com.cdr.mvvm_prototype.model.User
import com.cdr.mvvm_prototype.model.UsersRepository
import com.cdr.mvvm_prototype.screens.users.UsersListFragment

class HomeViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> = _selectedUser
    var isNewSelected: Boolean = false

    private val selectedUserListener: SelectedUserListener = {
        isNewSelected = true
        _selectedUser.value = it
    }

    init {
        usersRepository.addListenerSelected(selectedUserListener)
    }

    fun onChangeUserPressed() = navigator.launch(UsersListFragment.Screen())
    fun onUserUpdated(view: View, message: String) {
        uiActions.showSnackbar(view, message, R.color.dark_green)
        isNewSelected = false
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.removeListenerSelected(selectedUserListener)
    }
}