package br.com.brunocarvalhs.minhacasa.features.listCosts

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var selectedPosition = 0
    private val viewModel: CostsViewModel by viewModels()

    override fun initView() {
        setupTabBar()
        setupFloatingButton()
        setupReflesh()
        setupList()
        setupHeader()
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                binding.refresh.isEnabled = topRowVerticalPosition >= 0
            }
        })
    }

    private fun setupReflesh() {
        binding.refresh.setOnRefreshListener { viewModel.fetchData() }
    }

    private fun setupTabBar() {
        Type.values().toList().forEach { createTab(it) }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                displayData(tab.position)
                selectedPosition = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupFloatingButton() {
        binding.floatingActionButton.setOnClickListener { navigationRegister() }
    }

    private fun setupHeader() {
        binding.headerValues.visibility =
            if (viewModel.listVar.isEmpty() && viewModel.listFix.isEmpty()) View.GONE
            else View.VISIBLE
        binding.valueAll.text = getString(
            R.string.list_cots_header_value_all,
            viewModel.calculationValueAll().toString()
        )
    }

    private fun listCostsFix(list: List<Cost>) {
        binding.list.adapter =
            CostsRecyclerViewAdapter(requireContext(), list) { navigationInfo(it) }
    }

    private fun listCostsVar(list: List<Cost>) {
        binding.list.adapter =
            CostsRecyclerViewAdapter(requireContext(), list) { navigationInfo(it) }
    }

    override fun argumentsView(arguments: Bundle) {
        selectedPosition = arguments.getInt(SELECTED_TAB)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_TAB, binding.tabLayout.selectedTabPosition)
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
            setupHeader()
        }
    }

    private fun navigationRegister() {
        val action = CostsFragmentDirections
            .actionCostsFragmentToRegisterCostsFragment(viewModel.home)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        binding.tabLayout.getTabAt(selectedPosition)?.select()
    }

    override fun onPause() {
        super.onPause()
        selectedPosition = binding.tabLayout.selectedTabPosition
    }

    companion object {
        const val SELECTED_TAB = "SELECTED_TAB"
    }
}