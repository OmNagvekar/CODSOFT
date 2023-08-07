package com.example.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private TextView flashoff;
    private TextView flashon;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashLightOn = false;
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraId,false);
                isFlashLightOn = false;
                flashon.setVisibility(View.INVISIBLE);
                flashoff.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"This device does not have flashLight",Toast.LENGTH_SHORT).show();
            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        flashoff = findViewById(R.id.textView);
        flashon = findViewById(R.id.textView3);
        flashon.setVisibility(View.INVISIBLE);
        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraId = cameraManager.getCameraIdList()[0];
            }
        } catch (Exception e) {
              e.printStackTrace();
            Toast.makeText(this,"This device does not have flashLight",Toast.LENGTH_SHORT).show();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashLight();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void toggleFlashLight() {
        try{
            if(isFlashLightOn){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId,false);
                    isFlashLightOn = false;
                    flashon.setVisibility(View.INVISIBLE);
                    flashoff.setVisibility(View.VISIBLE);
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId,true);
                    isFlashLightOn = true;
                    flashoff.setVisibility(View.INVISIBLE);
                    flashon.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            Toast.makeText(this,"This device does not have flashLight",Toast.LENGTH_SHORT).show();
        }
    }
}