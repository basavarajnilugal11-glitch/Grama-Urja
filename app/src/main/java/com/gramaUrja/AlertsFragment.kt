package com.gramaUrja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gramaUrja.adapters.AlertAdapter
import com.gramaUrja.data.AlertItem
import com.gramaUrja.data.AlertType
import com.gramaUrja.databinding.FragmentAlertsBinding

class AlertsFragment : Fragment() {

    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!

    private val alerts = listOf(
        AlertItem("Power is back ON",    "Hebbalaguppe zone · Confirmed by 4 farmers",       "3 min ago",     AlertType.POWER_ON),
        AlertItem("Power cut reported",  "Malavalli zone · Duration unknown",                 "1 hr ago",      AlertType.POWER_OFF),
        AlertItem("Scheduled Outage",    "Srirangapatna · 10 AM–2 PM (BESCOM maintenance)",  "Tomorrow",      AlertType.SCHEDULED),
        AlertItem("Power restored",      "Nagamangala · After 4hr outage",                   "Yesterday 6 PM",AlertType.POWER_ON),
        AlertItem("Power cut reported",  "Pandavapura · Transformer fault reported",          "Yesterday 2 PM",AlertType.POWER_OFF),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAlerts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AlertAdapter(alerts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}