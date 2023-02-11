package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.users_list

import androidx.navigation.NavController
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.users.ListUserListener
import com.cdr.mvvm_prototype.model.users.User
import com.cdr.mvvm_prototype.model.users.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersListViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val usersRepository: UserRepository
) : BaseViewModel() {

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _userTitle = MutableStateFlow("")
    val userTitle: StateFlow<String> = _userTitle

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