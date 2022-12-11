package com.cdr.mvvm_prototype.screens.users

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.BaseScreen
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.FragmentUsersListBinding

class UsersListFragment : BaseFragment(R.layout.fragment_users_list), HasCustomTitle {

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

    class Screen : BaseScreen

    override fun getScreenTitle(): String =
        "${viewModel.screenTitle.value} \uD83D\uDC76"
}