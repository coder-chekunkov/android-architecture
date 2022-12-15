package com.cdr.core.views

import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes

/**
 * If your fragment wants to show custom action in the toolbar, implement this interface and override [getCustomAction] method.
 */
interface HasCustomAction {
    /**
     * @return a custom action specification, see [CustomAction] class for details.
     */
    fun getCustomAction(): CustomAction
}

class CustomAction(
    @DrawableRes val iconRes: Int, val textAction: String, val onCustomAction: Runnable
)

/**
 * If your fragment wants to show several custom actions in toolbar, implement this interface and override [getSeveralCustomActions] method.
 */
interface HasSeveralCustomActions {
    /**
     * @return several custom actions, see [SeveralCustomActions] and [Action] classes for details.
     */
    fun getSeveralCustomActions(): SeveralCustomActions
}

class SeveralCustomActions(@MenuRes val menuRes: Int, val onSeveralCustomActions: List<Action>)
class Action(val id: Int, val action: Runnable)