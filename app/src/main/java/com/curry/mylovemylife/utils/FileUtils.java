package com.curry.mylovemylife.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by curry on 2017/5/5.
 */

public class FileUtils {
    /**
     * 外部存储是否可用,是否存在SDCard
     *
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /******************************************getDataDirectory**********************************************/

    /**
     * 获取当前可用内存大小
     * api>18 getBlockSizeLong()、getAvailableBlocksLong()
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAvailableMemorySize(Context context) {
        File filePath = Environment.getDataDirectory();
        StatFs statFs = new StatFs(filePath.getPath());
        //字节
        long blockSize = statFs.getBlockSizeLong();
        //可用的空间数量
        long availableBlocks = statFs.getAvailableBlocksLong();

        //formatFileSize()以字节、千字节、兆字节等形式格式化内容大小
        String size = Formatter.formatFileSize(context, blockSize * availableBlocks);//GB M KB
        return size;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();  //path目录:"/data"
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        String size = Formatter.formatFileSize(context, totalBlocks * blockSize);//GB M KB
        return size;
    }

    /******************************************外部存储*******************************************/

    /**
     * 获取手机外部可用空间大小
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAvailableExternalMemorySize(Context context) {
        if (isExternalStorageAvailable()) {
            File path = Environment.getExternalStorageDirectory();//获取SDCard根目录 "/storage/emulated/0"
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            String size = Formatter.formatFileSize(context, availableBlocks * blockSize);//GB M KB
            return size;
        } else {
            return "不存在";
        }
    }

    /**
     * 获取手机外部总空间大小
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getTotalExternalMemorySize(Context context) {
        if (isExternalStorageAvailable()) {
            File path = Environment.getExternalStorageDirectory(); //获取SDCard根目录
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            String size = Formatter.formatFileSize(context, totalBlocks * blockSize);//GB M KB
            return size;
        } else {
            return "不存在";
        }
    }

    /******************************************内存**********************************************/

    /**
     * 获取系统总内存
     *
     * @param context 可传入应用程序上下文。
     * @return 总内存大单位为B。
     */
    public static String getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fileReader = new FileReader(dir);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
            String memoryLine = bufferedReader.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            bufferedReader.close();
            //"\\D+"正则表达式 : 标识所有数字及0-9
            String size = Formatter.formatFileSize(context, Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024);//GB M KB
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static String getAvailableMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        String size = Formatter.formatFileSize(context, memoryInfo.availMem);//GB M KB
        return size;
    }

    private static final DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static final DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
}
