package com.curry.mylovemylife.http;

import android.app.Activity;
import android.content.Context;

import com.curry.mylovemylife.application.MyApplication;
import com.curry.mylovemylife.utils.DialogUtil;
import com.curry.mylovemylife.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.curry.mylovemylife.utils.DialogUtil.dismissDialog;

/**
 * add by shouyi
 * 2017-03-28
 */
public class HttpUtils {
    /**
     * GET请求--带参数
     *
     * @param url
     * @param requestCode
     * @param getCallBack
     */
    public static void doGet(final Context context, final String msg, String url, HashMap<String, String> params, final int requestCode,
                             final HttpCallBack getCallBack) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (HashMap.Entry<String, String> param : params.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        //创建一个Request
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .addHeader("Content-Type", "text/json")
                .build();

        Call call = MyApplication.mOkHttpClient.newCall(request);
        if (msg != null && context != null && !msg.equals("")) {
            DialogUtil.showDialog(context, msg);
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissDialog();
                        ToastUtil.showToastShort(context, "服务器繁忙");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissDialog();
                        getCallBack.onSuccess(requestCode, result);
                    }
                });
            }
        });
    }


    public static void doPostJson(final Context context, final String msg, String url, String jsonStr,
                                  final int requestCode, final HttpCallBack httpCallBack) {
        try {
            MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
            RequestBody body;
            if (jsonStr != null) {
                body = RequestBody.create(JSONTYPE, jsonStr);
            } else {
                body = new FormBody.Builder().build();
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "text/json")
                    .build();
            Call call = MyApplication.mOkHttpClient.newCall(request);
            if (msg != null && context != null && !msg.equals("")) {
                DialogUtil.showDialog(context, msg);
            }
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            ToastUtil.showToastShort(context, "服务器繁忙");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    final int resultCode = response.code();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            if (resultCode == 500) {
                                ToastUtil.showToastShort(context, "");
                                return;
                            }
                            httpCallBack.onSuccess(requestCode, result);
                        }
                    });
                }
            });

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * POST请求(带图片或文件上传)
     *
     * @param context
     * @param msg
     * @param url
     * @param params
     * @param requestCode
     * @param httpCallBack
     */
    public static void doPost(final Context context, final String msg, String url, HashMap<String, String> params,
                              HashMap<String, File> fileMap,
                              final int requestCode, final HttpCallBack httpCallBack) {
        if (msg != null && null != context && !msg.equals("")) {
            DialogUtil.showDialog(context, msg);
        }
        try {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);

            if (params != null && params.size() > 0) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    multipartBodyBuilder.addFormDataPart(key, params.get(key));
                }
            }
            if (fileMap != null && fileMap.size() > 0) {
                Set<String> keys = fileMap.keySet();
                for (String key : keys) {
                    multipartBodyBuilder.addFormDataPart(key, fileMap.get(key).getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"), fileMap.get(key)));
                }
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(multipartBodyBuilder.build())
                    .addHeader("Content-Type", "text/json")
                    .build();

            Call call = MyApplication.mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            ToastUtil.showToastShort(context, "服务器繁忙");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            httpCallBack.onSuccess(requestCode, result);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求(参数最后会转成String类型上传)
     *
     * @param context
     * @param msg
     * @param url
     * @param params
     * @param requestCode
     * @param httpCallBack
     */
    public static void doPost(final Context context, final String msg, String url, HashMap<String, String> params,
                              final int requestCode, final HttpCallBack httpCallBack) {
        if (msg != null && null != context && !msg.equals("")) {
            DialogUtil.showDialog(context, msg);
        }
        try {
            FormBody.Builder formBody = new FormBody.Builder();
            if (params != null && params.size() > 0) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    formBody.add(key, params.get(key));
                }
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody.build())
                    .addHeader("Content-Type", "text/json")
                    .build();

            Call call = MyApplication.mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            if (e.getMessage() != null) {
                                ToastUtil.showToastShort(context, "服务器繁忙");
                                httpCallBack.onFailure(requestCode, 0, e.getMessage());
                                return;
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            httpCallBack.onSuccess(requestCode, result);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     */
    public static void downAsynFile(final FileCallBack fileCallBack, String url, final String target,
                                    final String type, final int resultCode) {
        Request request = new Request.Builder().url(url).build();
        Call call = MyApplication.mOkHttpClient.newCall(request);
        fileCallBack.onStart(type);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fileCallBack.onStop(1, type);//失败停止
            }

            @Override
            public void onResponse(Call call, Response response) {
                fileCallBack.onStop(0, type);//成功停止
                fileCallBack.fileCallBack(resultCode, response.body().toString(), type);
            }
        });
    }

    public interface HttpCallBack {
        void onSuccess(int requestCode, String result);

        void onFailure(int requestCode, int status, String msg);
    }

    public interface FileCallBack {
        void fileCallBack(int resultCode, String result, String type);//tpye:下载文件类型

        void onStart(String type);

        void onStop(int stop, String type);
    }
}
