package com.gramaUrja.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gramaUrja.R
import com.gramaUrja.data.FeedItem
import com.gramaUrja.databinding.ItemFeedBinding

class FeedAdapter(private val items: MutableList<FeedItem>) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item        = items[position]
        val ctx         = holder.binding.root.context
        val statusColor = if (item.isPowerOn) R.color.green_primary else R.color.red_primary

        // Shows: "Raju K. reported Power ON"
        holder.binding.tvFeedText.text   = "${item.name} reported Power "
        holder.binding.tvFeedStatus.text = if (item.isPowerOn) "ON" else "OFF"
        holder.binding.tvFeedStatus.setTextColor(ContextCompat.getColor(ctx, statusColor))
        holder.binding.tvFeedTime.text   = item.time
        holder.binding.viewDot.setBackgroundTintList(
            ContextCompat.getColorStateList(ctx, statusColor)
        )
    }
}
