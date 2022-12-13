package br.com.brunocarvalhs.minhacasa.commos

import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        initView()
        viewObservation()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let { argumentsView(it) }
    }

    abstract fun argumentsView(arguments: Bundle)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        setMenu(menu, inflater)
    }

    abstract fun initView()

    abstract fun viewObservation()

    open fun setMenu(menu: Menu, inflater: MenuInflater) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        selectedMenu(item.itemId)
        return false
    }

    open fun selectedMenu(@IdRes id: Int) {

    }
}