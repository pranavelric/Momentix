package com.alarm.momentix.utils

import android.os.Build


fun checkAboveOreo(): Boolean {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.O
}
