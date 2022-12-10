package com.cdr.mvvm_prototype

import android.app.Application
import com.cdr.core.BaseApplication

class App : Application(), BaseApplication {
    override val models: List<Any> = emptyList()
}