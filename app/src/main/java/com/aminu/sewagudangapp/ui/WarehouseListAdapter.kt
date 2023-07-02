package com.aminu.sewagudangapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aminu.sewagudangapp.R
import com.aminu.sewagudangapp.model.Warehouse

class WarehouseListAdapter(
    private val onItemClickListener: (Warehouse) -> Unit
): ListAdapter<Warehouse, WarehouseListAdapter.WarehouseViewHolder>(WORDS_COMPARATION) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarehouseViewHolder {
        return WarehouseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WarehouseViewHolder, position: Int) {
        val warehouse = getItem(position)
        holder.bind(warehouse)
        holder.itemView.setOnClickListener {
            onItemClickListener(warehouse)
        }
    }



    class WarehouseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView : TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView : TextView = itemView.findViewById(R.id.addressTextView)
        private val ownerTextView : TextView = itemView.findViewById(R.id.ownerTextView)

        fun bind(warehouse: Warehouse?) {
            nameTextView.text = warehouse?.name
            addressTextView.text = warehouse?.address
            ownerTextView.text = warehouse?.owner
        }

        companion object {
            fun create(parent: ViewGroup): WarehouseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_warehouse, parent, false)
                return WarehouseViewHolder(view)
            }
        }
    }

    companion object{
        private val WORDS_COMPARATION = object : DiffUtil.ItemCallback<Warehouse>(){
            override fun areItemsTheSame(oldItem: Warehouse, newItem: Warehouse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Warehouse, newItem: Warehouse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}

private fun WarehouseListAdapter.WarehouseViewHolder.bind(warehouse: Warehouse?) {

}
