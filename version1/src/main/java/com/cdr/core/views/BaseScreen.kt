package com.cdr.core.views

import java.io.Serializable


/**
 * Base class for defining screen arguments.
 * NOTE! All fields inside the screen should be serializable.
 */
interface BaseScreen : Serializable {

    companion object {
        /**
         * A constant variable used to pass the screen argument for the fragment
         */
        const val ARG_SCREEN = "ARG_SCREEN"
    }
}