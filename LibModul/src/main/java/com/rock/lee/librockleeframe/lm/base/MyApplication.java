package com.rock.lee.librockleeframe.lm.base;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.rock.lee.librockleeframe.lm.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rock.lee on 2017/7/26.
 * I am very NB ，NB very much !!!
 */

public class MyApplication  extends Application {

    /** Log 打印标识 */
    private static String TAG = "rocklee";
    /** okhttp请求网络对象 */
    private static OkHttpClient mOkhttpClient = null;
    /** Retrofit请求对欢畅*/
    private static Retrofit mRetrofit = null;
    /** 网络请求URL，例如;https://android.rocklee.com/ */
    private static String API_URL = null;
    

    @Override
    public void onCreate() {
        super.onCreate();
        
        initLogger();

        initOkhttpClient();
        initRetrofit();


    }
    
    


    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(TAG)               // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .showThreadInfo(false)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
                .methodOffset(2)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }



    /**
     * 获取Log 打印标识
     * @return
     */
    public String getTAG() {
        return TAG;
    }

    /**
     * 设置Log 打印标识
     * @param TAG
     */
    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    /**
     * 获取 okhttp请求网络对象
     * @return
     */
    public static OkHttpClient getmOkhttpClient() {
        return mOkhttpClient;
    }

    /**
     * 设置初始化OkHttp
     */
    private void initOkhttpClient() {
        //OkHttp设置打印日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("rocklee",message);
            }
        });
        
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        mOkhttpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS)
                .writeTimeout(300,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    /**
     * 获取 Retrofit请求网络对象
     * @return
     */
    public static Retrofit getmRetrofit() {
        return mRetrofit;
    }

    /**
     * 设置初始化Retrofit
     */
    private void initRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getApiUrl())
                .client(getmOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 返回网络请求主机地址
     * @return
     */
    public static String getApiUrl() {
        return API_URL;
    }

    /**
     * 设置网络请求主机地址
     * @return
     */
    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }
}
