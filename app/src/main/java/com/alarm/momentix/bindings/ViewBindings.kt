package com.alarm.momentix.bindings

import android.graphics.Typeface
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter

@BindingAdapter("myTextStyle")
fun myTextStyle(view: ToggleButton, checked: Boolean) {


    if (checked) {

    } else {
        view.setTypeface(null, Typeface.NORMAL)

    }


}