package com.cdr.mvvm_prototype.model.languages

data class Language(val id: Long, val name: String, val res: String, val isSelected: Boolean) {
    override fun toString(): String = "Language:{id:$id; name: $name}"
}
