package com.cdr.mvvm_prototype.screens.home

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.BaseScreen
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home), HasCustomTitle {

    override val viewModel: HomeViewModel by screenViewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        with(binding) {
            viewModel.selectedUser.observe(viewLifecycleOwner) {
                userTextView.text = it.name
                companyTextView.text = it.company
                Glide.with(requireContext()).load(it.photo).circleCrop().error(R.drawable.ic_user)
                    .placeholder(R.drawable.ic_user).into(imageView)

                if (viewModel.isNewSelected) viewModel.onUserUpdated(view, "User has been changed!")
            }

            changeUserButton.setOnClickListener { viewModel.onChangeUserPressed() }
        }
    }

    override fun getScreenTitle(): String = "Home \uD83C\uDFDA"

    class Screen : BaseScreen
}