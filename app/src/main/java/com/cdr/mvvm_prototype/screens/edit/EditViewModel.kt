package com.cdr.mvvm_prototype.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel

class EditViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    screen: EditFragment.Screen
) : BaseViewModel() {

    private val _currentText = MutableLiveData<String>()
    val currentText: LiveData<String> = _currentText

    init {
        _currentText.value = screen.currentText
    }

    fun onSavePressed(textFromEditText: String) {
        if (textFromEditText.isBlank()) uiActions.showToast("Text can't be empty!")
        else navigator.goBack(textFromEditText)
    }

    fun onCanselPressed() = navigator.goBack()
}