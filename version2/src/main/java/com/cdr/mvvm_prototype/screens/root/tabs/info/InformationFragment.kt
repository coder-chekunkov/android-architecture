package com.cdr.mvvm_prototype.screens.root.tabs.info

import android.os.Bundle
import android.view.View
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentInfoBinding

class InformationFragment : BaseFragment(R.layout.fragment_info), HasCustomTitle {

    override val viewModel: InformationViewModel by screenViewModel()
    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        with(binding) {
            applicationNameTextView.text = viewModel.applicationName
            versionNameTextView.text = viewModel.versionName
            versionCodeTextView.text = viewModel.versionCode
        }
    }

    override fun getScreenTitle(): String = getString(R.string.information)
}