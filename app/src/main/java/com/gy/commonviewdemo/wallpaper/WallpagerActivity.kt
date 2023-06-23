package com.gy.commonviewdemo.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.wallpaper.camera.CameraLiveWallpaper
import com.gy.commonviewdemo.wallpaper.drawimage.TestDrawImageWallpaperService
import com.gy.commonviewdemo.wallpaper.gl.AdvanceGLWallpaperService
import com.gy.commonviewdemo.wallpaper.gl.GLWallpaperService
import com.gy.commonviewdemo.wallpaper.mediaplayer.DynamicWallPaper
import com.gy.commonviewdemo.wallpaper.view.circle.NormalWallpaperService
import com.gy.commonviewdemo.wallpaper.view.clock.TextClockWallpaperService
import kotlinx.android.synthetic.main.activity_wallpager.*

class WallpagerActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpager)

        tv_set_clock.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, TextClockWallpaperService::class.java)
            )
            startActivity(intent)
        }

        tv_gl_3d.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, AdvanceGLWallpaperService::class.java)
            )
            startActivity(intent)
        }

        tv_draw_circle.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, NormalWallpaperService::class.java)
            )
            startActivity(intent)
        }

        tv_video.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, DynamicWallPaper::class.java)
            )
            startActivity(intent)
        }

        tv_camera.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, CameraLiveWallpaper::class.java)
            )
            startActivity(intent)
        }

        tv_test.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, TestDrawImageWallpaperService::class.java)
            )
            startActivity(intent)
        }
    }
}