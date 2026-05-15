package com.gramaUrja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gramaUrja.adapters.ZoneAdapter
import com.gramaUrja.data.Zone
import com.gramaUrja.data.ZoneStatus
import com.gramaUrja.databinding.FragmentZonesBinding

class ZonesFragment : Fragment() {

    private var _binding: FragmentZonesBinding? = null
    private val binding get() = _binding!!

    private val zones = listOf(
        Zone("Hebbalaguppe", "T-247", "Mandya Taluk", 12, ZoneStatus.ON),
        Zone("Malavalli",    "T-391", "Mandya Taluk", 8,  ZoneStatus.OFF),
        Zone("Srirangapatna","T-118", "Mandya Taluk", 21, ZoneStatus.ON),
        Zone("Pandavapura",  "T-204", "Mandya Taluk", 5,  ZoneStatus.UNKNOWN),
        Zone("Nagamangala",  "T-056", "Mandya Taluk", 3,  ZoneStatus.OFF),
        Zone("Maddur",       "T-312", "Mandya Taluk", 9,  ZoneStatus.ON),
        Zone("Krishnarajpet","T-088", "Mandya Taluk", 14, ZoneStatus.OFF),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvZones.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ZoneAdapter(zones)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
