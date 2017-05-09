package com.curry.mylovemylife.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.curry.mylovemylife.widget.AnimationButton;
import com.curry.mylovemylife.R;

public class LoginActivity extends BaseActivity {
    private TextView mTextView;
    private AnimationButton animationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        initView();

        animationButton = (AnimationButton)findViewById(R.id.action_button);
        animationButton.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                animationButton.start();
            }

            @Override
            public void animationFinish() {
                Toast.makeText(LoginActivity.this, "动画执行完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.text1);
    }
}
