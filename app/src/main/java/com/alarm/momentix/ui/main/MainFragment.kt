package com.alarm.momentix.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alarm.momentix.R
import com.alarm.momentix.utils.setFullScreenWithBtmNav

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setFullScreenWithBtmNav()
    }

}