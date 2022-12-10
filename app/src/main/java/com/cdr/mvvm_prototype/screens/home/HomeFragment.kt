package com.cdr.mvvm_prototype.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.BaseScreen
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.mvvm_prototype.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(), HasCustomTitle {

    override val viewModel: HomeViewModel by screenViewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            viewModel.state.observe(viewLifecycleOwner) {
                textView.text = it.text
                homeLayout.setBackgroundColor(resources.getColor(it.color))
            }

            editTextButton.setOnClickListener { viewModel.onEditPressed() }
            changeColorButton.setOnClickListener { viewModel.onChangeColorPressed() }
        }

        return binding.root
    }

    override fun getScreenTitle(): String = "Home \uD83C\uDFDA"

    class Screen : BaseScreen
}