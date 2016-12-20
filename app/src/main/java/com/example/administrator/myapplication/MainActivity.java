package com.example.administrator.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity implements EasyPermissions.PermissionCallbacks,View.OnClickListener{
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final String TAG = "MainActivity";
    TextView test_scan_qrcode = null;
    TextView tset_generate_qrcode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_scan_qrcode = (TextView) findViewById(R.id.test_scan_qrcode);
        tset_generate_qrcode = (TextView) findViewById(R.id.tset_generate_qrcode);
        test_scan_qrcode.setOnClickListener(this);
        tset_generate_qrcode.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    public void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this,perms)) {
            EasyPermissions.requestPermissions(this,"扫描二维码需要打开相机和散光灯的权限",REQUEST_CODE_QRCODE_PERMISSIONS,perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_scan_qrcode :
                    Log.i(TAG, "onClick:test_scan_qrcode");
                    startActivity(new Intent(MainActivity.this,TestScanActivity.class));
                    break;
                case R.id.tset_generate_qrcode :
                    Log.i(TAG, "onClick:tset_generate_qrcode");
                    startActivity(new Intent(MainActivity.this,TestGenerateActivity.class));
                    break;
            }
    }
}
