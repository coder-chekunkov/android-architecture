package com.cdr.mvvm_prototype

import android.app.Application
import com.cdr.core.BaseApplication
import com.cdr.mvvm_prototype.model.LanguageRepository
import com.cdr.mvvm_prototype.model.UsersRepository

class App : Application(), BaseApplication {
    override val models: List<Any> = listOf(UsersRepository(), LanguageRepository())
}