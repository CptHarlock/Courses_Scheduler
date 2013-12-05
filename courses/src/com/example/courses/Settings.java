package com.example.courses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends Activity {

	private int seekR, seekG, seekB;
	private int textR, textG, textB;
	SeekBar redSeekBar, greenSeekBar, blueSeekBar;
	SeekBar text_R, text_G, text_B;
	LinearLayout mScreen;

	// Getting Colour values from seekbars
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		mScreen = (LinearLayout) findViewById(R.id.myScreen);
		redSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_R);
		greenSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_G);
		blueSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_B);

		text_R = (SeekBar) findViewById(R.id.text_R);
		text_G = (SeekBar) findViewById(R.id.text_G);
		text_B = (SeekBar) findViewById(R.id.text_B);
		updateBackground();

		redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		text_R.setOnSeekBarChangeListener(seekBarChangeListener);
		text_G.setOnSeekBarChangeListener(seekBarChangeListener);
		text_B.setOnSeekBarChangeListener(seekBarChangeListener);

	}

	private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			updateBackground();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
	};

	private void updateBackground() {
		seekR = redSeekBar.getProgress();
		seekG = greenSeekBar.getProgress();
		seekB = blueSeekBar.getProgress();
		textR = text_R.getProgress();
		textG = text_G.getProgress();
		textB = text_B.getProgress();
		int x = 0xff000000 + seekR * 0x10000 + seekG * 0x100 + seekB;
		int y = 0xff000000 + textR * 0x10000 + textG * 0x100 + textB;
		TextView bcc = (TextView) findViewById(R.id.bcc);
		TextView fcc = (TextView) findViewById(R.id.fcc);
		//setting colors dynamically like a preview
		mScreen.setBackgroundColor(x);
		bcc.setTextColor(y);
		fcc.setTextColor(y);
		
		RadioGroup type = (RadioGroup) findViewById(R.id.radioGroup1);
		int checkedRadioButton1 = type.getCheckedRadioButtonId();
		int fonts = 0;
		switch (checkedRadioButton1) {
		case R.id.radio1:
			fonts = 0;
			break;
		case R.id.radio2:
			fonts = 1;
			break;
		case R.id.radio3:
			fonts = 2;
			break;
		}
		RadioButton b1 = (RadioButton) findViewById(R.id.radio2);
		RadioButton b2 = (RadioButton) findViewById(R.id.radio3);
		RadioButton b3 = (RadioButton) findViewById(R.id.radio1);
		Typeface b11 = Typeface.createFromAsset(getAssets(),
				"Advert-Regular.ttf");
		Typeface b22 = Typeface.createFromAsset(getAssets(), "kberry.ttf");
		b1.setTypeface(b11);
		b2.setTypeface(b22);
		b3.setTypeface(Typeface.SANS_SERIF);

		// save values in sharedPreferences
		SharedPreferences settings = this.getSharedPreferences(
				"com.example.courses", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("com.example.courses", x);
		editor.putInt("text", y);
		editor.putInt("font", fonts);

		// Commit the edits!
		editor.commit();
	}
	// saving values
	public void save(View view) {
		updateBackground();
		finish();
	}
}