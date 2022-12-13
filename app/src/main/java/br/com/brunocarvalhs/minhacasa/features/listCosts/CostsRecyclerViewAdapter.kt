package br.com.brunocarvalhs.minhacasa.features.listCosts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.minhacasa.databinding.FragmentCostsBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost

class CostsRecyclerViewAdapter(
    private val values: List<Cost>,
    private val callback: (Cost) -> Unit,
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCostsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name
        holder.container.setOnClickListener { callback(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val container = binding.root

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}