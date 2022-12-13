package br.com.brunocarvalhs.minhacasa.features.registerCosts

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.minhacasa.commos.BaseFragment
import br.com.brunocarvalhs.minhacasa.databinding.FragmentRegisterCostsBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.enums.Type
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterCostsFragment :
    BaseFragment<FragmentRegisterCostsBinding>(FragmentRegisterCostsBinding::inflate) {

    private val viewModel: RegisterCostsViewModel by viewModels()

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        setupAutoComplete()
        binding.cancelButtom.setOnClickListener { cancel() }
        binding.saveButtom.setOnClickListener { viewModel.create(createCost()) }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterCostsViewState.Error -> showError(it.error)
                RegisterCostsViewState.Loading -> showLoading()
                is RegisterCostsViewState.Success -> displayData(it.home, it.cost)
            }
        }
    }

    private fun displayData(home: Home, cost: Cost) {
        val action =
            RegisterCostsFragmentDirections.actionRegisterCostsFragmentToCostsFragment(home)
        findNavController().navigate(action)
    }

    private fun showLoading() {

    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun setupAutoComplete() {
        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.simple_dropdown_item_1line,
            Type.values().toList()
        )
        binding.autoComplete.setAdapter(adapter)
    }

    private fun cancel() {
        findNavController().popBackStack()
    }

    private fun createCost() = Cost(
        name = binding.textField.editText?.text.toString(),
        type = Type.valueOf(binding.textInputLayout.editText?.text.toString())
    )
}