package com.sharad.googlekt.ui.TotalAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharad.googlekt.databinding.TotalCardBinding
import com.sharad.googlekt.model.Details
import com.sharad.googlekt.utils.getPeriod
import com.sharad.googlekt.utils.toDateFormat

class TotalAdapter : ListAdapter<Details, TotalAdapter.TotalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TotalViewHolder(
            TotalCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) =
        holder.bind(getItem(position))


    class TotalViewHolder(private val binding: TotalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(details: Details) {
            binding.conformText.text = details.confirmed
            binding.textView2.text = details.active
            binding.textView3.text = details.recovered
            binding.textView4.text = details.deaths
            binding.timeStamp.text = getPeriod(details.lastUpdatedTime.toDateFormat())

            // New Confirmed
            details.deltaConfirmed.let {
                if (it == "0") {
                    binding.textView.visibility = View.GONE
                } else {
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.text = details.deltaConfirmed
                }
            }

            // New Recovered
            details.deltaRecovered.let {
                if (it == "0") {
                    binding.textNewRecovered.visibility = View.GONE
                } else {
                    binding.textNewRecovered.visibility = View.VISIBLE
                    binding.textNewRecovered.text = details.deltaRecovered
                }
            }

            // New Deaths
            details.deltaDeaths.let {
                if (it == "0") {
                    binding.textNewDeaths.visibility = View.GONE
                } else {
                    binding.textNewDeaths.visibility = View.VISIBLE
                    binding.textNewDeaths.text = details.deltaDeaths
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