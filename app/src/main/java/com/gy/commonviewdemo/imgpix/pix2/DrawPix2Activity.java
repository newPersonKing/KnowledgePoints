package com.gy.commonviewdemo.imgpix.pix2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gy.commonviewdemo.R;
import com.gy.commonviewdemo.imgpix.DrawUtils;
import com.gy.commonviewdemo.imgpix.SelfDrawView;


/**
 * 绘制操作工具类
 */
public class DrawPix2Activity extends AppCompatActivity {

    private ImageView imageSource = null;
    private Button button1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_px_2);

        initView();

        initListener();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        imageSource = (ImageView) findViewById(R.id.image_source);
        button1 = (Button) findViewById(R.id.button1);
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {
        button1.setOnClickListener(v -> {
            //todo 记录ImageView转Bitmap
            imageSource.setDrawingCacheEnabled(true);
            imageSource.buildDrawingCache();
            Bitmap bitmapSource = imageSource.getDrawingCache();

            Bitmap bitmap = ImageProcessUtils.getInkImg(bitmapSource, 15, 2);
            /**
             * 开始执行绘制素描的操作
             */
//            DrawUtils.startSelfDraw(imageDirsction, bitmapSource);

            ImageView btImg = findViewById(R.id.iv_bit);
            bitmap.setHasAlpha(true);
            btImg.setAlpha(1f);
            btImg.setImageBitmap(bitmap);
        });

    }
}
