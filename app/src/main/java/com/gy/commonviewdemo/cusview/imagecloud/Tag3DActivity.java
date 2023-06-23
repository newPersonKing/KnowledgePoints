package com.gy.commonviewdemo.cusview.imagecloud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gy.commonviewdemo.MainActivity;
import com.gy.commonviewdemo.R;

public class Tag3DActivity extends AppCompatActivity {

    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    private ViewTagsAdapter viewTagsAdapter;
    private VectorTagsAdapter vectorTagsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_3d);
        tagCloudView = findViewById(R.id.tag_cloud);

        textTagsAdapter = new TextTagsAdapter(new String[20]);
        viewTagsAdapter = new ViewTagsAdapter();
        vectorTagsAdapter = new VectorTagsAdapter();

        tagCloudView.setAdapter(textTagsAdapter);

        findViewById(R.id.tag_text).setOnClickListener(v -> tagCloudView.setAdapter(textTagsAdapter));

        findViewById(R.id.tag_view).setOnClickListener(v -> tagCloudView.setAdapter(viewTagsAdapter));

        findViewById(R.id.tag_vector).setOnClickListener(v -> tagCloudView.setAdapter(vectorTagsAdapter));
    }
}
