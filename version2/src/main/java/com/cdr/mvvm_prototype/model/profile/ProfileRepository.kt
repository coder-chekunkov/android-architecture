package com.cdr.mvvm_prototype.model.profile

import com.cdr.core.model.Repository

typealias ProfileListener = (profile: Profile) -> Unit

class ProfileRepository : Repository {

    private var profile: Profile
    private val profileListeners = mutableListOf<ProfileListener>()

    init {
        profile = Profile(name = "Alexandr Chekunkov", company = "Google Inc.")
    }

    fun editProfile(editedProfile: Profile) {
        profile = editedProfile
        notifyProfileChanges()
    }

    fun addListener(listener: ProfileListener) {
        profileListeners.add(listener)
        listener.invoke(profile)
    }

    fun removeListener(listener: ProfileListener) {
        profileListeners.remove(listener)
        listener.invoke(profile)
    }

    private fun notifyProfileChanges() = profileListeners.forEach { it.invoke(profile) }
}