/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxing.executor;

import com.dahuatech.app.R;
import com.dahuatech.app.common.StringUtils;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public abstract class ResultHandler {

	private static final String TAG = ResultHandler.class.getSimpleName();
	private ParsedResult result;
	private final Activity activity;
	@SuppressWarnings("unused")
	private final Result rawResult;

	/*
	 * public ResultHandler(ParsedResult result) { this.result = result; }
	 */

	ResultHandler(Activity activity, ParsedResult result) {
		this(activity, result, null);
	}

	ResultHandler(Activity activity, ParsedResult result, Result rawResult) {
		this.result = result;
		this.activity = activity;
		this.rawResult = rawResult;
	}

	public final ParsedResult getResult() {
		return result;
	}

	final Activity getActivity() {
		return activity;
	}

	/** 
	* @Title: handleDeal 
	* @Description: 不同类型 结果的不同处理
	* @param @param index     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月11日 下午1:13:37
	*/
	public abstract void handleDeal(int index);

	/**
	 * Create a possibly styled string for the contents of the current barcode.
	 * 
	 * @return The text to be displayed.
	 */
	public CharSequence getDisplayContents() {
		String contents = result.getDisplayResult();
		return contents.replace("\r", "");
	}

	/**
	 * A string describing the kind of barcode that was found, e.g.
	 * "Found contact info".
	 * 
	 * @return The resource ID of the string.
	 */
	public abstract int getDisplayTitle();

	/**
	 * A convenience method to get the parsed type. Should not be overridden.
	 * 
	 * @return The parsed type, e.g. URI or ISBN
	 */
	public final ParsedResultType getType() {
		return result.getType();
	}

	final void openURL(String url) {
		// Strangely, some Android browsers don't seem to register to handle
		// HTTP:// or HTTPS://.
		// Lower-case these as it should always be OK to lower-case these
		// schemes.
		if (url.startsWith("HTTP://")) {
			url = "http" + url.substring(4);
		} else if (url.startsWith("HTTPS://")) {
			url = "https" + url.substring(5);
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		try {
			launchIntent(intent);
		} catch (ActivityNotFoundException ignored) {
			Log.w(TAG, "Nothing available to handle " + intent);
		}
	}

	final void webSearch(String query) {
		Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		intent.putExtra("query", query);
		launchIntent(intent);
	}

	/**
	 * Like {@link #launchIntent(Intent)} but will tell you if it is not
	 * handle-able via {@link ActivityNotFoundException}.
	 * 
	 * @throws ActivityNotFoundException
	 */
	final void rawLaunchIntent(Intent intent) {
		if (intent != null) {
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			Log.d(TAG, "Launching intent: " + intent + " with extras: "+ intent.getExtras());
			activity.startActivity(intent);
		}
	}

	/**
	 * Like {@link #rawLaunchIntent(Intent)} but will show a user dialog if
	 * nothing is available to handle.
	 */
	final void launchIntent(Intent intent) {
		try {
			rawLaunchIntent(intent);
		} catch (ActivityNotFoundException ignored) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle(R.string.app_name);
			builder.setMessage(R.string.capture_msg_intent_failed);
			builder.setPositiveButton(R.string.sure, null);
			builder.show();
		}
	}

	@SuppressWarnings("unused")
	private static void putExtra(Intent intent, String key, String value) {
		if (!StringUtils.isEmpty(value)) {
			intent.putExtra(key, value);
		}
	}
}
