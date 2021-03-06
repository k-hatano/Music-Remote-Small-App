/*
 * Copyright 2011, 2012 Sony Corporation
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package jp.nita.musicremotesmallapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.BaseInputConnection;
import android.widget.TextView;
import android.widget.Toast;

import com.sony.smallapp.SmallAppWindow;
import com.sony.smallapp.SmallAppWindow.OnWindowStateChangeListener;
import com.sony.smallapp.SmallApplication;
import com.sony.smallapp.SmallAppWindow.WindowState;

public class MainApplication extends SmallApplication {

	TextView tx;

	@Override
	public void onCreate() {
		super.onCreate();
		setContentView(R.layout.main);
		setTitle(R.string.app_name);

		SmallAppWindow.Attributes attr = getWindow().getAttributes();
		attr.minWidth = 256; /* The minimum width of the application, if it's resizable.*/
		attr.minHeight = 256; /*The minimum height of the application, if it's resizable.*/
		attr.width = 384;  /*The requested width of the application.*/
		attr.height = 384;  /*The requested height of the application.*/
		attr.flags |= SmallAppWindow.Attributes.FLAG_RESIZABLE;   /*Use this flag to enable the application window to be resizable*/
		attr.flags |= SmallAppWindow.Attributes.FLAG_NO_TITLEBAR;  /*Use this flag to remove the titlebar from the window*/
		//        attr.flags |= SmallAppWindow.Attributes.FLAG_HARDWARE_ACCELERATED;  /* Use this flag to enable hardware accelerated rendering*/

		getWindow().setAttributes(attr); /*setting window attributes*/

		// small layout

		findViewById(R.id.play_tiny).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
			}
		});

		findViewById(R.id.prev_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
			}
		});

		findViewById(R.id.play_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
			}
		});

		findViewById(R.id.next_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_NEXT);
			}
		});

		findViewById(R.id.up_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_VOLUME_UP);
			}
		});

		findViewById(R.id.down_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_VOLUME_DOWN);
			}
		});

		findViewById(R.id.minimize_small).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getWindow().setWindowState(WindowState.MINIMIZED);
			}
		});

		// large layout

		findViewById(R.id.prev_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
			}
		});

		findViewById(R.id.play_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PLAY);
			}
		});
		
		findViewById(R.id.pause_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_PAUSE);
			}
		});

		findViewById(R.id.stop_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_STOP);
			}
		});

		findViewById(R.id.next_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_MEDIA_NEXT);
			}
		});
		
		findViewById(R.id.plane_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_ESCAPE);
			}
		});
		
		findViewById(R.id.rewind_large).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					KeyDownEventSender sender = new KeyDownEventSender();
					sender.execute(KeyEvent.KEYCODE_MEDIA_REWIND);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					KeyUpEventSender sender = new KeyUpEventSender();
					sender.execute(KeyEvent.KEYCODE_MEDIA_REWIND);
				}
				return false;
			}
		});
		
		findViewById(R.id.forward_large).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					KeyDownEventSender sender = new KeyDownEventSender();
					sender.execute(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					KeyUpEventSender sender = new KeyUpEventSender();
					sender.execute(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD);
				}
				return false;
			}
		});

		findViewById(R.id.up_media_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			}
		});

		findViewById(R.id.down_media_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			}
		});
		
		findViewById(R.id.up_ring_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				manager.adjustStreamVolume(AudioManager.STREAM_RING,
		                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			}
		});

		findViewById(R.id.down_ring_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				manager.adjustStreamVolume(AudioManager.STREAM_RING,
		                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			}
		});

		findViewById(R.id.home_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
		});

		/*
		findViewById(R.id.call_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_CALL);
			}
		});
		*/

		findViewById(R.id.minimize_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getWindow().setWindowState(WindowState.MINIMIZED);
			}
		});

		findViewById(R.id.rotate_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					if (Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) > 0){
						Toast.makeText(v.getContext(), getString(R.string.accelerometer_rotation_off), Toast.LENGTH_LONG).show();
						Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
					} else {
						Toast.makeText(v.getContext(), getString(R.string.accelerometer_rotation_on), Toast.LENGTH_LONG).show();
						Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
					}
				} catch (SettingNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.wifi_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
				if(wifi.isWifiEnabled()) {
					Toast.makeText(v.getContext(), getString(R.string.wifi_off), Toast.LENGTH_LONG).show();
					wifi.setWifiEnabled(false);
				}else{
					Toast.makeText(v.getContext(), getString(R.string.wifi_on), Toast.LENGTH_LONG).show();
					wifi.setWifiEnabled(true);
				}
			}
		});

		findViewById(R.id.bluetooth_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
				if(bluetooth.isEnabled()) {
					Toast.makeText(v.getContext(), getString(R.string.bluetooth_off), Toast.LENGTH_LONG).show();
					bluetooth.disable();
				}else{
					Toast.makeText(v.getContext(), getString(R.string.bluetooth_on), Toast.LENGTH_LONG).show();
					bluetooth.enable();
				}
			}
		});

		getWindow().setOnWindowStateChangeListener(new OnWindowStateChangeListener(){
			@Override
			public void onWindowStateChanged(WindowState state) {
				findViewById(R.id.layout).invalidate();
			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private class KeyEventSender extends AsyncTask<Integer, Object, Object> {
		@Override
		protected Object doInBackground(Integer... params) {
			int keycode = (Integer)(params[0]);
			Instrumentation ist = new Instrumentation();
			ist.sendKeyDownUpSync(keycode);
			return null;
		}
	}
	
	private class KeyDownEventSender extends AsyncTask<Integer, Object, Object> {
		@Override
		protected Object doInBackground(Integer... params) {
			int keycode = (Integer)(params[0]);
			KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN,keycode);
			Instrumentation ist = new Instrumentation();
			ist.sendKeySync(event);
			return null;
		}
	}
	
	private class KeyUpEventSender extends AsyncTask<Integer, Object, Object> {
		@Override
		protected Object doInBackground(Integer... params) {
			int keycode = (Integer)(params[0]);
			KeyEvent event = new KeyEvent(KeyEvent.ACTION_UP,keycode);
			Instrumentation ist = new Instrumentation();
			ist.sendKeySync(event);
			return null;
		}
	}
}
