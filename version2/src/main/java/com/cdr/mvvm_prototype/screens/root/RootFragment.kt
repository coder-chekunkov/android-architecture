package com.cdr.mvvm_prototype.screens.root

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cdr.core.views.BaseFragment
import com.cdr.core.views.screenViewModel
import com.cdr.version2.R
import com.cdr.version2.databinding.FragmentRootBinding

class RootFragment : BaseFragment(R.layout.fragment_root) {

    override val viewModel: RootViewModel by screenViewModel()
    private lateinit var binding: FragmentRootBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRootBinding.bind(view)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}