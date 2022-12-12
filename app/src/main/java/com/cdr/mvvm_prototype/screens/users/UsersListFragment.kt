package com.cdr.mvvm_prototype.screens.users

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdr.core.views.*
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.FragmentUsersListBinding

class UsersListFragment : BaseFragment(R.layout.fragment_users_list), HasCustomTitle,
    HasCustomAction {

    override val viewModel by screenViewModel<UsersListViewModel>()
    private lateinit var binding: FragmentUsersListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersListBinding.bind(view)

        val adapter = UsersAdapter(viewModel.userActionListener)
        viewModel.users.observe(viewLifecycleOwner) { adapter.data = it }
        viewModel.screenTitle.observe(viewLifecycleOwner) { notifyScreenUpdates() }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun getScreenTitle(): String =
        "${viewModel.screenTitle.value} \uD83D\uDC76"

    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_random,
        textAction = "Random user",
        onCustomAction = { viewModel.createRandomUser() }
    )

    class Screen : BaseScreen
}