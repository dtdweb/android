

package com.dtdweb.sharedpreferences.sample;

import com.dtdweb.sharedpreferences.sample.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {

	private EditText     _eText;
	private TextView     _vText;
	private SharedSample _sharedSample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button addButton    = (Button)findViewById(R.id.button_add);
		Button showButton   = (Button)findViewById(R.id.button_show);
		Button deleteButton = (Button)findViewById(R.id.button_delete);
		_eText              = (EditText)findViewById(R.id.textForm);
		_vText              = (TextView)findViewById(R.id.sharedText);
		_sharedSample       = new SharedSample(this);

		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_sharedSample.setText(_eText.getText().toString());
			}
		});
		showButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_vText.setText(_sharedSample.getText());
			}
		});
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_sharedSample.removeText();
				_vText.setText(getString(R.string.text_empty));
			}
		});
	}

}
