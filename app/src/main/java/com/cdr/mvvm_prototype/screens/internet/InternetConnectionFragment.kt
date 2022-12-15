package com.cdr.mvvm_prototype.screens.internet

import android.os.Bundle
import android.view.View
import com.cdr.core.views.*
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.FragmentInternetConnectionBinding

class InternetConnectionFragment : BaseFragment(R.layout.fragment_internet_connection),
    HasCustomTitle, HasCustomAction {

    override val viewModel by screenViewModel<InternetConnectionViewModel>()
    private lateinit var binding: FragmentInternetConnectionBinding
    private lateinit var viewFragment: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInternetConnectionBinding.bind(view)
        viewFragment = view

        if (viewModel.isFirstMessageOfError) viewModel.showFirstMessageOfError(view)
    }

    override fun getScreenTitle(): String = getString(R.string.error_connection_title)
    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_update,
        textAction = getString(R.string.update),
        onCustomAction = {
            if (checkInternetConnection()) viewModel.launchAllCitiesScreen()
            else viewModel.showSnackbarWithErrorConnection(viewFragment)
        }
    )

    class Screen : BaseScreen
}