package com.gy.commonviewdemo.imgpix.pix4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gy.commonviewdemo.R;
import com.gy.commonviewdemo.imgpix.SelfDrawView;
import com.gy.commonviewdemo.imgpix.pix3.SecondSketchFilter;


/**
 * 绘制操作工具类
 */
public class DrawPix4Activity extends AppCompatActivity {

    private ImageView imageSource = null;
    private SelfDrawView imageDirsction = null;
    private Button button1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_px_4);

        initView();

        initListener();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        imageSource = (ImageView) findViewById(R.id.image_source);
        imageDirsction = (SelfDrawView) findViewById(R.id.image_direction);
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

            /**
             * 开始执行绘制素描的操作
             */
//            DrawUtils.startSelfDraw(imageDirsction, bitmapSource);
            Sketch simpleSketch = new Sketch();
            Bitmap deBitmap = simpleSketch.createPencli(bitmapSource);
            ImageView btImg = findViewById(R.id.iv_bit);
            btImg.setImageBitmap(deBitmap);
        });

    }
}
