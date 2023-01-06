package com.cdr.mvvm_prototype.screens.language

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cdr.core.views.*
import com.cdr.mvvm_prototype.R
import com.cdr.mvvm_prototype.databinding.FragmentLanguagesListBinding

class LanguagesListFragment : BaseFragment(R.layout.fragment_languages_list), HasCustomTitle,
    HasCustomAction {

    override val viewModel by screenViewModel<LanguagesListViewModel>()
    private lateinit var binding: FragmentLanguagesListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLanguagesListBinding.bind(view)

        val adapter = LanguagesAdapter(viewModel.languageActionListener)
        viewModel.languages.observe(viewLifecycleOwner) { adapter.data = it }
        viewModel.screenTitle.observe(viewLifecycleOwner) { notifyScreenUpdates() }

        with(binding) {
            languagesRecyclerView.adapter = adapter
            languagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun getScreenTitle(): String =
        "Languages (${viewModel.screenTitle.value}) \uD83D\uDDA5"

    class Screen : BaseScreen

    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_random,
        textAction = "Random language",
        onCustomAction = { viewModel.createRandomLanguage() }
    )
}