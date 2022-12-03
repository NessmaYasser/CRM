package com.example.crm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crm.databinding.CustomerRvItemBinding
import com.example.crm.model.Customer
import com.example.crm.ui.main.adapters.ActionsAdapter

class CustomersAdapter :
    ListAdapter<Customer, CustomersAdapter.CustomerViewHolder>(CustomersDiffUtil()) {

    var onItemClicked : ((Customer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            CustomerRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CustomerViewHolder(var binding: CustomerRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked?.invoke(currentList[adapterPosition])
            }
        }

        fun bind(data: Customer) {
            binding.nameTv.text = data.Name
            if(!data.Actions.isNullOrEmpty()) {
                var customerActionsAdapter = ActionsAdapter()
                binding.actionsRv.apply {
                    adapter = customerActionsAdapter
                    layoutManager = LinearLayoutManager(binding.root.context)
                    customerActionsAdapter.submitList(data.Actions)

                }
            }
        }
    }
}

class CustomersDiffUtil : DiffUtil.ItemCallback<Customer>() {

    override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem.Id == oldItem.Id
    }
}
