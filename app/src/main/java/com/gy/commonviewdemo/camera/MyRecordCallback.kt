package com.gy.commonviewdemo.camera

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.VideoCapture

class MyRecordCallback(
    private val context: Context,
    private val listener: () -> Unit
): VideoCapture.OnVideoSavedCallback {
    @SuppressLint("RestrictedApi")
    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
        Log.d(
            Constants.TAG_CAMERA, "onVideoSaved outputFileResults:"
                    + outputFileResults.savedUri!!.path
        )
        Toast.makeText(
            context,
            "Video got" + (if (outputFileResults.savedUri != null) " @ " + outputFileResults.savedUri!!
                .path else "")
                    + ".", Toast.LENGTH_LONG
        ).show()
        listener.invoke()
    }

    @SuppressLint("RestrictedApi")
    override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
        Log.d(
            Constants.TAG_CAMERA, "onError videoCaptureError:"
                    + videoCaptureError + " message:" + message, cause
        )
        listener.invoke()
    }
}