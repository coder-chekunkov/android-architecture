package com.cdr.mvvm_prototype.screens.internet

import android.os.Bundle
import android.view.View
import com.cdr.core.views.*
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentNoInternetBinding

class InternetConnectionFragment : BaseFragment(R.layout.fragment_no_internet), HasCustomTitle,
    HasCustomAction {

    override val viewModel: InternetConnectionViewModel by screenViewModel()
    private lateinit var binding: FragmentNoInternetBinding
    private lateinit var mView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoInternetBinding.bind(view)

        mView = view
    }

    override fun getScreenTitle(): String = getString(R.string.error_connection_title)
    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_update,
        textAction = getString(R.string.update),
        onCustomAction = {
            if (checkInternetConnection()) viewModel.launchRootScreen(this)
            else viewModel.showErrorSnackbar(mView)
        }
    )
}