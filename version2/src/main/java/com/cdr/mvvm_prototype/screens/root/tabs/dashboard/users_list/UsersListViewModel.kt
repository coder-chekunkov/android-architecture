package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.users_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.users.ListUserListener
import com.cdr.mvvm_prototype.model.users.User
import com.cdr.mvvm_prototype.model.users.UserRepository

class UsersListViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val usersRepository: UserRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _userTitle = MutableLiveData<String>()
    val userTitle: LiveData<String> = _userTitle

    private val listListener: ListUserListener = {
        _users.value = it
        _userTitle.value = usersRepository.getSelectedUser().name
    }

    init {
        usersRepository.addListenerList(listListener)
        _userTitle.value = usersRepository.getSelectedUser().name
    }

    val userActionListener = object : UserActionListener {
        override fun onInfo(user: User) = uiActions.showToast(user.toString())
        override fun onSelect(user: User) {
            usersRepository.selectUser(user)
            _userTitle.value = user.name
        }
        override fun onRemove(user: User) = usersRepository.removeUser(user)
        override fun onMove(user: User, moveBy: Int) = usersRepository.moveUser(user, moveBy)
    }

    fun launchCreateUserScreen(navController: NavController) {
        val direction = UsersListFragmentDirections.actionUsersListFragmentToCreateUserFragment()
        navigator.launchByNavController(navController, direction)
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.removeListenerList(listListener)
    }
}