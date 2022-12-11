package com.cdr.mvvm_prototype.screens.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.User
import com.cdr.mvvm_prototype.model.ListUserListener
import com.cdr.mvvm_prototype.model.UsersRepository

class UsersListViewModel(
    private val uiActions: UiActions,
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _screenTitle = MutableLiveData<String>()
    val screenTitle: LiveData<String> = _screenTitle

    private val listListener: ListUserListener = { _users.value = it }

    init {
        usersRepository.addListenerList(listListener)
        _screenTitle.value = usersRepository.getSelectedUser().name
    }

    val userActionListener = object : UserActionListener {
        override fun onInfo(user: User) = uiActions.showToast(user.toString())
        override fun onSelect(user: User) {
            usersRepository.selectUser(user)
            _screenTitle.value = user.name
        }

        override fun onRemove(user: User) = usersRepository.removeUser(user)
        override fun onMove(user: User, moveBy: Int) = usersRepository.moveUser(user, moveBy)
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.removeListenerList(listListener)
    }
}