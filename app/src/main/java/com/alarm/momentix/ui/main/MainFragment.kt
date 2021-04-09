package com.alarm.momentix.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alarm.momentix.databinding.FragmentMainBinding
import com.alarm.momentix.utils.setFullScreenWithBtmNav
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : Fragment() {

    private lateinit var bottomSheet: View
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)


//        bottom sheet sliding set
        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.bottomSheet.setPadding(0, 0, 0, 0)

                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheet.setPadding(0, 120, 0, 0)

                    }


                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    binding.tempView.alpha = (slideOffset)
                    if(slideOffset<0.5F){
                        binding.tempView.alpha= 0.5F
                    }

                    bottomSheet.setPadding(0, (slideOffset * 120).toInt(), 0, 0)


                }

            })


        }





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setFullScreenWithBtmNav()


    }

}