package com.cdr.mvvm_prototype

import android.app.Application
import com.cdr.core.BaseApplication
import com.cdr.mvvm_prototype.model.languages.LanguageRepository
import com.cdr.mvvm_prototype.model.profile.ProfileRepository
import com.cdr.mvvm_prototype.model.users.UserRepository

class App : Application(), BaseApplication {

    override val models: List<Any> = listOf(UserRepository(), LanguageRepository(), ProfileRepository())
}