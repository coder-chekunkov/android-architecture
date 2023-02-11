package com.cdr.mvvm_prototype.screens.root.tabs.profile.edit_profile

import android.os.Bundle
import android.view.View
import com.cdr.core.utils.collectFlow
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile), HasCustomTitle {

    override val viewModel: EditProfileViewModel by screenViewModel()
    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        with(binding) {
            collectFlow(viewModel.profile) { profile ->
                profileNameEditText.setText(profile?.name)
                companyEditText.setText(profile?.company)
            }

            canselButton.setOnClickListener { viewModel.cansel(this@EditProfileFragment) }
            saveButton.setOnClickListener {
                val isNameOfProfile = isNameOfProfileIsNotBlank()
                val isNameOfCompanyProfile = isNameOfCompanyProfileIsNotBlank()

                if (isNameOfProfile && isNameOfCompanyProfile) {
                    val name = profileNameEditText.text.toString()
                    val company = companyEditText.text.toString()
                    viewModel.save(view, this@EditProfileFragment, name, company)
                } else {
                    if (!isNameOfProfile) valueTextInputProfileName.error = getString(R.string.message_error_empty)
                    else valueTextInputProfileName.error = null
                    if (!isNameOfCompanyProfile) valueTextInputProfileCompanyName.error = getString(R.string.message_error_empty)
                    else valueTextInputProfileCompanyName.error = null
                }
            }
        }
    }

    private fun isNameOfProfileIsNotBlank(): Boolean = binding.profileNameEditText.text?.isNotBlank()!!
    private fun isNameOfCompanyProfileIsNotBlank(): Boolean = binding.companyEditText.text?.isNotBlank()!!

    override fun getScreenTitle(): String = getString(R.string.edit_profile)
}