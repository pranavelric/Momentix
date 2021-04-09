package com.alarm.momentix.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.R
import com.alarm.momentix.utils.CoroutinesHelper


class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()

        CoroutinesHelper.delayWithMain(2000L) {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }


    }


}