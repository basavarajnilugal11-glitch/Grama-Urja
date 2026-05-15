package com.gramaUrja

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gramaUrja.adapters.FeedAdapter
import com.gramaUrja.data.FeedItem
import com.gramaUrja.data.PowerState
import com.gramaUrja.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedAdapter: FeedAdapter
    private val feedItems = mutableListOf<FeedItem>()

    private lateinit var database: DatabaseReference
    private var powerListener: ValueEventListener? = null
    private var zoneKey  = "your_village"
    private var userName = "Farmer"

    // ✅ Default random items — always shown in feed
    private val defaultFeedItems = listOf(
        FeedItem("Raju K.",    true,  "3m ago"),
        FeedItem("Meena S.",   false, "1h ago"),
        FeedItem("Govinda R.", true,  "1h 12m ago"),
        FeedItem("Lakshmi B.", false, "3h ago"),
        FeedItem("Suresh M.",  true,  "4h ago")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Read name and zone from SharedPreferences
        val prefs    = requireContext().getSharedPreferences("grama_urja", Context.MODE_PRIVATE)
        userName     = prefs.getString("user_name", "Farmer") ?: "Farmer"
        val userZone = prefs.getString("user_zone", "Your Village") ?: "Your Village"
        zoneKey      = userZone.trim().lowercase().replace(" ", "_")

        // ✅ Update all header labels
        binding.tvGreeting.text      = "Hello, $userName 👋"
        binding.tvZoneName.text      = userZone
        binding.tvZoneSub.text       = "Mandya Taluk"
        binding.tvLocationBadge.text = "📍 $userZone"

        // ✅ Load default items so feed is never empty
        feedItems.clear()
        feedItems.addAll(defaultFeedItems)

        feedAdapter = FeedAdapter(feedItems)
        binding.rvCommunityFeed.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter       = feedAdapter
            setHasFixedSize(true)
        }
        feedAdapter.notifyDataSetChanged()

        updatePowerUI(PowerState.ON)

        binding.btnPowerOn.setOnClickListener  { reportPower(PowerState.ON)  }
        binding.btnPowerOff.setOnClickListener { reportPower(PowerState.OFF) }

        // Connect Firebase
        try {
            database = FirebaseDatabase.getInstance().reference
            listenToPowerStatus()
        } catch (e: Exception) {
            Toast.makeText(requireContext(),
                "Firebase error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listenToPowerStatus() {
        powerListener = database
            .child("zones").child(zoneKey).child("power_on")
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    if (_binding == null) return
                    val isOn = snapshot.getValue(Boolean::class.java) ?: true
                    updatePowerUI(if (isOn) PowerState.ON else PowerState.OFF)
                }
                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
            })
    }

    private fun reportPower(state: PowerState) {
        // ✅ Always read fresh name from SharedPreferences
        val prefs        = requireContext().getSharedPreferences("grama_urja", Context.MODE_PRIVATE)
        val reporterName = prefs.getString("user_name", "Farmer") ?: "Farmer"
        val isOn         = state == PowerState.ON

        // ✅ Add name to TOP of feed immediately
        feedItems.add(0, FeedItem(reporterName, isOn, "just now"))
        feedAdapter.notifyItemInserted(0)
        binding.rvCommunityFeed.scrollToPosition(0)

        updatePowerUI(if (isOn) PowerState.ON else PowerState.OFF)

        // Write to Firebase
        if (::database.isInitialized) {
            database.child("zones").child(zoneKey).child("power_on").setValue(isOn)
            database.child("zones").child(zoneKey).child("reported_by").setValue(reporterName)
            database.child("zones").child(zoneKey).child("feed").push().setValue(
                mapOf(
                    "name"     to reporterName,
                    "power_on" to isOn,
                    "time"     to "just now"
                )
            )
        }

        // ✅ Toast with farmer name
        Toast.makeText(
            requireContext(),
            if (isOn) "✅ $reporterName reported: Power is ON"
            else      "🔴 $reporterName reported: Power is OFF",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updatePowerUI(state: PowerState) {
        val ctx = requireContext()
        if (state == PowerState.ON) {
            binding.cardPowerStatus.setCardBackgroundColor(
                ContextCompat.getColor(ctx, R.color.power_on_bg))
            binding.tvPowerStatus.text = "POWER IS ON"
            binding.tvPowerStatus.setTextColor(
                ContextCompat.getColor(ctx, R.color.green_primary))
            binding.tvPowerUpdated.text = "Live from Firebase ⚡"
            binding.ivPowerIcon.setImageResource(R.drawable.ic_bolt)
            binding.ivPowerIcon.setColorFilter(
                ContextCompat.getColor(ctx, R.color.green_primary))
            binding.btnPowerOn.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.green_dark))
            binding.btnPowerOff.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.surface_card))
        } else {
            binding.cardPowerStatus.setCardBackgroundColor(
                ContextCompat.getColor(ctx, R.color.power_off_bg))
            binding.tvPowerStatus.text = "POWER IS OFF"
            binding.tvPowerStatus.setTextColor(
                ContextCompat.getColor(ctx, R.color.red_primary))
            binding.tvPowerUpdated.text = "Live from Firebase 🔴"
            binding.ivPowerIcon.setImageResource(R.drawable.ic_bolt_off)
            binding.ivPowerIcon.setColorFilter(
                ContextCompat.getColor(ctx, R.color.red_primary))
            binding.btnPowerOn.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.surface_card))
            binding.btnPowerOff.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.red_dark))
        }
    }

    override fun onDestroyView() {
        if (::database.isInitialized) {
            powerListener?.let {
                database.child("zones").child(zoneKey)
                    .child("power_on").removeEventListener(it)
            }
        }
        super.onDestroyView()
        _binding = null
    }
}
