package com.cdr.mvvm_prototype.screens.root.tabs.profile

import android.os.Bundle
import android.view.View
import com.cdr.core.utils.collectFlow
import com.cdr.core.views.*
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile), HasCustomTitle, HasCustomAction {

    override val viewModel: ProfileViewModel by screenViewModel()
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        with(binding) {
            collectFlow(viewModel.profile) { profile ->
                profileNameTextView.text = profile?.name
                profileCompanyTextView.text = profile?.company
            }

            editProfileButton.setOnClickListener { viewModel.launchEditProfileScreen(this@ProfileFragment) }
        }
    }

    override fun getScreenTitle(): String = getString(R.string.profile)
    override fun getCustomAction(): CustomAction = CustomAction(iconRes = R.drawable.ic_edit,
        textAction = getString(R.string.edit_profile),
        onCustomAction = { viewModel.launchEditProfileScreen(this) })
}