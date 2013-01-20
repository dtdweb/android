package com.dtdweb.sharedpreferences.sample;

import android.content.Context;

import com.dtdweb.lib.sharedpreferences.SharedPreferencesWrapper;

public class SharedSample extends SharedPreferencesWrapper {

    private static final String TEXT_CONFIG_KEY_NAME = "text_config_name";

    public SharedSample(Context context) {
        super(context);
    }

    /**
     * テキストの保存
     */
    public void setText(String text) {
        this._set(SharedSample.TEXT_CONFIG_KEY_NAME, text);
    }

    /**
     * 保存されているテキストデータの取得
     */
    public String getText() {
        return this._get(SharedSample.TEXT_CONFIG_KEY_NAME);
    }

    /**
     * 保存されているテキストデータを削除
     */
    public void removeText() {
        this._remove(SharedSample.TEXT_CONFIG_KEY_NAME);
    }
}