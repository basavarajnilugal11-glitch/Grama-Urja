package com.gramaUrja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.gramaUrja.data.CropInfo
import com.gramaUrja.databinding.FragmentPumpTimerBinding

class PumpTimerFragment : Fragment() {

    private var _binding: FragmentPumpTimerBinding? = null
    private val binding get() = _binding!!

    private val cropData = mapOf(
        "Maize"     to CropInfo("Maize",     "1.5–2 hrs", "Critical during tasseling period",    "Morning irrigation (6–8 AM)"),
        "Paddy"     to CropInfo("Paddy",     "2–3 hrs",   "Keep field flooded during tillering", "Evening irrigation (5–7 PM)"),
        "Sugarcane" to CropInfo("Sugarcane", "3–4 hrs",   "Critical during grand growth phase",  "Alternate day irrigation"),
        "Ragi"      to CropInfo("Ragi",      "1–1.5 hrs", "Drought tolerant, avoid waterlogging","Once every 3–4 days"),
        "Tomato"    to CropInfo("Tomato",    "1–2 hrs",   "Consistent moisture needed",          "Drip preferred, twice daily"),
        "Groundnut" to CropInfo("Groundnut", "1–2 hrs",   "Critical during pod filling stage",   "Once every 4–5 days"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPumpTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crops = cropData.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, crops)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCrop.adapter = adapter
        binding.spinnerCrop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateCropInfo(cropData[crops[position]]!!)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        updateCropInfo(cropData["Maize"]!!)
    }

    private fun updateCropInfo(info: CropInfo) {
        binding.tvCropName.text    = "💧 ${info.name}: ${info.duration}"
        binding.tvCropNote.text    = info.note
        binding.tvRecommended.text = "⏰ Recommended: ${info.recommended}"
        binding.tvPumpAction.text  = "✅ If power ON now: Start pump · Set timer for ${info.duration}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}