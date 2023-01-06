package com.cdr.mvvm_prototype.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.*
import com.cdr.mvvm_prototype.screens.language.LanguagesListFragment
import com.cdr.mvvm_prototype.screens.users.UsersListFragment

class HomeViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val usersRepository: UsersRepository,
    private val languageRepository: LanguageRepository
) : BaseViewModel() {

    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> = _selectedUser
    var isNewUser: Boolean = false

    private val _selectedLanguage = MutableLiveData<Language>()
    val selectedLanguage: LiveData<Language> = _selectedLanguage
    var isNewLanguage: Boolean = false

    private val selectedUserListener: SelectedUserListener = {
        isNewUser = true
        _selectedUser.value = it
    }

    private val selectedLanguageListener: SelectedLanguageListener = {
        isNewLanguage = true
        _selectedLanguage.value = it
    }

    init {
        usersRepository.addListenerSelected(selectedUserListener)
        languageRepository.addSelectedLanguageListener(selectedLanguageListener)
    }

    fun onChangeUserPressed() = navigator.launch(UsersListFragment.Screen())
    fun onChangeLanguagePressed() = navigator.launch(LanguagesListFragment.Screen())
    fun onShowSnackbar(view: View, message: Int, color: Int) {
        uiActions.showSnackbar(view, uiActions.getString(message), color)
        isNewUser = false
        isNewLanguage = false
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.removeListenerSelected(selectedUserListener)
        languageRepository.removeSelectedLanguageListener(selectedLanguageListener)
    }
}