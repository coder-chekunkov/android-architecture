package com.cdr.mvvm_prototype.screens.home

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.cdr.core.views.*
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
            }

            viewModel.selectedLanguage.observe(viewLifecycleOwner) {
                languageTextView.text = it.name
                Glide.with(requireContext()).load(it.res).circleCrop()
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round).into(languagePhoto)
            }

            if (viewModel.isNewUser && viewModel.isNewLanguage) viewModel.onShowSnackbar(
                view, R.string.message_new_user_and_language, R.color.purple_500
            ) else if (viewModel.isNewUser) viewModel.onShowSnackbar(
                view, R.string.message_new_user, R.color.dark_green
            ) else if (viewModel.isNewLanguage) viewModel.onShowSnackbar(
                view, R.string.message_new_language, R.color.sienna
            )

            changeUserButton.setOnClickListener { viewModel.onChangeUserPressed() }
            changeLanguageButton.setOnClickListener { viewModel.onChangeLanguagePressed() }
        }
    }

    override fun getScreenTitle(): String = "Home \uD83C\uDFDA"

    class Screen : BaseScreen
}