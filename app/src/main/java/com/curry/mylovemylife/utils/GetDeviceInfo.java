package com.curry.mylovemylife.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 获取手机信息
 * <p>
 * Created by curry on 2017/5/4.
 */

public class GetDeviceInfo {
    /**
     * 获取版本号，版本名称
     *
     * @return
     */
    public static String getPackageVersionInfo(Context context) {
        //获得包名
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            return versionName + "-" + versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断手机是否root
     *
     * @return
     */
    public static boolean isRoot() {
        boolean isRoot = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                isRoot = false;
            } else {
                isRoot = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRoot;
    }


    /**
     * 获得手机屏幕宽高 分辨率
     * 要在acivity中使用
     *
     * @return
     */
    public void getHeightAndWidth() {
        //手机分辨率
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        String width = displayMetrics.widthPixels + "";
//        String height = displayMetrics.heightPixels + "";
//
//        //简单写法
//        int width=getWindowManager().getDefaultDisplay().getWidth();
//        int heigth=getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 获取IMEI号，IESI号，手机型号
     */
    public static String getInfo(Context context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        String imei = mTm.getDeviceId();
        String imsi = mTm.getSubscriberId();
        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
        return "手机IMEI号：" + imei + "\n手机IESI号：" + imsi + "\n手机号码" + numer;
    }

    /**
     * 获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String result = wifiInfo.getMacAddress();
        return result;
    }

    /**
     * 获取手机ip地址
     *
     * @return
     */
    public static String getNetworkIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void getPhoneInfo() {
        String phoneInfo = "BOARD: " + android.os.Build.BOARD + "\n";
        phoneInfo += ", BOOTLOADER: " + android.os.Build.BOOTLOADER + "\n";
        //BRAND 运营商
        phoneInfo += ", BRAND: " + android.os.Build.BRAND + "\n";//手机品牌
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI + "\n";
        phoneInfo += ", CPU_ABI2: " + android.os.Build.CPU_ABI2 + "\n";

        //DEVICE 驱动
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE + "\n";
        //DISPLAY Rom的名字 例如 Flyme 1.1.2（魅族rom） &nbsp;JWR66V（Android nexus系列原生4.3rom）
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY + "\n";
        //指纹
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT + "\n";
        //HARDWARE 硬件
        phoneInfo += ", HARDWARE: " + android.os.Build.HARDWARE + "\n";
        phoneInfo += ", HOST: " + android.os.Build.HOST + "\n";
        phoneInfo += ", ID: " + android.os.Build.ID + "\n";
        //MANUFACTURER 生产厂家
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER + "\n";
        //MODEL 机型
        phoneInfo += ", MODEL: " + android.os.Build.MODEL + "\n";
        phoneInfo += ", PRODUCT: " + android.os.Build.PRODUCT + "\n";
        phoneInfo += ", RADIO: " + android.os.Build.RADIO + "\n";
        phoneInfo += ", RADITAGSO: " + android.os.Build.TAGS + "\n";
        phoneInfo += ", TIME: " + android.os.Build.TIME + "\n";
        phoneInfo += ", TYPE: " + android.os.Build.TYPE + "\n";
        phoneInfo += ", USER: " + android.os.Build.USER + "\n";
        //VERSION.RELEASE 固件版本
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE + "\n";
        phoneInfo += ", VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME + "\n";
        //VERSION.INCREMENTAL 基带版本
        phoneInfo += ", VERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL + "\n";
        //VERSION.SDK SDK版本
        phoneInfo += ", VERSION.SDK: " + android.os.Build.VERSION.SDK + "\n";
        phoneInfo += ", VERSION.SDK_INT: " + Build.VERSION.SDK_INT;
    }
}


