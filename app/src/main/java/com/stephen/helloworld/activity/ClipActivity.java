package com.stephen.helloworld.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.stephen.helloworld.R;
import com.stephen.helloworld.utils.ImageTools;
import com.stephen.helloworld.views.ClipLayout;

import java.io.File;

public class ClipActivity extends Activity {

    private ClipLayout clipLayout;
    private ProgressDialog loadingDialog;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        //这步必须要加
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        clipLayout = (ClipLayout) findViewById(R.id.cliplayout);
        clipLayout.setBitmap(bitmap);
        ((Button) findViewById(R.id.comfirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = clipLayout.clip();
                        String path = Environment.getExternalStorageDirectory() + "/ClipHeadPhoto/cache/" + System.currentTimeMillis() + ".png";
                        ImageTools.savePhotoToSDCard(bitmap, path);
                        loadingDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path", path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).start();
            }
        });
    }
}
