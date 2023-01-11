package com.cdr.mvvm_prototype.screens.root.tabs.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.cdr.core.navigator.Navigator
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.languages.Language
import com.cdr.mvvm_prototype.model.languages.LanguageRepository
import com.cdr.mvvm_prototype.model.languages.SelectedLanguageListener
import com.cdr.mvvm_prototype.model.users.SelectedUserListener
import com.cdr.mvvm_prototype.model.users.User
import com.cdr.mvvm_prototype.model.users.UserRepository

class DashboardViewModel(
    private val navigator: Navigator,
    private val languageRepository: LanguageRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> = _selectedUser

    private val _selectedLanguage = MutableLiveData<Language>()
    val selectedLanguage: LiveData<Language> = _selectedLanguage

    private val selectedLanguageListener: SelectedLanguageListener =
        { _selectedLanguage.value = it }

    private val selectedUserListener: SelectedUserListener =
        {_selectedUser.value = it}

    init {
        userRepository.addListenerSelected(selectedUserListener)
        languageRepository.addSelectedLanguageListener(selectedLanguageListener)
    }

    fun launchUsersListScreen(navController: NavController) {
        val directions = DashboardFragmentDirections.actionDashboardFragmentToUsersListFragment()
        navigator.launchByNavController(navController, directions)
    }

    fun launchLanguageListScreen(navController: NavController) {
        val directions = DashboardFragmentDirections.actionDashboardFragmentToLanguageListFragment()
        navigator.launchByNavController(navController, directions)
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.removeListenerSelected(selectedUserListener)
        languageRepository.removeSelectedLanguageListener(selectedLanguageListener)
    }
}