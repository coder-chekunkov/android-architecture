package com.cdr.mvvm_prototype

import android.content.Context
import com.cdr.mvvm_prototype.model.languages.LanguageRepository
import com.cdr.mvvm_prototype.model.profile.ProfileRepository
import com.cdr.mvvm_prototype.model.users.UserRepository

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    val usersRepository: UserRepository by lazy { UserRepository() }
    val languageRepository: LanguageRepository by lazy { LanguageRepository() }
    val profileRepository: ProfileRepository by lazy { ProfileRepository() }

}