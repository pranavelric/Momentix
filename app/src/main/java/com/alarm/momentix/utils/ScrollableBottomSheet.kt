package com.alarm.momentix.utils

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ScrollableBottomSheet<v : View> : BottomSheetBehavior<v>() {


    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: v,
        event: MotionEvent
    ): Boolean {
        super.onInterceptTouchEvent(parent, child, event)
        return false
    }

}