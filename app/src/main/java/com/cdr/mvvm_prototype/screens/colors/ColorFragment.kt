package com.cdr.mvvm_prototype.screens.colors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdr.core.views.*
import com.cdr.mvvm_prototype.databinding.FragmentColorBinding

class ColorFragment : BaseFragment(), HasCustomTitle {

    override val viewModel by screenViewModel<ColorViewModel>()
    private lateinit var binding: FragmentColorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorBinding.inflate(inflater, container, false)

        with(binding) {
            viewModel.color.observe(viewLifecycleOwner) {
                colorName.text = it.nameColor
                colorLayout.setBackgroundColor(resources.getColor(it.idColor))
            }
            newColorButton.setOnClickListener { viewModel.onNewColor() }
            applyButton.setOnClickListener { viewModel.onApply() }
        }

        return binding.root
    }

    override fun getScreenTitle(): String = "Random Color  \t\uD83C\uDF08"

    class Screen(val currentValue: Int) : BaseScreen
}