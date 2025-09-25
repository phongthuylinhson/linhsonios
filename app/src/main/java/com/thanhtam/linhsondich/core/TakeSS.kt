package com.thanhtam.linhsondich.core

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import github.nisrulz.screenshott.ScreenShott
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


data class TakeSS(val view: View, val context: Context) {

    fun savescrolltoGallery() {
        try {
            val bitmap_view =
                ScreenShott.getInstance().takeScreenShotOfView(view)
            val file: File = ScreenShott.getInstance()
                .saveScreenshotToPicturesFolder(context, bitmap_view, "")
            val bitmapFilePath = file.absolutePath
            Toast.makeText(context, "Đã Lưu Ảnh", Toast.LENGTH_SHORT).show()
        } catch (e:Exception) {
            Toast.makeText(context, e.message , Toast.LENGTH_SHORT).show()
        }
    }
}
