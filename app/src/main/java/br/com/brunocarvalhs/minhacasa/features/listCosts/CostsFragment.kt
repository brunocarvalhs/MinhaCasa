package br.com.brunocarvalhs.minhacasa.features.listCosts

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.minhacasa.R
import br.com.brunocarvalhs.minhacasa.commos.BaseFragment
import br.com.brunocarvalhs.minhacasa.databinding.FragmentCostsListBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.enums.Type
import br.com.brunocarvalhs.minhacasa.features.listCosts.viewModel.CostsViewModel
import br.com.brunocarvalhs.minhacasa.features.listCosts.viewModel.CostsViewState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsFragment : BaseFragment<FragmentCostsListBinding>(FragmentCostsListBinding::inflate) {

    private val viewModel: CostsViewModel by viewModels()

    override fun initView() {
        binding.list.layoutManager = LinearLayoutManager(context)
        Type.values().toList().forEach { createTab(it) }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                displayData(tab.id)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
        binding.floatingActionButton.setOnClickListener { navigationRegister() }
    }

    private fun listCostsFix(list: List<Cost>) {
        binding.list.adapter = CostsRecyclerViewAdapter(list) { navigationInfo(it) }
    }

    private fun listCostsVar(list: List<Cost>) {
        binding.list.adapter = CostsRecyclerViewAdapter(list) { navigationInfo(it) }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun setMenu(menu: Menu, inflater: MenuInflater) {
        super.setMenu(menu, inflater)
        inflater.inflate(R.menu.list_cost_menu, menu)
    }

    override fun selectedMenu(id: Int) {
        super.selectedMenu(id)
        when (id) {
            R.id.action_global_nav_home -> navigationMyHome()
        }
    }

    private fun navigationMyHome() {
        viewModel.cleanHome()
        val action = CostsFragmentDirections.actionGlobalNavHome()
        findNavController().navigate(action)
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CostsViewState.Error -> showError(it.error)
                CostsViewState.Loading -> showLoading()
                CostsViewState.Success -> displayData()
            }
        }
    }

    private fun navigationRegister() {
        val action = CostsFragmentDirections
            .actionCostsFragmentToRegisterCostsFragment(viewModel.home)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun displayData(position: Int = 0) {
        when (position) {
            Type.Fix.ordinal -> listCostsFix(viewModel.listFix)
            Type.Variable.ordinal -> listCostsVar(viewModel.listVar)
        }
    }

    private fun showLoading() {

    }

    private fun showError(error: String) {

    }

    private fun createTab(type: Type) {
        binding.tabLayout.addTab(
            binding.tabLayout.newTab()
                .setText(type.title)
                .setId(type.id)
                .setContentDescription(type.title)
        )
    }

    private fun navigationInfo(cost: Cost) {
        val action = CostsFragmentDirections.actionCostsFragmentToInfoFragment(
            viewModel.home,
            cost,
            cost.name
        )
        findNavController().navigate(action)
    }
}