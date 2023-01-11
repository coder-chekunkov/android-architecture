package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.users_list

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdr.core.views.*
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentUsersListBinding

class UsersListFragment : BaseFragment(R.layout.fragment_users_list), HasCustomTitle,
    HasCustomAction {

    override val viewModel: UsersListViewModel by screenViewModel()
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersListBinding.bind(view)
        navController = findNavController()

        val adapter = UsersAdapter(viewModel.userActionListener)
        viewModel.users.observe(viewLifecycleOwner) { adapter.data = it }
        viewModel.userTitle.observe(viewLifecycleOwner) { notifyScreenUpdates() }

        with(binding) {
            usersListRecyclerView.adapter = adapter
            usersListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun getScreenTitle(): String = viewModel.userTitle.value.toString()
    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_create_user,
        textAction = getString(R.string.create_user),
        onCustomAction = { viewModel.launchCreateUserScreen(navController) }
    )
}