package com.cdr.mvvm_prototype.screens.root.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cdr.core.utils.collectFlow
import com.cdr.core.views.*
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard), HasCustomTitle,
    HasCustomMultipleAction {

    override val viewModel: DashboardViewModel by screenViewModel()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        navController = findNavController()

        with(binding) {
            collectFlow(viewModel.selectedUser) {
                usernameTextView.text = it?.name
                companyTextView.text = it?.company
                Glide.with(requireContext())
                    .load(it?.photo)
                    .circleCrop()
                    .error(R.drawable.ic_account)
                    .placeholder(R.drawable.ic_account)
                    .into(userImageView)
            }

            collectFlow(viewModel.selectedLanguage) {
                languageTextView.text = it?.name
                Glide.with(requireContext())
                    .load(it?.res)
                    .circleCrop()
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(languageImageView)
            }

            changeUserButton.setOnClickListener { viewModel.launchUsersListScreen(navController) }
            changeLanguageButton.setOnClickListener { viewModel.launchLanguageListScreen(navController) }
        }
    }

    override fun getScreenTitle(): String = getString(R.string.dashboard)
    override fun getMultipleCustomAction(): MultipleCustomAction = MultipleCustomAction(
        menuRes = R.menu.dashboard_toolbar_menu,
        onSeveralCustomActions = listOf(
            Action(
                id = R.id.selectChangeUserButton,
                action = { viewModel.launchUsersListScreen(navController) }
            ),
            Action(
                id = R.id.selectChangeLanguageButton,
                action = { viewModel.launchLanguageListScreen(navController) }
            )
        )
    )
}