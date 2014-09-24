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

import android.app.Instrumentation;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.sony.smallapp.SmallAppWindow;
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
		attr.minWidth = 384; /* The minimum width of the application, if it's resizable.*/
		attr.minHeight = 384; /*The minimum height of the application, if it's resizable.*/
		attr.width = 384;  /*The requested width of the application.*/
		attr.height = 384;  /*The requested height of the application.*/
		attr.flags |= SmallAppWindow.Attributes.FLAG_RESIZABLE;   /*Use this flag to enable the application window to be resizable*/
		attr.flags |= SmallAppWindow.Attributes.FLAG_NO_TITLEBAR;  /*Use this flag to remove the titlebar from the window*/
		//        attr.flags |= SmallAppWindow.Attributes.FLAG_HARDWARE_ACCELERATED;  /* Use this flag to enable hardware accelerated rendering*/

		getWindow().setAttributes(attr); /*setting window attributes*/

		// small layout

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
				sender.execute(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
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

		findViewById(R.id.up_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_VOLUME_UP);
			}
		});

		findViewById(R.id.down_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				KeyEventSender sender = new KeyEventSender();
				sender.execute(KeyEvent.KEYCODE_VOLUME_DOWN);
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

		findViewById(R.id.minimize_large).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getWindow().setWindowState(WindowState.MINIMIZED);
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
			Instrumentation ist = new Instrumentation();
			int keycode = (Integer)(params[0]);
			ist.sendKeyDownUpSync(keycode);
			return null;
		}
	}
}
