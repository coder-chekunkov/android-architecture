package com.cdr.core

/**
 * Implement this interface in your Application class.
 */
interface BaseApplication {
    /**
     * The list of repositories that can be added to the fragment view-model constructors.
     */
    val models: List<Any>
}