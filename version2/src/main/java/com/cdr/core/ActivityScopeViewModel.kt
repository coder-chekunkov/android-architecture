package com.cdr.core

import androidx.lifecycle.ViewModel
import com.cdr.core.navigator.IntermediateNavigator
import com.cdr.core.navigator.Navigator
import com.cdr.core.uiactions.UiActions

/**
 * Implementation of [Navigator] and [UiActions].
 * It is based on activity view-model because instances of [Navigator] and [UiActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor).
 */
class ActivityScopeViewModel(
    val navigator: IntermediateNavigator,
    val uiActions: UiActions,
    val dependencies: List<Any>
) : ViewModel(), Navigator by navigator, UiActions by uiActions {

    override fun onCleared() {
        super.onCleared()
        navigator.cleared()
    }
}