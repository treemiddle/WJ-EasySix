package com.example.sample.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemLabelBinding
import com.example.sample.ui.model.mock.MockData

class BookAdapter : ListAdapter<MockData, BookAdapter.BookHolder>(object : DiffUtil.ItemCallback<MockData>() {
    override fun areItemsTheSame(oldItem: MockData, newItem: MockData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MockData, newItem: MockData): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ItemLabelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class BookHolder(private val binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MockData) {
            binding.item = item
            binding.executePendingBindings()
        }

    }
}