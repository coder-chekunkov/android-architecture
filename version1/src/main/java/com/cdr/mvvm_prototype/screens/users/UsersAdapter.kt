package com.cdr.mvvm_prototype.screens.users

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.ItemUserBinding
import com.cdr.mvvm_prototype.model.User
import kotlin.properties.Delegates

class UsersDiffUtil(
    private val oldList: List<User>, private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]

        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]

        return oldUser == newUser
    }
}

interface UserActionListener {
    fun onInfo(user: User)
    fun onSelect(user: User)
    fun onRemove(user: User)
    fun onMove(user: User, moveBy: Int)
}

class UsersAdapter(private val userActionListener: UserActionListener) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var data: List<User> = emptyList()
        set(newValue) {
            val usersDiffUtil = UsersDiffUtil(field, newValue)
            val diffUtilResult = DiffUtil.calculateDiff(usersDiffUtil)
            field = newValue
            diffUtilResult.dispatchUpdatesTo(this@UsersAdapter)
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.selectUserButton.setOnClickListener(this)
        binding.more.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user: User = data[position]
        val context: Context = holder.itemView.context

        with(holder.binding) {
            holder.itemView.tag = user
            selectUserButton.tag = user
            more.tag = user

            var color: Int by Delegates.notNull()
            if (user.isSelected) {
                color = R.color.dark_green
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
                selectUserButton.isClickable = false
            } else {
                color = R.color.dark_grey
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                selectUserButton.isClickable = true
            }

            nameTextView.text = user.name
            companyTextView.text = user.company
            Glide.with(context).load(user.photo).circleCrop().error(R.drawable.ic_user)
                .placeholder(R.drawable.ic_user).into(imageView)
            selectUserButton.setColorFilter(
                ContextCompat.getColor(context, color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    override fun onClick(view: View) {
        val user: User = view.tag as User
        when (view.id) {
            R.id.more -> showPopupMenu(view)
            R.id.selectUserButton -> userActionListener.onSelect(user)
            else -> userActionListener.onInfo(user)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val user: User = view.tag as User

        val position = data.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Up")
            .apply { isEnabled = position > 0 }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Down")
            .apply { isEnabled = position < data.size - 1 }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")
            .apply { isEnabled = !user.isSelected }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> userActionListener.onMove(user, -1)
                ID_MOVE_DOWN -> userActionListener.onMove(user, 1)
                ID_REMOVE -> userActionListener.onRemove(user)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }

    class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}