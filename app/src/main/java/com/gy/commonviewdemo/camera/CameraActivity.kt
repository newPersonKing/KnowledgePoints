package com.gy.commonviewdemo.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor

class CameraActivity : AppCompatActivity() {

    // CameraX's use case
    private lateinit var mPreview: Preview
    private var mImageCapture: ImageCapture? = null
    private var mImageAnalysis: ImageAnalysis? = null
    private var mVideoCapture: VideoCapture? = null
    private lateinit var cameraZoomState: LiveData<ZoomState>

    private var isBack = true
    private var isAnalyzing = false
    private var isVideoMode = false
    private var isRecording = false
    private var isCameraXHandling = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        startCameraWhenAttached()

        recordVideo.setOnClickListener {
            onVideoGo()
        }

        light.setOnClickListener {
            mCamera.cameraInfo.torchState.apply {
                if( value == 0 )  mCamera.cameraControl.enableTorch(true) else  mCamera.cameraControl.enableTorch(false)
            }
        }
    }

    private fun startCameraWhenAttached() {
        previewView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{

            override fun onViewAttachedToWindow(v: View) {
                Log.i("ccccccccccccc", "onViewAttachedToWindow")
                ensureCameraPermission()
            }

            override fun onViewDetachedFromWindow(v: View) {
                Log.i("cccccccccccccc", "onViewDetachedFromWindow")
            }
        })
    }
    private val REQUEST_CAMERA = 20
    /*校验权限*/
    private fun ensureCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("CCCCCCCCCCCCCCCCCC", "no camera permission & request")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
            )
            return
        }
        setupCamera(previewView)
    }

    private lateinit var mCameraProvider: ProcessCameraProvider
    private lateinit var mCamera: Camera
    private fun setupCamera(previewView: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                mCameraProvider = cameraProviderFuture.get()
                bindPreview(mCameraProvider, previewView)
                listenGesture()
                initBeepManager()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, lightExecutor)
    }

    // Executor that used to do light work
    private val lightExecutor: Executor by lazy {
        ContextCompat.getMainExecutor(this)
    }

    private fun bindPreview(
        cameraProvider: ProcessCameraProvider,
        previewView: PreviewView, isVideo: Boolean = false
    ) {
        // Select specified camera.
        // val cameraSelector = CameraSelector.Builder().addCameraFilter(AllCameraFilter()).build()

        // Selector that determine which camera will be used.
        val cameraSelector = if (isBack) CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA

        // Image preview use case
        val previewBuilder = Preview.Builder()

        // Image capture use case
        val captureBuilder = ImageCapture.Builder()
            .setTargetRotation(previewView.display.rotation)

        // Image analysis use case
        mImageAnalysis = ImageAnalysis.Builder()
            .setTargetRotation(previewView.display.rotation)
            .setTargetResolution(Size(720, 1440))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        // Video recording use case
//        mVideoCapture = VideoCapture.Builder()
//            .setTargetRotation(previewView.display.rotation)
//            .setVideoFrameRate(25)
//            .setBitRate(3 * 1024 * 1024)
//            .build()

        // Add extended effect for capture and preview.
        val extenderHelper = MyExtenderHelper()
        extenderHelper.setPreviewExtender(previewBuilder, cameraSelector)
        extenderHelper.setCaptureExtender(captureBuilder, cameraSelector)

        // Create preview and capture use case's instance
        mPreview = previewBuilder.build()
        mImageCapture = captureBuilder.build()

        // Must unbind all owner to the camera before binding.
        cameraProvider.unbindAll()

        try {
            // Bind camera provider with selector and necessary use cases.
            mCamera = if (isVideo) {
                cameraProvider.bindToLifecycle(
                    this, cameraSelector,
                    mPreview, mVideoCapture
                )
            } else {
                cameraProvider.bindToLifecycle(
                    this, cameraSelector,
                    mPreview, mImageCapture, mImageAnalysis
                )
            }

            // Bind the view's surface to preview use case.
            mPreview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: java.lang.Exception) {
            Log.e("cccccccc", "camera provider bind error:", e)
        }

        cameraZoomState = mCamera.cameraInfo.zoomState
    }

    // Listen to gesture on preview screen to zoom, focus camera.
    private fun listenGesture() {
        previewView.setOnTouchListener { view, event ->
            Log.d("cccccccc", "onTouch event:$event")

            // Listen to zoom gesture.
            scalePreview(event)

            // Zoom when double click.
            doubleClickZoom(event)

            // Singe tap for focus.
            singleTapForFocus(event)

            true
        }

        // Focus center first.
        focusOnPosition((previewView.width / 2).toFloat(), (previewView.height / 2).toFloat())
    }

    private var scaleDetector: ScaleGestureDetector? = null
    // Zoom preview screen when scale gesture.
    private fun scalePreview(event: MotionEvent) {
        Log.d("cccccccc", "scalePreview event:$event")
        if (scaleDetector == null) {
            scaleDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        cameraZoomState.value?.let {
                            val zoomRatio = it.zoomRatio
                            Log.d(
                                "cccccccc",
                                "Scale factor:${detector.scaleFactor} current:$zoomRatio linear:${it.linearZoom}"
                            )
                            mCamera.cameraControl.setZoomRatio(zoomRatio * detector.scaleFactor)
                        }
                        return true
                    }
                })
        }
        Log.d("cccccccc", "scalePreview onTouchEvent")
        scaleDetector?.onTouchEvent(event)
    }

    val MIN_ZOOM_SCALE = 0.0
    val MIDDLE_ZOOM_SCALE = 0.5
    private var doubleClickDetector: GestureDetector? = null
    // Zoom preview screen when double click gesture.
    private fun doubleClickZoom(event: MotionEvent) {
        Log.d("cccccccc", "doubleClickZoom event:$event")
        if (doubleClickDetector == null) {
            doubleClickDetector = GestureDetector(this,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        Log.d("cccccccc", "Double tap")

                        cameraZoomState.value?.let {
                            val zoomRatio = it.zoomRatio
                            val minRatio = it.minZoomRatio
                            Log.d(
                                "cccccccc",
                                "Double tap zoomRatio:$zoomRatio min:$minRatio max:${it.maxZoomRatio}")

                            // Ratio parameter from 0f to 1f.
                            if (zoomRatio > minRatio) {
                                // Reset to original ratio
                                mCamera.cameraControl.setLinearZoom(MIN_ZOOM_SCALE.toFloat())
                            } else {
                                // Or zoom to 0.5 ratio
                                mCamera.cameraControl.setLinearZoom(MIDDLE_ZOOM_SCALE.toFloat())
                                // Zoom to max
                                // mCamera.cameraControl.setLinearZoom(Constants.MAX_ZOOM_SCALE.toFloat())
                            }
                        }
                        return true
                    }
                })
        }
        doubleClickDetector?.onTouchEvent(event)
    }

    private var singleTapDetector: GestureDetector? = null
    // Focus when single tap.
    private fun singleTapForFocus(event: MotionEvent) {
        if (singleTapDetector == null) {
            singleTapDetector = GestureDetector(this,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        Log.d("ccccccccc", "Single tap confirmed with event:$e")
                        // Focus view when tap.
                        focusOnPosition(event.x, event.y, true)
                        return super.onSingleTapConfirmed(e)
                    }
                })
        }

        singleTapDetector?.onTouchEvent(event)
    }

    private fun focusOnPosition(x: Float, y: Float, needShowTap: Boolean = false) {
        Log.d("ccccccccc", "focusPosition x:$x y:$y")
        val action = FocusMeteringAction.Builder(
             previewView.meteringPointFactory.createPoint(x, y)
        ).build()

        try {
            if (needShowTap) showTapView(x.toInt(), y.toInt())

            Log.d("ccccccccc", "Focus camera")
            mCamera.cameraControl.startFocusAndMetering(action)
        } catch (e: Exception) {
            Log.e("ccccccccc", "Error focus camera:e")
        }
    }

    // Show finger tap location on preview screen.
    private fun showTapView(x: Int, y: Int) {
        Log.d("ccccccccc", "showTapView x:$x y:$y")
        val popupWindow = PopupWindow(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.ic_focus_view)

        val offset = resources.getDimensionPixelSize(R.dimen.tap_view_size)
        Log.d("ccccccccc", "showTapView offset:$offset")

        popupWindow.contentView = imageView
        // popupWindow.setBackgroundDrawable(getDrawable(android.R.color.holo_blue_bright));
        // popupWindow.showAtLocation(binding.previewView, Gravity.CENTER, x, y);
        // popupWindow.showAsDropDown(binding.previewView, x, y)
        popupWindow.showAsDropDown(previewView, x - offset / 2, y - offset /2 )

        previewView.postDelayed({ popupWindow.dismiss() }, 600)
        // binding.previewView.playSoundEffect(SoundEffectConstants.CLICK);
    }

    // Beep manager when recognized qrcode.
    private lateinit var beepManager: BeepManager
    private fun initBeepManager() {
        beepManager = BeepManager(this)
        beepManager.setPlayBeep(true)
        beepManager.setVibrate(true)
    }


    fun onVideoGo() {
        if (!isVideoMode) {
            // Check audio and storage permission before go to video mode.
            ensureAudioStoragePermission(Constants.REQUEST_STORAGE_VIDEO_BINDING)
        } else {
            // Check storage permission before go to camera mode.
            ensureAudioStoragePermission(Constants.REQUEST_STORAGE_BINDING)
        }

    }

    private fun ensureAudioStoragePermission(requestId: Int) {
        if (requestId == Constants.REQUEST_STORAGE || requestId == Constants.REQUEST_STORAGE_BINDING) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(Constants.TAG_CAMERA, "no storage permission & request")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    requestId
                )
                return
            }
            if (requestId == Constants.REQUEST_STORAGE) {
                takenPictureInternal(true)
            } else {
                toggleVideoMode()
            }
        } else if (requestId == Constants.REQUEST_STORAGE_VIDEO
            || requestId == Constants.REQUEST_STORAGE_VIDEO_BINDING) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(Constants.TAG_CAMERA, "no storage or audio permission & request")
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                    ), requestId
                )
                return
            }

            // toggleVideoMode();
            if (requestId == Constants.REQUEST_STORAGE_VIDEO) {
                recordVideo()
            } else {
                toggleVideoMode()
            }
        }
    }
    // Mark for file's name for capturing or recording occasion.
    private var recCount = 0
    private var picCount = 0
    private fun takenPictureInternal(isExternal: Boolean) {
        Log.d(Constants.TAG_CAMERA, "takenPictureInternal isExternal:$isExternal")
        val contentValues = ContentValues()
        contentValues.put(
            MediaStore.MediaColumns.DISPLAY_NAME, Constants.CAPTURED_FILE_NAME
                    + "_" + picCount++
        )
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )
            .build()

        // Mirror image
        ImageCapture.Metadata().apply {
            isReversedHorizontal = true
        }

        mImageCapture?.takePicture(outputFileOptions, lightExecutor, MyCaptureCallback(picCount, this))
    }

    private fun toggleVideoMode() {
        Log.d(Constants.TAG_CAMERA, "toggleVideoMode isVideoMode:$isVideoMode")
        isVideoMode = !isVideoMode
        recordVideo.setImageResource(
            if (isVideoMode) R.drawable.ic_camera_new else R.drawable.ic_video
        )
        capture.setImageResource(
            if (isVideoMode) R.drawable.ic_capture_record else R.drawable.ic_capture
        )
        bindPreview(mCameraProvider, previewView, isVideoMode)
    }


    @SuppressLint("RestrictedApi")
    private fun recordVideo() {
        Log.d(Constants.TAG_CAMERA, "recordVideo() isCameraXHandling:$isCameraXHandling")
        // Ensure recording is done before recording again cause stopRecording is async.
        if (isCameraXHandling) return

        Log.d(Constants.TAG_CAMERA, "recordVideo()")
        val contentValues = ContentValues()
        contentValues.put(
            MediaStore.MediaColumns.DISPLAY_NAME, Constants.RECORDED_FILE_NAME
                    + "_" + recCount++
        )
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, Constants.RECORDED_FILE_NAME_END)
        Log.d(Constants.TAG_CAMERA, "recordVideo() startRecording")
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mVideoCapture?.startRecording(
                VideoCapture.OutputFileOptions.Builder(
                    contentResolver,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues
                ).build(),  // CameraXExecutors.mainThreadExecutor(),
                lightExecutor,
                MyRecordCallback(this) {
                    videoRecordingPrepared()
                }
            )
        } catch (e: Exception) {
            Log.e(Constants.TAG_CAMERA, "Record video error:", e)
        }
        toggleRecordingStatus()
        isCameraXHandling = true
    }

    private fun toggleRecordingStatus() {
        Log.d(Constants.TAG_CAMERA, "toggleRecordingStatus() isVideoMode:$isVideoMode isRecording:$isRecording")
        if (!isVideoMode) return

        isRecording = !isRecording
        capture.setImageResource(
            if (isRecording) R.drawable.ic_capture_record_pressing else R.drawable.ic_capture_record
        )

        // Stop recording when toggle to false.
        if (!isRecording) {
            Log.d(Constants.TAG_CAMERA, "toggleRecordingStatus() stopRecording")
            mVideoCapture?.stopRecording()
            // Keep record button disabled till video recording truly stopped.
            capture.post { capture.isEnabled = false }
        }
    }

    // Enable record button after recording stopped.
    private fun videoRecordingPrepared() {
        Log.d(Constants.TAG_CAMERA, "videoRecordingPrepared()")
        isCameraXHandling = false
        // Keep disabled status for a while to avoid fast click error with "Muxer stop failed!".
        capture.postDelayed({ capture.isEnabled = true }, 500)
    }


}