package com.cdr.mvvm_prototype.model.profile

data class Profile(val name: String, val company: String) {
    override fun toString(): String = "$name - {$company}"
}