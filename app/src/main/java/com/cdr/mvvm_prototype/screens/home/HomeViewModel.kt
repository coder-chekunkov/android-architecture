package com.cdr.mvvm_prototype.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.screens.colors.ColorFragment
import com.cdr.mvvm_prototype.screens.edit.EditFragment

class HomeViewModel(
    private val navigator: Navigator,
    uiActions: UiActions
) : BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    init {
        _state.value = State(text = uiActions.getString(R.string.base_text), color = R.color.grey)
    }

    fun onEditPressed() = navigator.launch(EditFragment.Screen(_state.value?.text!!))
    fun onChangeColorPressed() = navigator.launch(ColorFragment.Screen(_state.value?.color!!))

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is String) _state.value = _state.value?.copy(text = result)
        if (result is Int) _state.value = _state.value?.copy(color = result)
    }

    data class State(val text: String, val color: Int)
}