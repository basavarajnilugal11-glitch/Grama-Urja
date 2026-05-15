package com.gramaUrja.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gramaUrja.R
import com.gramaUrja.data.AlertItem
import com.gramaUrja.data.AlertType
import com.gramaUrja.databinding.ItemAlertBinding

class AlertAdapter(private val alerts: List<AlertItem>) :
    RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAlertBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = alerts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alert = alerts[position]
        val ctx   = holder.binding.root.context
        holder.binding.tvAlertTitle.text = alert.title
        holder.binding.tvAlertSub.text   = alert.subtitle
        holder.binding.tvAlertTime.text  = alert.time
        when (alert.type) {
            AlertType.POWER_ON -> {
                holder.binding.tvAlertIcon.text = "⚡"
                holder.binding.cardAlert.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.power_on_bg))
                holder.binding.tvAlertTitle.setTextColor(ContextCompat.getColor(ctx, R.color.green_primary))
            }
            AlertType.POWER_OFF -> {
                holder.binding.tvAlertIcon.text = "🔴"
                holder.binding.cardAlert.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.power_off_bg))
                holder.binding.tvAlertTitle.setTextColor(ContextCompat.getColor(ctx, R.color.red_primary))
            }
            AlertType.SCHEDULED -> {
                holder.binding.tvAlertIcon.text = "⏰"
                holder.binding.cardAlert.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.surface_card))
                holder.binding.tvAlertTitle.setTextColor(ContextCompat.getColor(ctx, R.color.amber_primary))
            }
        }
    }
}
