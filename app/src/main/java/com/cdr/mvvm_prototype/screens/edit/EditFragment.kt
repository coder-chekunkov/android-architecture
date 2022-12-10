package com.cdr.mvvm_prototype.screens.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdr.core.views.*
import com.cdr.mvvm_prototype.databinding.FragmentEditBinding

class EditFragment : BaseFragment(), HasCustomTitle {

    override val viewModel by screenViewModel<EditViewModel>()
    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        with(binding) {
            viewModel.currentText.observe(viewLifecycleOwner) { editText.setText(it) }
            saveButton.setOnClickListener { viewModel.onSavePressed(editText.text.toString()) }
            cancelButton.setOnClickListener { viewModel.onCanselPressed() }
        }

        return binding.root
    }

    override fun getScreenTitle(): String = "Edit \uD83D\uDCDD"

    class Screen(val currentText: String) : BaseScreen
}