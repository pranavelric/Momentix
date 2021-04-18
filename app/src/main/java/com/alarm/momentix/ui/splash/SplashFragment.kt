package com.alarm.momentix.ui.splash

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.R
import com.alarm.momentix.utils.CoroutinesHelper
import com.alarm.momentix.utils.setFullScreen


class SplashFragment : Fragment(R.layout.fragment_splash) {




    override fun onStart() {
        super.onStart()
        activity?.setFullScreen()
        CoroutinesHelper.delayWithMain(2000L) {
            if (findNavController().currentDestination?.id == R.id.splashFragment)
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }


    }


}