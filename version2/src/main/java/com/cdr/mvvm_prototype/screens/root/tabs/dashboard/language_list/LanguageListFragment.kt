package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.language_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.HasCustomTitle
import com.cdr.core.views.screenViewModel
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentLanguageListBinding

class LanguageListFragment : BaseFragment(R.layout.fragment_language_list), HasCustomTitle {

    override val viewModel: LanguageListViewModel by screenViewModel()
    private lateinit var binding: FragmentLanguageListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLanguageListBinding.bind(view)

        val adapter = LanguageAdapter(viewModel.languageActionListener)
        viewModel.languages.observe(viewLifecycleOwner) { adapter.data = it }
        viewModel.languageTitle.observe(viewLifecycleOwner) { notifyScreenUpdates() }

        with(binding) {
            languagesRecyclerView.adapter = adapter
            languagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun getScreenTitle(): String =
        getString(R.string.current_language, viewModel.languageTitle.value)
}