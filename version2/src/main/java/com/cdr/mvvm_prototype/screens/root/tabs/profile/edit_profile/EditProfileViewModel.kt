package com.cdr.mvvm_prototype.screens.root.tabs.profile.edit_profile

import android.view.View
import androidx.fragment.app.Fragment
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.profile.Profile
import com.cdr.mvvm_prototype.model.profile.ProfileListener
import com.cdr.mvvm_prototype.model.profile.ProfileRepository
import com.cdr.version2.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditProfileViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    private val profileListener: ProfileListener = { _profile.value = it }

    init {
        profileRepository.addListener(profileListener)
    }

    fun cansel(fragment: Fragment) = navigator.popBackStackByTopNavController(fragment)
    fun save(view: View, fragment: Fragment, name: String, company: String) {
        profileRepository.editProfile(Profile(name, company))
        uiActions.showSnackbar(
            view = view,
            message = uiActions.getString(R.string.message_profile_edited_snackbar),
            backgroundColor = R.color.green,
            mainColor = R.color.black
        )
        navigator.popBackStackByTopNavController(fragment)
    }

    override fun onCleared() {
        super.onCleared()
        profileRepository.removeListener(profileListener)
    }
}