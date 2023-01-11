package com.cdr.mvvm_prototype.screens.root.tabs.info

import com.cdr.core.views.BaseViewModel
import com.cdr.version2.BuildConfig
import kotlin.properties.Delegates

class InformationViewModel : BaseViewModel() {

    var applicationName by Delegates.notNull<String>()
    var versionName by Delegates.notNull<String>()
    var versionCode by Delegates.notNull<String>()

    init {
        applicationName = "MVVM - Navigation"
        versionName = BuildConfig.VERSION_NAME
        versionCode = BuildConfig.VERSION_CODE.toString()
    }
}