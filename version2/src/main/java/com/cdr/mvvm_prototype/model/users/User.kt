package com.cdr.mvvm_prototype.model.users

data class User(
    val id: Long,
    val name: String,
    val company: String,
    val photo: String,
    val isSelected: Boolean
) {
    override fun toString(): String = "User:{id:$id; name: $name}"
}
