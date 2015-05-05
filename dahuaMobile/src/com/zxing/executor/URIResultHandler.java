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

import android.app.Activity;

import com.dahuatech.app.R;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;

/**
 * @ClassName URIResultHandler
 * @Description 提供URL响应
 * @author 21291
 * @date 2014年7月11日 下午12:38:19
 */
public final class URIResultHandler extends ResultHandler {
	// URIs beginning with entries in this array will not be saved to history or
	// copied to the
	// clipboard for security.
	@SuppressWarnings("unused")
	private static final String[] SECURE_PROTOCOLS = { "otpauth:" };

	public URIResultHandler(Activity activity, ParsedResult result) {
		super(activity, result);
	}

	@Override
	public void handleDeal(int index) {
		URIParsedResult uriResult = (URIParsedResult) getResult();
		String uri = uriResult.getURI();
		switch (index) {
			case 0:
				openURL(uri);
				break;
		}
	}

	@Override
	public int getDisplayTitle() {
		return R.string.capture_result_uri;
	}
}
