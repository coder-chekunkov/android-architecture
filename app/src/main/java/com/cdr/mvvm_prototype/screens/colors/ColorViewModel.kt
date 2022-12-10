package com.cdr.mvvm_prototype.screens.colors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.R
import kotlin.random.Random

class ColorViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    screen: ColorFragment.Screen
) : BaseViewModel() {

    private val _color = MutableLiveData<State>()
    val color: LiveData<State> = _color

    init {
        _color.value = State(
            idColor = screen.currentValue,
            nameColor = createNameColor(screen.currentValue)
        )
    }

    fun onNewColor() {
        val idColor = createIdColor()
        val nameColor = createNameColor(idColor)
        _color.value = _color.value?.copy(idColor = idColor, nameColor = nameColor)
        uiActions.showToast("New color --->>> $nameColor")
    }

    fun onApply() = navigator.goBack(_color.value?.idColor)

    private fun createIdColor(): Int = when (Random.nextInt(1, 7)) {
        1 -> R.color.grey
        2 -> R.color.red
        3 -> R.color.green
        4 -> R.color.yellow
        5 -> R.color.blue
        6 -> R.color.purple
        else -> R.color.white
    }

    private fun createNameColor(idColor: Int): String = when (idColor) {
        R.color.grey -> "grey"
        R.color.red -> "red"
        R.color.green -> "green"
        R.color.yellow -> "yellow"
        R.color.blue -> "blue"
        R.color.purple -> "purple"
        else -> "error"
    }

    data class State(val idColor: Int, val nameColor: String)
}