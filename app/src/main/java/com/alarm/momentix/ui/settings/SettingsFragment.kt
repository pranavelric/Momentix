package com.alarm.momentix.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alarm.momentix.databinding.SettingsFragmentBinding
import com.alarm.momentix.ui.activities.MainActivity
import com.alarm.momentix.utils.getBackgroundImage


class SettingsFragment : Fragment() {


    private lateinit var binding: SettingsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater)

        setClickListeners()
        setData()

        return binding.root
    }

    private fun setData() {
        binding.expandedImage.getBackgroundImage(Uri.parse((activity as MainActivity).mySharedPrefrences.getBrackgroundImage()))



        binding.nightmodeSwitch.isChecked =
            (activity as MainActivity).mySharedPrefrences.getIsNightModeEnabled()


    }

    private fun setClickListeners() {
        binding.nightmodeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            setNightMode(isChecked)
        }

        binding.background.setOnClickListener {

            selectImage()


        }


    }

    private fun selectImage() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, 9998)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 9998) {

            val uri: Uri = data?.data ?: return

            (activity as MainActivity).mySharedPrefrences.setBackgroundImage(uri.toString())


            binding.expandedImage.getBackgroundImage(uri)
        }
    }


    private fun setNightMode(checked: Boolean) {
        if (checked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            (activity as MainActivity).mySharedPrefrences.setNightModeEnabled(false)
        }
    }


}