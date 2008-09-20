/*
 * Copyright 2008 Android4ME
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.content.res;

/**
 * @author Dmitry Skiba
 *
 */
public final class Configuration {

	public float fontScale;
	public int keyboard;
	public int keyboardHidden;
	//public Locale locale;
	public int mcc;
	public int mnc;
	public int navigation;
	public int orientation;
	public int touchscreen;
	
	public static final int 
		TOUCHSCREEN_UNDEFINED	=0,
		TOUCHSCREEN_NOTOUCH		=1,
		TOUCHSCREEN_STYLUS		=2,
		TOUCHSCREEN_FINGER		=3;
	
	public static final int 
		KEYBOARD_UNDEFINED		=0,
		KEYBOARD_NOKEYS			=1,
		KEYBOARD_QWERTY			=2,
		KEYBOARD_12KEY			=3;
	
	public static final int 
		KEYBOARDHIDDEN_UNDEFINED=0,
		KEYBOARDHIDDEN_NO		=1,
		KEYBOARDHIDDEN_YES		=2;
	
	public static final int 
		NAVIGATION_UNDEFINED	=0,
		NAVIGATION_NONAV		=1,
		NAVIGATION_DPAD			=2,
		NAVIGATION_TRACKBALL	=3,
		NAVIGATION_WHEEL		=4;
	
	public static final int
		ORIENTATION_UNDEFINED	=0,
		ORIENTATION_PORTRAIT	=1,
		ORIENTATION_LANDSCAPE	=2,
		ORIENTATION_SQUARE		=3;
}
