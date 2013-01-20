/**
 * データ保存クラス(SharedPreferences)でデータを保存する際、データをキーごと暗号化するためのラッパークラスです。
 * 使用するにはこのクラスを継承し、setter/getterを呼び出して使用します。
 * 
 * @author dtdweb
 */
package com.dtdweb.lib.sharedpreferences;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class SharedPreferencesWrapper {

	private static final String APP_BASE_CONFIG_KEY = "sec_base_key_name";
	// 設定ファイル名
	private static final String CONFIG_FILE_NAME = "shared_data";

	private Context           _context;
	private SharedPreferences _shared_preference;
	private Key               _key;

	public SharedPreferencesWrapper(Context context) {
		_context = context;
		_shared_preference = _context.getSharedPreferences(SharedPreferencesWrapper.CONFIG_FILE_NAME, Context.MODE_PRIVATE);

		// 復元用のキーを取得/初回起動時はキーを生成します。
    	String keyStr = this.getPreference().getString(SharedPreferencesWrapper.APP_BASE_CONFIG_KEY, "");
    	if (keyStr.length() == 0) {
    		// 生成
    		_key = Encryptor.generateKey();
    		String base64key = Base64.encodeToString(_key.getEncoded(), Base64.URL_SAFE | Base64.NO_WRAP);
    		SharedPreferences.Editor editor = this.getPreference().edit();
    		editor.putString(SharedPreferencesWrapper.APP_BASE_CONFIG_KEY, base64key);
    		editor.commit();
    	} else {
    		// 復元
    		byte[] keyBytes = Base64.decode(keyStr, Base64.URL_SAFE | Base64.NO_WRAP);
    		_key = new SecretKeySpec(keyBytes, "AES");
    	}
	}

	protected Context getContext() {
		return _context;
	}

    protected SharedPreferences getPreference() {
        return _shared_preference;
    }

    /**
     * データの保存
     * @param  String key
     * @param  String value
     * @return void
     */
    protected void _set(String key, String value) {
    	SharedPreferences.Editor editor = this.getPreference().edit();
    	// key / valueともに暗号化する
    	editor.putString(Encryptor.encrypt(key, _key), Encryptor.encrypt(value, _key));
    	editor.commit();
    }

    /**
     * データを取得
     * @param  String key
     * @return String value
     */
    protected String _get(String key) {
    	String value = this.getPreference().getString(Encryptor.encrypt(key, _key), "");
    	if (value.length() == 0) {
    		return "";
    	} else {
    		return Encryptor.decrypt(value, _key);
    	}
    }

    /**
     * キー名に該当するデータを削除
     * @param  String key
     * @return void
     */
    protected void _remove(String key) {
    	SharedPreferences.Editor editor = this.getPreference().edit();
    	editor.remove(Encryptor.encrypt(key, _key));
    	editor.commit();
    }

}