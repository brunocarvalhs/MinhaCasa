package br.com.brunocarvalhs.minhacasa.features.myHome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.brunocarvalhs.minhacasa.commos.BaseFragment
import br.com.brunocarvalhs.minhacasa.databinding.FragmentHomeBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.session { this.clickItem(it) }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.list.layoutManager = GridLayoutManager(context, NUMBER_COLUMN)
        binding.createMyHome.setOnClickListener { navigationCreateMyHome() }
        binding.includeMyHome.setOnClickListener { navigationIncludeMyHome() }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewState.Error -> showError(it.error)
                HomeViewState.Loading -> showLoading()
                is HomeViewState.Screen -> displayData(it.home)
            }
        }
    }

    private fun displayData(list: List<Home>) {
        if (list.isNullOrEmpty()) {
            binding.list.visibility = View.GONE
        } else {
            binding.list.adapter = MyHomesRecyclerViewAdapter(list) { clickItem(it) }
        }
    }

    private fun clickItem(home: Home) {
        viewModel.selectHome(home)
        val action = HomeFragmentDirections.actionHomeFragmentToNavCosts(home, home.name)
        findNavController().navigate(action)
    }

    private fun showLoading() {

    }

    private fun showError(error: String) {

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun navigationIncludeMyHome() {
        val action = HomeFragmentDirections.actionHomeFragmentToIncludeHomeFragment()
        findNavController().navigate(action)
    }

    private fun navigationCreateMyHome() {
        val action = HomeFragmentDirections.actionHomeFragmentToRegisterHomeFragment()
        findNavController().navigate(action)
    }

    private companion object {
        const val NUMBER_COLUMN = 2
    }
}