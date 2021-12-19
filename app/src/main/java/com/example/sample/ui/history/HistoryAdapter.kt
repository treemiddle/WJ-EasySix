package com.example.sample.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemLabelBinding
import com.example.sample.ui.model.mock.MockData

class HistoryAdapter : ListAdapter<MockData, HistoryAdapter.HistoryHolder>(object : DiffUtil.ItemCallback<MockData>() {
    override fun areItemsTheSame(oldItem: MockData, newItem: MockData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MockData, newItem: MockData): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            ItemLabelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class HistoryHolder(
        private val binding: ItemLabelBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MockData) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}