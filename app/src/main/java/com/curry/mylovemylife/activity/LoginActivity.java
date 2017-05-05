package com.curry.mylovemylife.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.curry.mylovemylife.R;
import com.curry.mylovemylife.utils.FileUtils;
import com.curry.mylovemylife.utils.GetDeviceInfo;

public class LoginActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        String testStr = "SD卡是否存在："+FileUtils.isExternalStorageAvailable()+"\n"
                +"可用内存大小："+ FileUtils.getAvailableMemorySize(LoginActivity.this)+"\n"
                +"获得系统总内存"+FileUtils.getTotalMemory(this)+"\n"
                +"获取手机外部可用空间大小"+FileUtils.getAvailableExternalMemorySize(this)+"\n"
                +"获取手机外部总空间大小"+FileUtils.getTotalExternalMemorySize(this)+"\n"
                +"获取手机内部总的存储空间"+FileUtils.getTotalInternalMemorySize(this)+"\n"
                +"获取系统总内存"+FileUtils.getTotalMemorySize(this)+"\n"
                +"获取当前可用内存"+FileUtils.getAvailableMemory(this);
        mTextView.setText(testStr);
    }

    private void initView(){
        mTextView = (TextView) findViewById(R.id.text);
    }
}
