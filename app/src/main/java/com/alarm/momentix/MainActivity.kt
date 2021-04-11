package com.alarm.momentix

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.alarm.momentix.databinding.ActivityMainBinding
import com.alarm.momentix.utils.MySharedPrefrences
import com.alarm.momentix.utils.setFullScreen
import com.alarm.momentix.utils.setFullScreenForNotch
import com.alarm.momentix.utils.setFullScreenWithBtmNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_container)
    }
    @Inject
    lateinit var mySharedPrefrences: MySharedPrefrences
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        setFullScreenWithBtmNav()
        setFullScreenForNotch()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


}