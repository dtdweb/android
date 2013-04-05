/**
 * GPSから位置情報を取得して表示します
 * @author muroi@dtdweb.com
 */
package com.dtdweb.gpssample;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity implements LocationListener {

	// 緯度・経度表示用のTextView
	private TextView    _textViewNetworkLat, _textViewNetworkLng, _textViewGpsLat, _textViewGpsLng;
	private ProgressBar _searchProgressBar;

	private Button _buttonSearchNetwork, _buttonSearchGps;

	private Context         _context;
	private LocationManager _locationManager;

	// どちらのプロバイダを実行中か判別できるようにする
	private int _execSearchProvider;
	private static final int EXEC_PROVIDER_NETOWORK = 1;
	private static final int EXEC_PROVIDER_GPS      = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_textViewNetworkLat  = (TextView)findViewById(R.id.textViewNetworkLat);
		_textViewNetworkLng  = (TextView)findViewById(R.id.textViewNetworkLng);
		_textViewGpsLat      = (TextView)findViewById(R.id.textViewGpsLat);
		_textViewGpsLng      = (TextView)findViewById(R.id.textViewGpsLng);
		_searchProgressBar   = (ProgressBar)findViewById(R.id.progressBar_search);
		_buttonSearchNetwork = (Button)findViewById(R.id.button_search_location_network);
		_buttonSearchGps     = (Button)findViewById(R.id.button_search_location_gps);
		_context             = this;
		_locationManager     = (LocationManager)_context.getSystemService(Context.LOCATION_SERVICE);

		// プログレスバーは初回非表示とする
		_searchProgressBar.setVisibility(View.INVISIBLE);

		_buttonSearchNetwork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ネットワークサービスよる検索が有効かチェック
				if (_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
					_locationManager.removeUpdates(MainActivity.this);
					_buttonSearchNetwork.setClickable(false);
					_buttonSearchGps.setClickable(false);
					_searchProgressBar.setVisibility(View.VISIBLE);
					_execSearchProvider = EXEC_PROVIDER_NETOWORK;
					// 検索を実行
					_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity.this);
				} else {
					// 無効
					Toast.makeText(_context, "ネットワークサービスが無効のため検索する事が出来ません", Toast.LENGTH_SHORT).show();
				}
			}
		});
		_buttonSearchGps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// GPSよる検索が有効かチェック
				if (_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					_locationManager.removeUpdates(MainActivity.this);
					_buttonSearchNetwork.setClickable(false);
					_buttonSearchGps.setClickable(false);
					_searchProgressBar.setVisibility(View.VISIBLE);
					_execSearchProvider = EXEC_PROVIDER_GPS;
					// 検索を実行
					_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
				} else {
					// 無効
					Toast.makeText(_context, "GPSが無効のため検索する事が出来ません", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		// バッググラウンドに移動した場合は検索を終了させる
		_locationManager.removeUpdates(this);
		_buttonSearchNetwork.setClickable(true);
		_buttonSearchGps.setClickable(true);
		_searchProgressBar.setVisibility(View.INVISIBLE);
	}

	// 位置が特定できると呼び出されます
	@Override
	public void onLocationChanged(Location location) {
		_locationManager.removeUpdates(this);
		// テンプレートに表示
		if (_execSearchProvider == EXEC_PROVIDER_GPS) {
			_textViewGpsLat.setText(String.valueOf(location.getLatitude()));
			_textViewGpsLng.setText(String.valueOf(location.getLongitude()));
		} else {
			_textViewNetworkLat.setText(String.valueOf(location.getLatitude()));
			_textViewNetworkLng.setText(String.valueOf(location.getLongitude()));
		}
		// ボタンを有効に戻す
		_buttonSearchNetwork.setClickable(true);
		_buttonSearchGps.setClickable(true);
		// プログレスを非表示とする
		_searchProgressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}
