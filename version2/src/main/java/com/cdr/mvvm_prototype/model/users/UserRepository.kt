package com.cdr.mvvm_prototype.model.users

import com.cdr.core.model.Repository
import com.github.javafaker.Faker
import java.util.*
import kotlin.collections.ArrayList

typealias ListUserListener = (users: List<User>) -> Unit
typealias SelectedUserListener = (user: User) -> Unit

class UserRepository : Repository {

    private var users = mutableListOf<User>()
    private val listListener = mutableListOf<ListUserListener>()

    private lateinit var selectedUser: User
    private val selectedUserListener = mutableListOf<SelectedUserListener>()

    private val faker = Faker.instance()

    init {
        users = (1..50).map {
            User(
                id = it.toLong(),
                name = faker.name().fullName(),
                company = faker.company().name(),
                photo = IMAGES[faker.random().nextInt(0, IMAGES.size - 1)],
                isSelected = false
            )
        }.toMutableList()
        createRandomSelectedUser()
    }

    private fun createRandomSelectedUser() {
        val randomId = faker.random().nextInt(1, 50).toLong()
        val index = users.indexOfFirst { it.id == randomId }

        if (index == -1) throw IllegalArgumentException("Error with random user. Unknown ID.")
        users[index] = users[index].copy(isSelected = true)
        selectedUser = getSelectedUser()
    }

    fun getSelectedUser(): User = users.find { it.isSelected }!!

    fun getAllUsers(): List<User> = users

    fun createNewUser(user: User, isMakeSelected: Boolean) {
        users = ArrayList(users)
        users.add(user)
        notifyChangesList()

        if (isMakeSelected) selectUser(user)
    }

    fun selectUser(user: User) {
        val index = findIndex(user)
        if (index == -1) return

        users = ArrayList(users)
        for (id in 0 until users.size)
            users[id] = users[id].copy(isSelected = false)
        users[index] = users[index].copy(isSelected = true)
        notifyChangesList()

        selectedUser = getSelectedUser()
        notifyChangesSelected()
    }

    fun removeUser(user: User) {
        val index = findIndex(user)
        if (index == -1) return

        users = ArrayList(users)
        users.removeAt(index)
        notifyChangesList()
    }

    fun moveUser(user: User, moveBy: Int) {
        val oldIndex = findIndex(user)
        if (oldIndex == -1) return

        val newIndex = oldIndex + moveBy
        users = ArrayList(users)
        Collections.swap(users, oldIndex, newIndex)
        notifyChangesList()
    }

    fun addListenerList(listener: ListUserListener) {
        listListener.add(listener)
        listener.invoke(users)
    }

    fun removeListenerList(listener: ListUserListener) {
        listListener.remove(listener)
        listener.invoke(users)
    }

    fun addListenerSelected(listener: SelectedUserListener) {
        selectedUserListener.add(listener)
        listener.invoke(selectedUser)
    }

    fun removeListenerSelected(listener: SelectedUserListener) {
        selectedUserListener.remove(listener)
        listener.invoke(selectedUser)
    }

    private fun notifyChangesList() = listListener.forEach { it.invoke(users) }
    private fun notifyChangesSelected() = selectedUserListener.forEach { it.invoke(selectedUser) }
    private fun findIndex(user: User): Int = users.indexOfFirst { it.id == user.id }

    companion object {
        private var IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            ""
        )
    }
}