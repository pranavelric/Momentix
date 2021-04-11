package com.alarm.momentix.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.MainActivity
import com.alarm.momentix.R
import com.alarm.momentix.adapters.AlarmRcAdapter
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.databinding.FragmentMainBinding
import com.alarm.momentix.utils.getStatusBarHeight
import com.alarm.momentix.utils.share
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var alarmRcAdapter: AlarmRcAdapter

    private val mainFragViewModel: MainFragViewModel by lazy {
        ViewModelProvider(this)[MainFragViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mainFragViewModel.alarmList.observe(this, { alarmList ->
            Log.d("RRR", "onCreate: ${alarmList}")
            if (alarmList.isNotEmpty() && !alarmList.isNullOrEmpty()) {
                alarmRcAdapter.submitList(alarmList)
            }
        })



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        setHasOptionsMenu(true)
        setSlidingBehaviour()
        setViews()
        getData()
        return binding.root

    }

    private fun getData() {
        mainFragViewModel.getAlarms()
    }

    private fun setViews() {


        binding.fragmentListalarmsAddAlarm.setOnClickListener {

            val alarm = Alarm(
                4,
                1,
                1,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                "title",
                "tone",
                true
            )
            mainFragViewModel.insertAlarm(alarm)

        }


        binding.fragmentListalarmsRecylerView.apply {
            adapter =alarmRcAdapter

        }
    }


    private fun setSlidingBehaviour() {
        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.bottomSheet.setPadding(0, 0, 0, 0)

                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        activity?.getStatusBarHeight()?.let { bottomSheet.setPadding(0, it, 0, 0) }

                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    if (slideOffset < 0.5F) {
                        binding.tempView.alpha = 0.5F
                    } else {
                        binding.tempView.alpha = (slideOffset)
                    }

                    bottomSheet.setPadding(
                        0,
                        (slideOffset * activity?.getStatusBarHeight()!!).toInt(),
                        0,
                        0
                    )


                }

            })

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_setting -> {
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                return true
            }
            R.id.action_rate -> {


                if (!(activity as MainActivity).mySharedPrefrences.getIsNightModeEnabled()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(true)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(false)
                }
                return true
            }
            R.id.action_share -> {
                activity?.share("Playstore link", "text")
                return true
            }
            else -> {
                return false
            }
        }
    }


}


