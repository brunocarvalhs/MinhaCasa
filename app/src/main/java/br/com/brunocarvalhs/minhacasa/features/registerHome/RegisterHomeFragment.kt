package br.com.brunocarvalhs.minhacasa.features.registerHome

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.minhacasa.commos.BaseFragment
import br.com.brunocarvalhs.minhacasa.databinding.FragmentRegisterHomeBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterHomeFragment :
    BaseFragment<FragmentRegisterHomeBinding>(FragmentRegisterHomeBinding::inflate) {

    private val viewModel: RegisterHomeViewModel by viewModels()

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.createButtom.setOnClickListener { viewModel.create(createHome()) }
        binding.cancelButtom.setOnClickListener { cancel() }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterHomeViewState.Error -> showError(it.error)
                RegisterHomeViewState.Loading -> showLoading()
                is RegisterHomeViewState.Success -> displayData(it.home)
            }
        }
    }

    private fun createHome(): Home = Home(
        name = binding.name.editText?.text.toString()
    )

    private fun displayData(home: Home) {
        val action = RegisterHomeFragmentDirections.actionRegisterHomeFragmentToNavCosts(home)
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