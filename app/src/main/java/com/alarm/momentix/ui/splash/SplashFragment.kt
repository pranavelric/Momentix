package com.alarm.momentix.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.MainActivity
import com.alarm.momentix.R
import com.alarm.momentix.utils.CoroutinesHelper
import com.alarm.momentix.utils.setFullScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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