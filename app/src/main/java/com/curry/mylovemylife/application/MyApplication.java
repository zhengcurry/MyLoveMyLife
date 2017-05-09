package com.curry.mylovemylife.application;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.curry.mylovemylife.utils.ActivityCollector;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by curry on 2017/5/4.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static MyApplication myApplication = null;
    public static OkHttpClient mOkHttpClient;

    public synchronized static MyApplication getInstance() {
        if (null == myApplication) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * add by shouyi in 2017-03-29
         *  初始化OkHttp
         */
        if (mOkHttpClient == null) {
            try {
                File sdcache = getExternalCacheDir();//获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
                int cacheSize = 10 * 1024 * 1024;
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
                mOkHttpClient = builder.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityCollector.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityCollector.removeActivity(activity);
    }
}
