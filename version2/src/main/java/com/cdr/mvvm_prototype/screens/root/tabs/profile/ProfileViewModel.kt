package com.cdr.mvvm_prototype.screens.root.tabs.profile

import androidx.fragment.app.Fragment
import com.cdr.core.navigator.Navigator
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.profile.Profile
import com.cdr.mvvm_prototype.model.profile.ProfileListener
import com.cdr.mvvm_prototype.model.profile.ProfileRepository
import com.cdr.mvvm_prototype.screens.root.RootFragmentDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val navigator: Navigator,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    private val profileListener: ProfileListener = { _profile.value = it }

    init {
        profileRepository.addListener(profileListener)
    }

    fun launchEditProfileScreen(fragment: Fragment) {
        val directions = RootFragmentDirections.actionRootFragmentToEditProfileFragment()
        navigator.launchByTopNavController(fragment, directions)
    }

    override fun onCleared() {
        super.onCleared()
        profileRepository.removeListener(profileListener)
    }
}

