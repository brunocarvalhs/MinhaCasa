package br.com.brunocarvalhs.minhacasa.features.includeHome

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.minhacasa.commos.BaseFragment
import br.com.brunocarvalhs.minhacasa.databinding.FragmentIncludeHomeBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncludeHomeFragment :
    BaseFragment<FragmentIncludeHomeBinding>(FragmentIncludeHomeBinding::inflate) {

    private val viewModel: IncludeHomeViewModel by viewModels()

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.includeButtom.isEnabled = false
        binding.token.editText?.doOnTextChanged { text, _, _, _ ->
            binding.includeButtom.isEnabled = !text.isNullOrEmpty()
        }
        binding.includeButtom.setOnClickListener {
            viewModel.include(binding.token.editText?.text.toString())
        }
        binding.cancelButtom.setOnClickListener { cancel() }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is IncludeHomeViewState.Error -> showError(it.error)
                IncludeHomeViewState.Loading -> showLoading()
                is IncludeHomeViewState.Success -> displayData(it.home)
            }
        }
    }

    private fun displayData(home: Home) {
        val action = IncludeHomeFragmentDirections.actionIncludeHomeFragmentToNavCosts(home)
        findNavController().navigate(action)
    }

    private fun showLoading() {

    }

    private fun showError(error: String) {

    }

    private fun cancel() {
        findNavController().popBackStack()
    }
}