package com.example.crm.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crm.databinding.ActionViewBinding
import com.example.crm.model.Customer
import com.example.crm.model.CustomerActions
import com.example.crm.utils.hide
import com.example.crm.utils.show

class ActionsAdapter :
    ListAdapter<CustomerActions, ActionsAdapter.CustomerActionViewHolder>(CustomersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerActionViewHolder {
        return CustomerActionViewHolder(
            ActionViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerActionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CustomerActionViewHolder(var binding: ActionViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomerActions) {
            binding.actionTypeTv.text = data.ActionType
            when (data.ActionType) {
                "Call" -> {
                    binding.callActionView.show()
                    binding.visitActionView.hide()
                    binding.followUpView.hide()
                    binding.phoneTv.text = data.ActionDetails
                }
                "Visit" -> {
                    binding.callActionView.hide()
                    binding.followUpView.hide()
                    binding.visitActionView.show()
                    binding.locationTv.text = data.ActionDetails
                }

                "Follow up" -> {
                    binding.callActionView.hide()
                    binding.followUpView.show()
                    binding.visitActionView.hide()
                    binding.followUpTv.text = data.ActionDetails
                }

            }
        }
    }

}

class CustomersDiffUtil : DiffUtil.ItemCallback<CustomerActions>() {

    override fun areItemsTheSame(oldItem: CustomerActions, newItem: CustomerActions): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CustomerActions, newItem: CustomerActions): Boolean {
        return oldItem == oldItem
    }
}
