package com.dvt.currencyexchangeapp.ui.currencies.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.core.extensions.capitaliseName
import com.dvt.core.extensions.getInitials
import com.dvt.currencyexchangeapp.databinding.CurrencyRowItemLayoutBinding
import com.dvt.network.models.Currencies

class CurrencyAdapter: ListAdapter<Currencies, CurrencyAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val binding:CurrencyRowItemLayoutBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Currencies){

            binding.apply {
                tvNameAvi.text = item.toString().getInitials().capitaliseName()
                currencyName.text = item.toString()
            }
        }
    }
    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<Currencies>(){
            override fun areItemsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
                return oldItem.bTCUSD == newItem.bTCUSD
            }

            override fun areContentsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = CurrencyRowItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}