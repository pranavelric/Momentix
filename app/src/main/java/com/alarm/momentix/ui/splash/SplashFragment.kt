package com.alarm.momentix.ui.splash

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.R
import com.alarm.momentix.ui.activities.MainActivity
import com.alarm.momentix.utils.CoroutinesHelper
import com.alarm.momentix.utils.setFullScreen
import com.alarm.momentix.utils.setFullScreenForNotch


class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setFullScreen()
        (activity as MainActivity).setFullScreenForNotch()



        CoroutinesHelper.delayWithMain(2000L) {
            if (findNavController().currentDestination?.id == R.id.splashFragment)
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }


    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setFullScreen()
        (activity as MainActivity).setFullScreenForNotch()

    }


}