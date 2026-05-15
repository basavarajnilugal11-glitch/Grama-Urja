package com.gramaUrja.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gramaUrja.R
import com.gramaUrja.data.Zone
import com.gramaUrja.data.ZoneStatus
import com.gramaUrja.databinding.ItemZoneBinding

class ZoneAdapter(private val zones: List<Zone>) :
    RecyclerView.Adapter<ZoneAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemZoneBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemZoneBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = zones.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val zone = zones[position]
        val ctx  = holder.binding.root.context
        holder.binding.tvZoneName.text = zone.name
        holder.binding.tvZoneSub.text  = "${zone.transformer} · ${zone.activeFarmers} farmers active"
        when (zone.status) {
            ZoneStatus.ON -> {
                holder.binding.tvZoneStatus.text = "ON"
                holder.binding.tvZoneStatus.setTextColor(ContextCompat.getColor(ctx, R.color.green_primary))
                holder.binding.tvZoneStatus.setBackgroundResource(R.drawable.bg_badge_green)
                holder.binding.cardZone.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.power_on_bg))
            }
            ZoneStatus.OFF -> {
                holder.binding.tvZoneStatus.text = "OFF"
                holder.binding.tvZoneStatus.setTextColor(ContextCompat.getColor(ctx, R.color.red_primary))
                holder.binding.tvZoneStatus.setBackgroundResource(R.drawable.bg_badge_red)
                holder.binding.cardZone.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.power_off_bg))
            }
            ZoneStatus.UNKNOWN -> {
                holder.binding.tvZoneStatus.text = "Unknown"
                holder.binding.tvZoneStatus.setTextColor(ContextCompat.getColor(ctx, R.color.amber_primary))
                holder.binding.tvZoneStatus.setBackgroundResource(R.drawable.bg_badge_amber)
                holder.binding.cardZone.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.surface_card))
            }
        }
    }
}
