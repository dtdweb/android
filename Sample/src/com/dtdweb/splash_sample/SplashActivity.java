/**
 * スレッドを利用し、指定秒数間画像等を表示した後にメイン画面に遷移させる
 * @author muroi@dtdweb.com
 */
package com.dtdweb.splash_sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	// スプラッシュ画像を何秒間表示するか
	private static final int SPLASH_TIME = 3000; // 例:3秒

	private Context _context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		_context = this;

		// スレッドのインスタンスを作成、遅延実行を行なう
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), SPLASH_TIME);
	}

	class splashHandler implements Runnable {
		public void run() {
			// ここで次に遷移させたいActivityを使用して呼び出します
			startActivity(new Intent(_context, MainActivity.class));
			SplashActivity.this.finish();
		}
	}
}