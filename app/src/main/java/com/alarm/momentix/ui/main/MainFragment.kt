package com.alarm.momentix.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.ui.activities.MainActivity
import com.alarm.momentix.R
import com.alarm.momentix.adapters.AlarmRcAdapter
import com.alarm.momentix.databinding.FragmentMainBinding
import com.alarm.momentix.utils.checkAboveKitkat
import com.alarm.momentix.utils.getStatusBarHeight
import com.alarm.momentix.utils.rateUs
import com.alarm.momentix.utils.share
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var alarmRcAdapter: AlarmRcAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }


        (activity as MainActivity).commonViewModel.getAllLiveAlarm()
            .observe(viewLifecycleOwner, { alarmList ->
                alarmRcAdapter.submitList(alarmList)
            })



        setHasOptionsMenu(true)
        setSlidingBehaviour()
        setViews()
        setClickListeners()

        return binding.root

    }

    private fun setClickListeners() {

        alarmRcAdapter.setOnAlarmCancelClickListener { alarm, isChecked ->
            if (!isChecked) {
                context?.let { alarm.cancelAlarm(it) }
            } else {
                context?.let { alarm.schedule(it) }
            }
            (activity as MainActivity).commonViewModel.update(alarm)
        }


        alarmRcAdapter.setAlarmDeleteClickListener { alarm, position ->
            alarmRcAdapter.notifyItemRemoved(position)
            alarmRcAdapter.notifyItemRangeChanged(position, alarmRcAdapter.currentList.size)
            (activity as MainActivity).commonViewModel.deleteAlarm(alarm.alarmId)
            context?.let { alarm.cancelAlarm(it) }
        }


    }


    private fun setViews() {


        binding.fragmentListalarmsAddAlarm.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createAlarmFragment)

        }
        binding.fragmentListalarmsRecylerView.apply {
            adapter = alarmRcAdapter
        }

        alarmRcAdapter.setOnItemClickListener { alarm ->
            val bundle = Bundle().apply {
                putSerializable("alarm", alarm)
            }
            findNavController().navigate(R.id.action_mainFragment_to_createAlarmFragment, bundle)
        }

    }


    private fun setSlidingBehaviour() {
        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        if (checkAboveKitkat()) {

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
//                if (!(activity as MainActivity).mySharedPrefrences.getIsNightModeEnabled()) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(true)
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(false)
//                }
                activity?.rateUs()
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


