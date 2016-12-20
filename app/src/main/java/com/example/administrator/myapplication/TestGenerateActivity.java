package com.example.administrator.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class TestGenerateActivity extends AppCompatActivity{
    private ImageView mChineseIv;
    private ImageView mEnglishIv;
    private ImageView mChineseLogoIv;
    private ImageView mEnglishLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_generate);

        initView();

    }

    private void initView() {
        mChineseIv = (ImageView)findViewById(R.id.iv_chinese);
        mEnglishIv = (ImageView)findViewById(R.id.iv_english);
        mChineseLogoIv = (ImageView)findViewById(R.id.iv_chinese_logo);
        mEnglishLogoIv = (ImageView)findViewById(R.id.iv_english_logo);

    }

    private void createQRCode() {
        createChineseQRCode();
        createEnglishQRCode();
        createChineseqQRCodeWithLogo();
        createEnglishQRCodeWithLogo();
    }

    private void createChineseQRCode() {
        new AsyncTask<Void,Void,Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode("廖成丽", BGAQRCodeUtil.dp2px(TestGenerateActivity.this,150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mChineseIv.setImageBitmap(bitmap);
                }else {
                    Toast.makeText(TestGenerateActivity.this,"生成中文二维码失败",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void createEnglishQRCode() {
        new AsyncTask<Void,Void,Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode("lcl",BGAQRCodeUtil.dp2px(TestGenerateActivity.this,150), Color.parseColor("#ff0000"));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mEnglishIv.setImageBitmap(bitmap);
                }else {
                    Toast.makeText(TestGenerateActivity.this,"生成英文二维码失败",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void createChineseqQRCodeWithLogo() {
        new AsyncTask<Void,Void,Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(TestGenerateActivity.this.getResources(),R.mipmap.logo);
                return QRCodeEncoder.syncEncodeQRCode("廖成丽",BGAQRCodeUtil.dp2px(TestGenerateActivity.this,150),Color.parseColor("#ff0000"),logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mChineseLogoIv.setImageBitmap(bitmap);
                }else {
                    Toast.makeText(TestGenerateActivity.this, "生成带有log的中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void createEnglishQRCodeWithLogo() {
        new AsyncTask<Void,Void,Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(TestGenerateActivity.this.getResources(),R.mipmap.logo);
                return QRCodeEncoder.syncEncodeQRCode("lcl",BGAQRCodeUtil.dp2px(TestGenerateActivity.this,150),Color.parseColor("#ff0000"),logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mEnglishLogoIv.setImageBitmap(bitmap);
                }else {
                    Toast.makeText(TestGenerateActivity.this,"生成带有logo的英文二维码失败",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void decode(final Bitmap bitmap, final String errorTip) {
        new AsyncTask<Void,Void,String>() {

            @Override
            protected String doInBackground(Void... params) {
                return QRCodeDecoder.syncDecodeQRCode(bitmap);
            }

            @Override
            protected void onPostExecute(String result) {
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(TestGenerateActivity.this,errorTip,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TestGenerateActivity.this,result,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public void decodeChinese(View v) {
        mChineseIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mChineseIv.getDrawingCache();
        decode(bitmap, "解析中文二维码失败");
    }

    public void decodeEnglish(View v) {
        mEnglishIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mEnglishIv.getDrawingCache();
        decode(bitmap, "解析英文二维码失败");
    }

    public void decodeChineseWithLogo(View v) {
        mChineseLogoIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mChineseLogoIv.getDrawingCache();
        decode(bitmap, "解析带logo的中文二维码失败");
    }

    public void decodeEnglishWithLogo(View v) {
        mEnglishLogoIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mEnglishLogoIv.getDrawingCache();
        decode(bitmap, "解析带logo的英文二维码失败");
    }

    public void decodeIsbn(View v) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_isbn);
        decode(bitmap, "解析ISBN失败");
    }
}
