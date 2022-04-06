package com.muhammadhusniabdillah.inventariskti.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhammadhusniabdillah.inventariskti.R

class RecyclerAdapterHome :
    ListAdapter<TableHome, RecyclerAdapterHome.HomeViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.barcode, current.spec, current.location, current.pic, current.condition)
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val barcodeView = itemView.findViewById<TextView>(R.id.tv_data_barcode)
        private val specView = itemView.findViewById<TextView>(R.id.tv_data_spec)
        private val locationView = itemView.findViewById<TextView>(R.id.tv_data_location)
        private val picView = itemView.findViewById<TextView>(R.id.tv_data_pic)
        private val conditionView = itemView.findViewById<TextView>(R.id.tv_data_condition)

        fun bind(barcode: String, spec: String, location: String, pic: String, condition: String) {
            barcodeView.text = barcode
            specView.text = spec
            locationView.text = location
            picView.text = pic
            conditionView.text = condition
        }

        companion object {
            fun create(parent: ViewGroup): HomeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item, parent, false)
                return HomeViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<TableHome>() {
            override fun areItemsTheSame(oldItem: TableHome, newItem: TableHome): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: TableHome, newItem: TableHome): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}