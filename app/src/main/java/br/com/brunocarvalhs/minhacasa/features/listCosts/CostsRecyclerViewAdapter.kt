package br.com.brunocarvalhs.minhacasa.features.listCosts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.minhacasa.R
import br.com.brunocarvalhs.minhacasa.databinding.ItemCostsBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost

class CostsRecyclerViewAdapter(
    private val context: Context,
    private val values: List<Cost>,
    private val callback: (Cost) -> Unit,
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemCostsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.name
        holder.type.text = item.type?.name
        holder.value.text =
            context.getString(R.string.list_cots_header_value_all, item.value.toString())
        holder.container.isChecked = item.isPay
        holder.container.setOnClickListener { callback(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val type: TextView = binding.type
        val value: TextView = binding.value
        val container = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

}