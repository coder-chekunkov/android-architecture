package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.create_user

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentCreateUserBinding

class CreateUserFragment : BaseFragment(R.layout.fragment_create_user), HasCustomTitle {

    override val viewModel: CreateUserViewModel by screenViewModel()
    private lateinit var binding: FragmentCreateUserBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateUserBinding.bind(view)
        navController = findNavController()

        with(binding) {
            createUserButton.setOnClickListener {
                val isUserNameExist = isNameOfUserIsNotBlank()
                val isCompanyNameExist = isCompanyNameIsNotBlank()

                if (isUserNameExist && isCompanyNameExist) viewModel.createNewUser(
                    username = usernameEditText.text.toString(),
                    companyName = companyEditText.text.toString(),
                    isMakeSelect = makeSelectedCheckBox.isChecked,
                    view = view,
                    navController = navController
                )
                else {
                    if (!isUserNameExist) valueTextInputUsername.error = getString(R.string.message_error_empty)
                    else valueTextInputUsername.error = null
                    if (!isCompanyNameExist) valueTextInputCompanyName.error = getString(R.string.message_error_empty)
                    else valueTextInputCompanyName.error = null
                    viewModel.showErrorSnackbar(view)
                }
            }
        }
    }

    private fun isNameOfUserIsNotBlank(): Boolean = binding.usernameEditText.text?.isNotBlank()!!
    private fun isCompanyNameIsNotBlank(): Boolean = binding.companyEditText.text?.isNotBlank()!!

    override fun getScreenTitle(): String = getString(R.string.create_user)
}