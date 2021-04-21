package com.alarm.momentix.utils

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


fun ImageView.getBackgroundImage(backgroundUrl: Uri?) {

    if (backgroundUrl != null) {


        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor: Cursor? = this.context?.contentResolver?.query(
            backgroundUrl, filePathColumn, null, null, null
        )
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
        val filePath: String? = columnIndex?.let { cursor?.getString(it) }
        if (cursor != null) {
            cursor.close()
        }


        this.context?.let {
            Glide.with(it)
                .load(File(filePath)).into(this)
        }


    }
}
