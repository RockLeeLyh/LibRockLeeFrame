package com.rock.lee.librockleeframe.lm.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

/**
 * Created by rock.lee on 2017/7/24.
 * I am very NB ，NB very much !!!
 */

public class BaseActivity extends FragmentActivity {

    /** 使用ButterKnife框架时，继承父类，调用时先设置布局文件 */
    private int layoutId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.i("FragmentActivity is name =====" + this.getClass().getName());
    }

    /**
     * 使用ButterKnife框架时，继承父类，调用时先设置布局文件
     * @param layoutId
     */
    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }


    /**
     * 页面跳转使用
     * @param cls
     * @return
     */
    public Intent getIntent(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        return intent;
    }


    /**
     * 普通跳转页面
     * @param cls
     */
    public void startClassActivity(Class<?> cls) {
        startActivity(getIntent(cls));
    }
}
