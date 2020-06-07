package com.sharad.googlekt.ui.MainActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharad.googlekt.R
import com.sharad.googlekt.databinding.ItemStateBinding
import com.sharad.googlekt.model.Details
import com.sharad.googlekt.utils.getPeriod
import com.sharad.googlekt.utils.toDateFormat

class ItemAdapter(val clickListener: (stateDetails: Details) -> Unit = {}) :
    ListAdapter<Details, ItemAdapter.StateViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StateViewHolder(
        ItemStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class StateViewHolder(private val binding: ItemStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(details: Details) {
            binding.textState.text = details.state
            binding.textLastUpdatedView.text = itemView.context.getString(
                R.string.text_last_updated,
                getPeriod(
                    details.lastUpdatedTime.toDateFormat()
                )
            )

            binding.textConfirmed.text = details.confirmed
            binding.textActive.text = details.active
            binding.textRecovered.text = details.recovered
            binding.textDeath.text = details.deaths

            // Set Click Listener
            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }

                val item = getItem(bindingAdapterPosition)
                item.let {
                    clickListener.invoke(it)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Details>() {
            override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean =
                oldItem.state == newItem.state

            override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean =
                oldItem == newItem
        }
    }
}
