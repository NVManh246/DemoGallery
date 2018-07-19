package com.example.nvmanh.demogallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final String PATH_IMAGE = PATH + "/DCIM/Camera";
    private static final String PNG = ".png";
    private static final String JPG = ".jpg";
    private static final String JPEG = ".jpeg";
    private static final int LENGTH_CUT = 32;
    private RecyclerView mRecyclerGallery;
    private List<Picture> mPictures;
    private RecyclerAdapterImage mAdapterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPictures = new ArrayList<>();
        mRecyclerGallery.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapterImage = new RecyclerAdapterImage(this, mPictures);
        mRecyclerGallery.setAdapter(mAdapterImage);
        checkPermission();
    }

    private void initView() {
        mRecyclerGallery = findViewById(R.id.recycler_gallery);
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        } else {
            getImageFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getImageFromGallery();
        } else {
            finish();
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getImageFromGallery(){
        File file = new File(PATH_IMAGE);
        File[] images = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getAbsolutePath().endsWith(PNG)
                        || file.getAbsolutePath().endsWith(JPG)
                        || file.getAbsolutePath().endsWith(JPEG);
            }
        });

        for(File f : images){
            Picture p = new Picture(f.getAbsolutePath().substring(LENGTH_CUT), f.getAbsolutePath());
            mPictures.add(p);
            mAdapterImage.notifyDataSetChanged();
        }
    }
}
