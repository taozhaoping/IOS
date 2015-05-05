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
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

import android.app.Activity;


/**
 * @ClassName TextResultHandler
 * @Description 文本解析
 * @author 21291
 * @date 2014年7月11日 下午12:38:43
 */
public final class TextResultHandler extends ResultHandler {

  public TextResultHandler(Activity activity, ParsedResult result, Result rawResult) {
    super(activity, result, rawResult);
  }

  @Override
  public void handleDeal(int index) {
    String text = getResult().getDisplayResult();
    switch (index) {
      case 0:
        webSearch(text);
        break;
    }
  }

  @Override
  public int getDisplayTitle() {
    return R.string.capture_result_text;
  }
}
