package br.com.brunocarvalhs.minhacasa.features.myHome

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.minhacasa.databinding.ItemHomesBinding
import br.com.brunocarvalhs.minhacasa.domain.entities.Home

class MyHomesRecyclerViewAdapter(
    private val context: Context,
    private val values: List<Home>,
    private val callback: (Home) -> Unit,
) : RecyclerView.Adapter<MyHomesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemHomesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
        holder.container.setOnClickListener { callback.invoke(item) }
        holder.container.setOnLongClickListener {
            Toast.makeText(context, "Long Press", Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemHomesBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val container = binding.root

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }


}